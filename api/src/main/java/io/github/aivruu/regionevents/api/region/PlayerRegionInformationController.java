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

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.regionevents.api.repository.RepositoryModel;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used as upper-layer for players' entered-regions information handling.
 *
 * @since 1.0.0
 */
public final class PlayerRegionInformationController {
  private final RepositoryModel<ProtectedRegion> cache;

  public PlayerRegionInformationController(final RepositoryModel<ProtectedRegion> cache) {
    this.cache = cache;
  }

  /**
   * Returns the player's entered-region if it found.
   *
   * @param playerId the player's id.
   * @return The {@link ProtectedRegion} or {@code null} if player has never entered a region.
   * @see RepositoryModel#findSync(String)
   * @since 1.0.0
   */
  public @Nullable ProtectedRegion search(final String playerId) {
    return this.cache.findSync(playerId);
  }

  /**
   * Saves the player's entered-region's information in the repository, only if the player has
   * never entered a region.
   *
   * @param playerId the player's id.
   * @param region the region that the player entered.
   * @since 1.0.0
   */
  public void store(final String playerId, final ProtectedRegion region) {
    if (this.cache.findSync(playerId) != null) {
      return;
    }
    this.cache.saveSync(playerId, region);
  }

  /**
   * Removes the player's entered-region's information from the repository.
   *
   * @param playerId the player's id.
   * @return Whether the player has entered-region sometime.
   * @see RepositoryModel#deleteSync(String)
   * @since 1.0.0
   */
  public boolean remove(final String playerId) {
    return this.cache.deleteSync(playerId);
  }

  /**
   * Clears the repository's information-registry.
   *
   * @since 1.0.0
   */
  public void clear() {
    this.cache.clearSync();
  }
}
