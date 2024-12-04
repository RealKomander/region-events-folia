// This file is part of regions, licensed under the GNU License.
//
// Copyright (c) 2024 aivruu
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
package io.github.aivruu.regionevents.region;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.regionevents.RegionEventsPlugin;
import io.github.aivruu.regionevents.api.region.PlayerRegionInformationController;
import io.github.aivruu.regionevents.api.region.RegionUpdaterManager;
import io.github.aivruu.regionevents.config.object.SettingsConfigModel;
import io.github.aivruu.regionevents.event.RegionEnteredEvent;
import io.github.aivruu.regionevents.event.RegionQuitEvent;
import io.github.aivruu.regionevents.util.DebugHelper;
import io.github.aivruu.regionevents.util.RegionHelper;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public final class PluginRegionUpdaterManagerImpl implements RegionUpdaterManager {
  private final PlayerRegionInformationController playerRegionInformation;
  private final BukkitScheduler bukkitScheduler;
  private final RegionEventsPlugin plugin;
  private final PluginManager pluginManager;
  private SettingsConfigModel config;
  private World world;

  public PluginRegionUpdaterManagerImpl(final PlayerRegionInformationController playerRegionInformation, final BukkitScheduler bukkitScheduler,
                                        final RegionEventsPlugin plugin, final PluginManager pluginManager,
                                        final SettingsConfigModel config) {
    this.playerRegionInformation = playerRegionInformation;
    this.bukkitScheduler = bukkitScheduler;
    this.plugin = plugin;
    this.pluginManager = pluginManager;
    this.config = config;
  }

  public void setConfig(final SettingsConfigModel config) {
    this.config = config;
  }

  /**
   * Override to super-class's {@link RegionUpdaterManager#start()} method.
   * <p>
   * Checks if the plugin should monitor a single world, or several, and register a new async-task for region-events
   * monitoring.
   *
   * @since 1.0.0
   */
  @Override
  public void start() {
    final var asyncScheduler = this.plugin.getServer().getAsyncScheduler();
    if (this.config.monitorSingleWorldForRegions) {
      this.world = Bukkit.getWorld(this.config.worldToMonitor);
      DebugHelper.write("Value initialization for single-world monitoring is: {}", (this.world == null) ? "N/A" : this.config.worldToMonitor);
    }
    DebugHelper.write("Registering async-task for region-events firing management.");
    asyncScheduler.runAtFixedRate(this.plugin, task -> {
      if (this.world != null) {
        this.processSingleWorldUpdating();
      } else {
        this.processAllWorldsUpdating();
      }
    }, 1L, this.config.taskUpdateRate, this.config.timeUnitForUpdateRate);
  }

  @Override
  public void stop() {
    this.plugin.getServer().getAsyncScheduler().cancelTasks(this.plugin);
  }

  /**
   * Override to super-class's {@link RegionUpdaterManager#update(Player)} method.
   * <p>
   * Verifies if at player's location there regions to process, then iterates over that regions and perform
   * checks to verify if the player has entered a new region, has stay on one, or as left a region. The logic
   * for region events-firing and cached-info handling is delegated to another methods.
   *
   * @param player the player to check.
   * @return Whether the method's logic was executed as expected and (if necessary) events were fired.
   * @see #processRegionEnter(Player, String, ProtectedRegion)
   * @see #processRegionLeave(Player, String, ProtectedRegion)
   * @since 1.0.0
   */
  @Override
  public boolean update(final Player player) {
    final var regions = RegionHelper.searchAtWorld(player.getWorld());
    if (regions == null || regions.isEmpty()) {
      DebugHelper.write("Not found regions for the current world and location of the player.");
      DebugHelper.write("May regions' Set is empty, region-processing is skipped, no event-firing.");
      return true;
    }
    final var location = player.getLocation();
    final var playerId = player.getUniqueId().toString();
    for (final var region : regions.values()) {
      final var regionId = region.getId();
      // Check if the player is in the region, then process the region-enter.
      if (region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
        DebugHelper.write("The player is in the region: {}", regionId);
        return this.processRegionEnter(player, playerId, region);
      }
      DebugHelper.write("The player is not in the region: {}", regionId);
      return this.processRegionLeave(player, playerId, region);
    }
    return false;
  }

  private boolean processRegionEnter(final Player player, final String playerId, final ProtectedRegion region) {
    if (this.playerRegionInformation.search(playerId) != null) {
      return true;
    }
    final var regionEnteredEvent = new RegionEnteredEvent(player, region);
    // Prevent async event-firing.
    this.bukkitScheduler.runTask(this.plugin, task -> this.pluginManager.callEvent(regionEnteredEvent));
    if (regionEnteredEvent.isCancelled()) {
      DebugHelper.write("RegionEnteredEvent has been cancelled, skipping in-cache saving.");
      return false;
    }
    DebugHelper.write("Saving in-cache entered region '{}' by player '{}'", region.getId(), player.getName());
    this.playerRegionInformation.store(playerId, region);
    return true;
  }

  private boolean processRegionLeave(final Player player, final String playerId, final ProtectedRegion region) {
    if (!this.playerRegionInformation.remove(playerId)) {
      DebugHelper.write("Not entered-region by player data deletion from cache.");
      return false;
    }
    this.bukkitScheduler.runTask(this.plugin, task -> this.pluginManager.callEvent(new RegionQuitEvent(player, region)));
    DebugHelper.write("Entered-region by player data deletion completed.");
    return true;
  }

  private void processSingleWorldUpdating() {
    for (final var player : this.world.getPlayers()) {
      if (this.update(player)) {
        DebugHelper.write("Single-World - region-movement updated for player.");
      } else {
        DebugHelper.write("Single-World - region-movement was not updated, may event is cancelled, or there's no region for process in the world.");
      }
    }
  }

  private void processAllWorldsUpdating() {
    for (final var world : Bukkit.getWorlds()) {
      final var worldName = world.getName();
      for (final var player : world.getPlayers()) {
        if (this.update(player)) {
          DebugHelper.write("World {} - region-movement updated for player.", worldName);
        } else {
          DebugHelper.write("World {} - region-movement was not updated, may event is cancelled, or there's no region for process in the world.", worldName);
        }
      }
    }
  }
}
