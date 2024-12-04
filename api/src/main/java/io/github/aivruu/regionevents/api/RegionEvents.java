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
package io.github.aivruu.regionevents.api;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.regionevents.api.region.PlayerRegionInformationController;
import io.github.aivruu.regionevents.api.region.RegionUpdaterManager;
import io.github.aivruu.regionevents.api.repository.RepositoryModel;

/**
 * This is the plugin's API-interface used for access to the API classes' instances.
 * <p>
 * This interface's methods are supposed to be used once the plugin has been fully-enabled, if the API-instance
 * isn't initialized when call a method, it will throw an {@link IllegalStateException}.
 *
 * @since 1.0.0
 */
public interface RegionEvents {
  /**
   * Returns the {@link RegionUpdaterManager} instance.
   *
   * @return The {@link RegionUpdaterManager}.
   * @since 1.0.0
   */
  RegionUpdaterManager regionUpdaterManager();

  /**
   * Returns the {@link RepositoryModel} instance for the entered-regions in-cache handling.
   *
   * @return The {@link RepositoryModel} implementation for the entered-regions.
   * @since 1.0.0
   */
  RepositoryModel<ProtectedRegion> enteredRegionsCacheRepository();

  /**
   * Returns the {@link PlayerRegionInformationController} instance.
   *
   * @return The {@link PlayerRegionInformationController}.
   * @since 1.0.0
   */
  PlayerRegionInformationController playerRegionInformationController();
}
