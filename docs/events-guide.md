# Events Usage

The plugin have maximum two events currently.
* `RegionEnteredEvent` for when a player enters a region.
* `RegionQuitEvent` for when the player leaves the previously entered-region.

Both events provides the involved-player and the `ProtectedRegion` (from WorldGuard) object for the involved-region.

This events's methods to access to their attributes' instances are:
* `#player()` to get the `Player` reference for the involved-player in the event.
* `#region()` to get the `ProtectedRegion` reference for the involved-region.

The `entered-region` event can be cancelled by another plugins if they consider this needed, this means that the plugin will skip any data-store
operation for when a player enters a region, and a debugging-message will be printed (if debug-mode is enabled) into server's console.
