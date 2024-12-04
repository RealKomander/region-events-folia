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
package io.github.aivruu.regionevents.event;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when a player leaves a region.
 *
 * @since 1.0.0
 */
public final class RegionQuitEvent extends Event {
  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Player player;
  private final ProtectedRegion region;

  public RegionQuitEvent(final Player player, final ProtectedRegion region) {
    this.player = player;
    this.region = region;
  }

  /**
   * Returns the involved player.
   *
   * @return The {@link Player} who left the region.
   * @since 1.0.0
   */
  public Player player() {
    return this.player;
  }

  /**
   * Returns the involved region in this event.
   *
   * @return The {@link ProtectedRegion} the player left.
   * @since 1.0.0
   */
  public ProtectedRegion region() {
    return this.region;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
