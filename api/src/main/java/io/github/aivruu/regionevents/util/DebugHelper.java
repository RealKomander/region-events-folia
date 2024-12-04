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
package io.github.aivruu.regionevents.util;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

/**
 * This class is used for debug-messages purposes only if debug is allowed.
 *
 * @since 1.0.0
 */
public final class DebugHelper {
  private static final ComponentLogger LOGGER = ComponentLogger.logger("RegionEvents-Debugger");
  private static boolean enabled;

  private DebugHelper() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantiated.");
  }

  /**
   * Sets the debug-mode as enabled or disabled.
   *
   * @param enable if debug-mode must be enabled.
   * @since 1.0.0
   */
  public static void enable(final boolean enable) {
    enabled = enable;
  }

  /**
   * Uses the logger to send a debug-message if debug-mode is enabled.
   *
   * @param message the message to send.
   * @param values the values to replace in the message.
   * @since 1.0.0
   */
  public static void write(final String message, final Object... values) {
    if (enabled) {
      LOGGER.info(message, values);
    }
  }
}
