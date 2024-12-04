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
package io.github.aivruu.regionevents.listener;

import io.github.aivruu.regionevents.api.region.PlayerRegionInformationController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerRegionListener implements Listener {
  private final PlayerRegionInformationController playerRegionInformation;

  public PlayerRegionListener(final PlayerRegionInformationController playerRegionInformation) {
    this.playerRegionInformation = playerRegionInformation;
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    // We ignore the result provided, we don't want to send a debug-message if information was removed or not.
    // We only care that if the player is on region when leave the server, delete that information from cache.
    this.playerRegionInformation.remove(event.getPlayer().getUniqueId().toString());
  }
}
