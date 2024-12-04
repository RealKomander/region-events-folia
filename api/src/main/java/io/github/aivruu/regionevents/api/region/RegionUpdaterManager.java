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
package io.github.aivruu.regionevents.api.region;

import org.bukkit.entity.Player;

/**
 * This interface is used as main-base for any implementation that need to handle region-movements by the players.
 *
 * @since 1.0.0
 */
public interface RegionUpdaterManager {
  /**
   * Executes the start-logic for the region-updater, the logic to execute may depend on of the
   * implementation type.
   *
   * @since 1.0.0
   */
  void start();

  /**
   * Executes the stop-logic for the region-updater, the logic to execute may depend on of the
   * implementation type.
   *
   * @since 1.0.0
   */
  void stop();

  /**
   * Executes the logic defined by the implementation for this method for any update for player's entered-region
   * information.
   *
   * @param player the player to check.
   * @return Whether the method's logic was executed as expected.
   * @since 1.0.0
   */
  boolean update(final Player player);
}
