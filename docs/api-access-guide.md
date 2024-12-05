# How to get the plugin's API reference

The plugin has a interface-model for access to the API classes, to access to this API you must use the `RegionEventsProvider#get` class's function to get the API reference.

You must notice that if you try to get the API reference when `region-events` plugin isn't fully enabled yet, the function will throw an `IllegalStateException` error, to
avoid this, you must call to this function preferably during the `onEnable` function.

Once you have the API instance, you can access to all the interface's methods. The `RegionEvents` interface's available methods are:
* `#regionUpdaterManager` Returns a `RegionUpdaterManager` interface instance.
* `#enteredRegionsCacheRepository` Returns the `RepositoryModel` implementation used for entered-regions in-cache management.
* `#playerRegionInformationController` Returns a `PlayerRegionInformationController` class instance.

```java
public final class TestPlugin extends JavaPlugin {
  private RepositoryModel<ProtectedRegion> enteredRegionsCache;

  @Override
  public void onEnable() {
    final var regionEvents = RegionEventsProvider.get();
    this.enteredRegionsCache = regionEvents.enteredRegionsCacheRepository();
  }
}
```
