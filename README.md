# Profile Demo App

![Android CI with Gradle](https://github.com/tomkoptel/sliide-task/workflows/Android%20CI%20with%20Gradle/badge.svg)

## [Get the App](https://github.com/tomkoptel/sliide-task/actions/runs/566599172)
You can find the latest version inside [Actions Tab](https://github.com/tomkoptel/sliide-task/actions).

## Architecture
So far it is a single module setup with `platform` Gradle submodule to align version configurations.

The basic idea is to keep 3 levels of responsibility:
* data - holds implementation of data sources and respective mappers for domain layer.
* domain - or interface, or contract layer defines pure interfaces and data classes.
* presentation - consumes domain layer directly has no knowledge about data layer.

# App
![App](https://github.com/tomkoptel/sliide-task/blob/master/app.gif?raw=true)
