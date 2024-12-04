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
package io.github.aivruu.regionevents;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.regionevents.api.RegionEvents;
import io.github.aivruu.regionevents.api.cache.EnteredRegionsCacheRepository;
import io.github.aivruu.regionevents.api.region.PlayerRegionInformationController;
import io.github.aivruu.regionevents.api.region.RegionUpdaterManager;
import io.github.aivruu.regionevents.api.repository.RepositoryModel;
import io.github.aivruu.regionevents.command.MainCommand;
import io.github.aivruu.regionevents.config.ConfigurationContainer;
import io.github.aivruu.regionevents.config.object.SettingsConfigModel;
import io.github.aivruu.regionevents.listener.PlayerRegionListener;
import io.github.aivruu.regionevents.region.PluginRegionUpdaterManagerImpl;
import io.github.aivruu.regionevents.util.DebugHelper;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class RegionEventsPlugin extends JavaPlugin implements RegionEvents {
  private ComponentLogger logger;
  private ConfigurationContainer<SettingsConfigModel> settingsConfigContainer;
  private RepositoryModel<ProtectedRegion> enteredRegionsCache;
  private PlayerRegionInformationController playerRegionInformationController;
  private PluginRegionUpdaterManagerImpl regionUpdaterManager;
  private MainCommand command;

  @Override
  public RegionUpdaterManager regionUpdaterManager() {
    if (this.regionUpdaterManager == null) {
      throw new IllegalStateException("The region updater-manager has not been initialized yet.");
    }
    return this.regionUpdaterManager;
  }

  @Override
  public RepositoryModel<ProtectedRegion> enteredRegionsCacheRepository() {
    if (this.enteredRegionsCache == null) {
      throw new IllegalStateException("The entered regions cache has not been initialized yet.");
    }
    return this.enteredRegionsCache;
  }

  @Override
  public PlayerRegionInformationController playerRegionInformationController() {
    if (this.playerRegionInformationController == null) {
      throw new IllegalStateException("The region-information-controller for players has not been initialized yet.");
    }
    return this.playerRegionInformationController;
  }

  @Override
  public void onLoad() {
    this.logger = super.getComponentLogger();
    this.settingsConfigContainer = ConfigurationContainer.of(super.getDataPath(), "config", SettingsConfigModel.class);
    if (this.settingsConfigContainer == null) {
      this.logger.info("Failed to load configuration files, plugin won't be enabled correctly.");
      return;
    }
    this.enteredRegionsCache = new EnteredRegionsCacheRepository();
    this.logger.info("Initialized cache-controller for the players' entered-regions.");
  }

  @Override
  @SuppressWarnings("UnstableApiUsage")
  public void onEnable() {
    if (this.enteredRegionsCache == null) {
      return;
    }
    final var config = this.settingsConfigContainer.model();
    DebugHelper.enable(config.debugMode);
    this.playerRegionInformationController = new PlayerRegionInformationController(this.enteredRegionsCache);
    this.logger.info("Initialized region-information-controller for players.");
    final var pluginManager = super.getServer().getPluginManager();
    this.regionUpdaterManager = new PluginRegionUpdaterManagerImpl(this.playerRegionInformationController, super.getServer().getScheduler(),
      this, pluginManager, config);
    this.regionUpdaterManager.start();
    this.logger.info("Initialized region-updater manager.");
    pluginManager.registerEvents(new PlayerRegionListener(this.playerRegionInformationController), this);
    this.command = new MainCommand(this, config);
    super.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, eventHandler -> {
      final var commands = eventHandler.registrar();
      commands.register(this.command.register());
    });
    this.logger.info(Component.text("The plugin has been enabled!").color(NamedTextColor.GREEN));
  }

  public boolean reload() {
    this.settingsConfigContainer = this.settingsConfigContainer.reload();
    if (this.settingsConfigContainer == null) {
      return false;
    }
    final var config = this.settingsConfigContainer.model();
    this.regionUpdaterManager.stop();
    this.regionUpdaterManager.setConfig(config);
    this.regionUpdaterManager.start();
    this.command.setConfig(config);
    return true;
  }

  @Override
  public void onDisable() {
    if (this.regionUpdaterManager != null) {
      this.regionUpdaterManager.stop();
    }
    if (this.enteredRegionsCache != null) {
      this.enteredRegionsCache.clearSync();
    }
    this.logger.info("Disabled");
  }
}
