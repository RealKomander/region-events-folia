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
package io.github.aivruu.regionevents.config.object;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.concurrent.TimeUnit;

@ConfigSerializable
public final class SettingsConfigModel implements SealedConfigurationInterface {
  @Comment("Shows extra-information for almost-all plugin's processes, events-firing and information-handling.")
  public boolean debugMode = false;

  @Comment("""
    This means that the plugin only will have region-management for players at the specified world, only
    if this option is enabled.""")
  public boolean monitorSingleWorldForRegions = true;

  @Comment("The world to monitor for region-events, only if 'monitor-single-world-for-regions' option is enabled.")
  public String worldToMonitor = "world";

  @Comment("""
    The update-rate to set for the async-task.

    The plugin uses an async-task to check and monitor movements for any region event-firing instead using a listener
    for player's movements, this for a plugin's performance and avoid possible server lag-spikes if there are too many
    players.""")
  public byte taskUpdateRate = 1;

  @Comment("The time-unit to used for the update-rate.")
  public TimeUnit timeUnitForUpdateRate = TimeUnit.SECONDS;

  public String reloadSuccess = "<green>[RegionEvents]</green> The plugin was reloaded successfully!";

  public String reloadFailed = "<green>[RegionEvents]</green> <yellow>The plugin's configuration couldn't be reloaded.";
}
