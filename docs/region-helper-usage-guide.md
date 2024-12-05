# How to use the plugin's RegionHelper utility-class

The plugin's API provides an util-class to check regions and more, this class is used by the plugin to obtain all the regions in a single or multiple-worlds for
management and event-firing.

This class provides several functions for in-region players checking or getting, or get the regions that are in a location, single-world or multiple-worlds, these
functions are:
* `#isInRegion(Location, String)` Returns whether the location-given is at the specified-region (name-required).
  
* `#playersInRegion(Map<String, ProtectedRegion>, String)` Returns a `List` with the players that are in the specified-region, the `cache` parameter is a Map (which can be
  obtained with `RepositoryModel#findAllSync`) with the players' that're in a region currently.
  
* `#searchLocal(Player, String)` May return a `ProtectedRegion` object for the specified-region or `null` if there aren't regions in the world, or `null` if no regions were found.
  
* `#searchGlobally(String)` Searchs across all server's worlds by the specified-region and return a `ProtectedRegion` object if found, or `null` if no regions were found or given-region
  doesn't exist.

* `#searchAtWorld(String)` Returns a `Map` with all the regions that're in the specified-world, or `null` if world doesn't exist.

* `#searchAtWorld(World)` Same as `#searchAtWorld(String)` but requires a `World` object instead, or `null` if no regions were found.

* `#searchAtLocation(World, Location)` Returns a `Set` with all the regions that're in the world and in the specified-location, or `null` if no regions were found.
