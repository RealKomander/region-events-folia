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
package io.github.aivruu.regionevents.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.aivruu.regionevents.RegionEventsPlugin;
import io.github.aivruu.regionevents.config.object.SettingsConfigModel;
import io.github.aivruu.regionevents.utils.MiniMessageHelper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public final class MainCommand implements RegistrableCommandInterface {
  private final RegionEventsPlugin plugin;
  private SettingsConfigModel config;

  public MainCommand(final RegionEventsPlugin plugin, final SettingsConfigModel config) {
    this.plugin = plugin;
    this.config = config;
  }

  public void setConfig(final SettingsConfigModel config) {
    this.config = config;
  }

  @Override
  @SuppressWarnings("UnstableApiUsage")
  public LiteralCommandNode<CommandSourceStack> register() {
    return Commands.literal("regionevents")
      .then(Commands.literal("reload")
        .requires(src -> src.getSender().hasPermission("regionevents.reload"))
        .executes(ctx -> {
          final var sender = ctx.getSource().getSender();
          if (this.plugin.reload()) {
            sender.sendMessage(MiniMessageHelper.apply(this.config.reloadSuccess));
          } else {
            sender.sendMessage(MiniMessageHelper.apply(this.config.reloadFailed));
          }
          return Command.SINGLE_SUCCESS;
        })
      )
      .build();
  }
}
