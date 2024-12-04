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
package io.github.aivruu.regionevents.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * This class provide utility-functions to work with {@code WorldGuard}'s functions, such as region-search
 * or check if a player is in a region.
 *
 * @since 1.0.0
 */
public final class RegionHelper {
  private static final RegionContainer REGION_CONTAINER = WorldGuard.getInstance().getPlatform().getRegionContainer();

  private RegionHelper() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantied.");
  }

  /**
   * Checks if the given location is at the specified region.
   *
   * @param location the location.
   * @param regionId the region where should be.
   * @return Whether the location is in the region.
   * @see #searchAtLocation(World, Location)
   * @since 1.0.0
   */
  public static boolean isInRegion(final Location location, final String regionId) {
    final var regions = searchAtLocation(location.getWorld(), location);
    if (regions == null) {
      return false;
    }
    for (final var region : regions) {
      if (region.getId().equals(regionId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Search for the specified region at player's world, and return a {@link ProtectedRegion} for the found region.
   *
   * @param player the player.
   * @param regionId the region to search.
   * @return The {@link ProtectedRegion} or {@code null} if not found.
   * @since 1.0.0
   */
  public static @Nullable ProtectedRegion searchLocal(final Player player, final String regionId) {
    final var regionManager = REGION_CONTAINER.get(BukkitAdapter.adapt(player.getWorld()));
    if (regionManager == null) {
      return null;
    }
    return regionManager.getRegion(regionId);
  }

  /**
   * Search for the specified region across all server's worlds, and return a {@link ProtectedRegion} for the found region.
   *
   * @param regionId the region to search.
   * @return The {@link ProtectedRegion} or {@code null} if not found.
   * @since 1.0.0
   */
  public static @Nullable ProtectedRegion searchGlobally(final String regionId) {
    for (final var world : Bukkit.getWorlds()) {
      final var regionManager = REGION_CONTAINER.get(BukkitAdapter.adapt(world));
      if (regionManager == null) {
        continue;
      }
      final var region = regionManager.getRegion(regionId);
      if (region == null) {
        continue;
      }
      return region;
    }
    return null;
  }

  /**
   * Finds all the regions that are in the specified world.
   *
   * @param worldName the name of the world where search by regions.
   * @return {@code null} if the world doesn't exist, otherwise result may depend on of {@link #searchAtWorld(World)} function.
   * @see #searchAtWorld(World)
   * @since 1.0.0
   */
  public static @Nullable Map<String, ProtectedRegion> searchAtWorld(final String worldName) {
    final var world = Bukkit.getWorld(worldName);
    return (world == null) ? null : searchAtWorld(world);
  }

  /**
   * Finds all the regions that are in the given world.
   *
   * @param world the world where to search.
   * @return A {@link Map} with the found regions, or {@code null} if there are no regions in the world.
   * @since 1.0.0
   */
  public static @Nullable Map<String, ProtectedRegion> searchAtWorld(final World world) {
    final var regionManager = REGION_CONTAINER.get(BukkitAdapter.adapt(world));
    if (regionManager == null) {
      return null;
    }
    return regionManager.getRegions();
  }

  /**
   * Finds all the regions at the world and location specified.
   *
   * @param world the world where to search.
   * @param location the location to surround by regions.
   * @return A {@link Set} with the found regions or {@code null} if there are no regions in the world.
   * @since 1.0.0
   */
  public static @Nullable Set<ProtectedRegion> searchAtLocation(final World world, final Location location) {
    final var regionManager = REGION_CONTAINER.get(BukkitAdapter.adapt(world));
    if (regionManager == null) {
      return null;
    }
    final var applicableRegionsSet = regionManager.getApplicableRegions(
      BukkitAdapter.asBlockVector(location), RegionQuery.QueryOption.COMPUTE_PARENTS);
    return applicableRegionsSet.getRegions();
  }
}
