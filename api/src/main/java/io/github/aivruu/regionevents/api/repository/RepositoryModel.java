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
package io.github.aivruu.regionevents.api.repository;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * This interface is used as model to provide methods for repositories' information-handling.
 *
 * @param <T> the object-type to be handled by the repository.
 */
public interface RepositoryModel<T> {
  /**
   * Finds the mapped-object for the given key.
   *
   * @param id the object's id.
   * @return The object or {@code null} if not found.
   * @since 1.0.0
   */
  @Nullable T findSync(final String id);

  /**
   * Returns a non-modifiable {@link Collection} with all the current stored-values in this repository.
   *
   * @return A immutable {@link Collection} with this repository's registry.
   * @since 1.0.0
   */
  ImmutableMap<String, T> findAllSync();

  /**
   * Saves the given object into the repository with its id.
   *
   * @param id the object's id.
   * @param model the object.
   * @since 1.0.0
   */
  void saveSync(final String id, final T model);

  /**
   * Deletes the object-mapping for the key from the repository.
   *
   * @param id the object's id.
   * @return Whether the model was deleted (if it existed).
   * @since 1.0.0
   */
  boolean deleteSync(final String id);

  /**
   * Removes all the objects from the repository.
   *
   * @since 1.0.0
   */
  void clearSync();
}
