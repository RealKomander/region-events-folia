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
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is fired when a player enters a region, this event can be cancelled.
 *
 * @since 1.0.0
 */
public final class RegionEnteredEvent extends Event implements Cancellable {
  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Player player;
  private final ProtectedRegion enteredRegion;
  private boolean cancelled;

  public RegionEnteredEvent(final Player player, final ProtectedRegion enteredRegion) {
    this.player = player;
    this.enteredRegion = enteredRegion;
  }

  /**
   * Returns the involved player.
   *
   * @return The {@link Player} who entered the region.
   * @since 1.0.0
   */
  public Player player() {
    return this.player;
  }

  /**
   * Returns the involved region in this event.
   *
   * @return The {@link ProtectedRegion} the player entered.
   * @since 1.0.0
   */
  public ProtectedRegion region() {
    return this.enteredRegion;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public void setCancelled(final boolean cancel) {
    this.cancelled = cancel;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
