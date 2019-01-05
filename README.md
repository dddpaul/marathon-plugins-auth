# Authentication and authorization plugin for Marathon

[![Build Status](https://travis-ci.com/dddpaul/marathon-plugins.svg?branch=master)](https://travis-ci.com/dddpaul/marathon-plugins)

This plugin is based on HTTP basic authentication. Authentication and authorization is performed against credentials in JSON configuration file.

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.
It's specified like  `compileOnly "mesosphere.marathon:plugin-interface_2.11:1.5.1"` for Gradle.
But Mesosphere [Maven repository](http://downloads.mesosphere.io/maven) may lack some Marathon versions.
In this case you have to build Marathon plugin interface library by yourself:

1. Go grab the [Marathon sources](https://github.com/mesosphere/marathon).
2. Switch to preferred tag/version (`git checkout v1.5.1` for example).
3. Build and publish to local Maven repo with `sbt publishM2`.

## Package

To build the package run this command: `./gradlew clean shadowJar`
The resulting jars with all dependencies are put into the plugin directories: `build/libs`.
This directories can be used directly as plugin directory for Marathon.

## Using a Plugin

1. Run `./gradlew clean shadowJar` in the repository's root directory.
2. Locate the Plugin configuration file [plugin-conf.json](src/main/resources/plugin-conf.json).
3. Start Marathon with the following flags: `--plugin_dir <plugin_path>/build/libs --plugin_conf <path_to_the_plugin_config_file>`.
4. Or even better - you can use [docker-compose file](src/test/resources/docker-compose.yml).

## Actions

Some hints and caveats:

* `ViewGroup` on `/` is necessary to `Create Application` in root space
* `CreateRunSpec` on `group` is necessary to create this group
