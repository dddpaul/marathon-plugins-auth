#!/usr/bin/env bash
yq . src/main/resources/plugin-conf.yml > src/main/resources/plugin-conf.json
./gradlew clean shadowJar
docker-compose -f src/test/resources/docker-compose.yml up
