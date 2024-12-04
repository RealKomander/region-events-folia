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
package io.github.aivruu.regionevents.api.cache;

import com.google.common.collect.ImmutableMap;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.aivruu.regionevents.api.repository.RepositoryModel;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link RepositoryModel} implementation for the entered {@link ProtectedRegion} by players.
 *
 * @since 1.0.0
 */
public final class EnteredRegionsCacheRepository implements RepositoryModel<ProtectedRegion> {
  private final Map<String, ProtectedRegion> enteredRegionsCache = new HashMap<>();

  @Override
  public @Nullable ProtectedRegion findSync(final String id) {
    return this.enteredRegionsCache.get(id);
  }

  @Override
  public ImmutableMap<String, ProtectedRegion> findAllSync() {
    return ImmutableMap.copyOf(this.enteredRegionsCache);
  }

  @Override
  public void saveSync(final String id, final ProtectedRegion model) {
    this.enteredRegionsCache.put(id, model);
  }

  @Override
  public boolean deleteSync(final String id) {
    return this.enteredRegionsCache.remove(id) != null;
  }

  @Override
  public void clearSync() {
    this.enteredRegionsCache.clear();
  }
}
