# Plugins for Marathon

[![Build Status](https://travis-ci.com/dddpaul/marathon-plugins.svg?branch=master)](https://travis-ci.com/dddpaul/marathon-plugins)

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.
It's specified like  `compileOnly "mesosphere.marathon:plugin-interface_2.11:1.5.1"` for Gradle.
But Mesosphere [Maven repository](http://downloads.mesosphere.io/maven) may lack some Marathon versions.
In this case you have to build Marathon plugin interface library by yourself:

1. Go grab the [Marathon sources](https://github.com/mesosphere/marathon).
2. Switch to preferred tag/version (`git checkout v1.5.1` for example).
3. Build and publish to local Maven repo with `sbt publishM2`.


## Package

To build the package run this command: `./gradlew clean build`
This will compile and package all plugins.
The resulting jars with all dependencies are put into the plugin directories: `build/libs`.
This directories can be used directly as plugin directory for Marathon.

# Using a Plugin
1. Run `./gradlew clean build` in the repository's root directory.
2. Locate the Plugin configuration file (look at the Plugin's README.md for a hint)).
3. Start Marathon with the following flags: `--plugin_dir <plugin_path>/build/libs --plugin_conf <path_to_the_plugin_config_file>`.
4. Or even better - you can use [docker-compose file](fileauth/src/test/resources/docker-compose.yml).

## Plugins

### fileauth

Authentication and Authorization Plugin performed against htpasswd file. See [README.md](fileauth/README.md).
