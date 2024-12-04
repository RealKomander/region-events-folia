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
package io.github.aivruu.regionevents.config;

import io.github.aivruu.regionevents.config.object.SealedConfigurationInterface;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public record ConfigurationContainer<C>(C model, HoconConfigurationLoader loader, Class<C> modelClass) {
  public @Nullable ConfigurationContainer<C> reload() {
    try {
      final var commentedNode = this.loader.load();
      return new ConfigurationContainer<>(commentedNode.get(this.modelClass), this.loader, this.modelClass);
    } catch (final ConfigurateException exception) {
      return null;
    }
  }

  public static <C extends SealedConfigurationInterface>
  @Nullable ConfigurationContainer<C> of(final Path directory, final String fileName, final Class<C> modelClass) {
    final var path = directory.resolve(fileName + ".conf");
    final var loader = HoconConfigurationLoader.builder()
      .prettyPrinting(true)
      .defaultOptions(opts -> opts.shouldCopyDefaults(true))
      .path(path)
      .build();
    try {
      final var commentedNode = loader.load();
      final var config = commentedNode.get(modelClass);
      if (Files.notExists(path)) {
        commentedNode.set(modelClass, config);
        loader.save(commentedNode);
      }
      return new ConfigurationContainer<>(config, loader, modelClass);
    } catch (final ConfigurateException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
