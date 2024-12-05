# region-events | [![Codacy Badge](https://app.codacy.com/project/badge/Grade/7f47ec178cd646a1842b68265bc79532)](https://app.codacy.com/gh/aivruu/region-events/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![](https://jitpack.io/v/aivruu/region-events.svg)](https://jitpack.io/#aivruu/region-events)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/aivruu/region-events/build.yml)
![GitHub License](https://img.shields.io/github/license/aivruu/region-events)
![GitHub commit activity](https://img.shields.io/github/commit-activity/t/aivruu/region-events)

`RegionEvents` is a very modern-plugin that manages when a player enters or leaves a `WorldGuard` region in their world. This plugin provides an API to work
with players that are in a region, provide functions to search regions at a world or location, and listen to events when a player enters or leaves a region.

This plugin was made to run with `Paper (or forks)` on versions 1.21.3 and newer.

## Features
* Listen to events when player enters or leaves a region.
* Provide useful functions to search possible regions at a specific world or location, or get the players that are in a region.
* Async, plugin uses an async-task to check by players' movements in/out of regions prior region's action's event firing.
* Easy to use.

## Documentation
Plugin's code itself is already documented for a good understanding of what plugin does, and if another developers want to contribute, know where and how they can
do it, either way, there's guides to know how to implement and use the Plugin's API classes, utilities and events.

* [Plugin's API import](https://github.com/aivruu/region-events/blob/main/docs/install-guide.md); For plugin's API installation.
* [API Instance Access](https://github.com/aivruu/region-events/blob/main/docs/api-access-guide.md); For know how to get a plugin's API instance.
* [RegionHelper Usage](https://github.com/aivruu/region-events/blob/main/docs/region-helper-usage-guide.md); For summary of all the functions available for regions-management.
* [Available Events](https://github.com/aivruu/region-events/blob/main/docs/events-guide.md); For plugin's available events.

## Building
The plugin's dependency-manager structure uses Gradle-Kotlin and requires Java 21+ for compilement.
```
git clone git@github.com:aivruu/region-events
cd region-events
./gradlew shadowJar
```
