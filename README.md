# Authentication and authorization plugin for Marathon

[![Build Status](https://travis-ci.com/dddpaul/marathon-plugins.svg?branch=master)](https://travis-ci.com/dddpaul/marathon-plugins)

This plugin is based on HTTP basic authentication and it's heavily inspired by [Example plugins for Marathon](https://github.com/mesosphere/marathon-example-plugins) project. Authentication and authorization is performed against credentials in JSON configuration file.

## Marathon Plugin Dependency

The Marathon plugin interface is needed to compile this package.
It's specified like  `compileOnly "mesosphere.marathon:plugin-interface_2.11:1.5.11"` for Gradle.
But Mesosphere [Maven repository](http://downloads.mesosphere.io/maven) may lack some Marathon versions.
In this case you have to build Marathon plugin interface library by yourself:

1. Go grab the [Marathon sources](https://github.com/mesosphere/marathon).
2. Switch to preferred tag/version (`git checkout v1.5.11` for example).
3. Build and publish to local Maven repo with `sbt publishM2; sbt plugin-interface/publishM2`.

## Building a Plugin

To build the plugin run this command: `./gradlew clean shadowJar`.
The resulting uber-jar with all dependencies is put into the `build/libs` directory.
This directory can be used directly as plugin directory for Marathon.

Use `scalaVersion` and `marathonVersion` to specify versions, for example `-PscalaVersion=2.11 -PmarathonVersion=1.5.11`.

## Using a Plugin

1. Run `./gradlew clean shadowJar` in the repository's root directory or grab compile jar from [Bintray](https://dl.bintray.com/dddpaul/maven/com/github/dddpaul/marathon/marathon-plugins-auth/).
2. Locate and update the Plugin configuration file [plugin-conf.json](src/main/resources/plugin-conf.json).
3. Start Marathon with the following flags: `--plugin_dir <plugin_path>/build/libs --plugin_conf <path_to_the_plugin_config_file>`.
4. Or even better - you can use [docker-compose file](src/test/resources/docker-compose.yml) or [shell script wrapper](docker-compose.sh).

## Configuration

Marathon plugin subsystem consumes configuration in [JSON format](src/main/resources/plugin-conf.json). But for the sake of brevity and readability configuration could be managed in [YAML format](src/main/resources/plugin-conf.yml) and converted to JSON with [yq](https://github.com/kislyuk/yq):

```
yq . src/main/resources/plugin-conf.yml > src/main/resources/plugin-conf.json
```

## Marathon actions

See full list of actions in [Action](src/main/java/com/github/dddpaul/marathon/plugin/auth/entities/Action.java) enum class.

Some hints and caveats:

* `ViewGroup` on `/` is necessary to `Create Application` in root space
* `CreateRunSpec` on `group` is necessary to create this group
* `ViewResource` on [AuthorizedResource](https://github.com/mesosphere/marathon/blob/master/plugin-interface/src/main/scala/mesosphere/marathon/plugin/auth/AuthorizedResource.scala) case objects (internal resources) is checked on almost every request; it has to be permitted
