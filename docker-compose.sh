#!/usr/bin/env bash

usage="Usage: docker-compose.sh up|down|cluster-up|cluster-down"

function prepare() {
    yq . src/main/resources/plugin-conf.yml > src/main/resources/plugin-conf.json
    ./gradlew clean shadowJar
}

case "$1" in
    up)
        prepare
        docker-compose -f src/test/resources/docker-compose.yml up
        ;;
    down)
        docker-compose -f src/test/resources/docker-compose.yml down
        ;;
    cluster-up)
        prepare
        docker-compose -f src/test/resources/docker-compose-cluster.yml up
        ;;
    cluster-down)
        docker-compose -f src/test/resources/docker-compose-cluster.yml down
        ;;
    *)
        echo ${usage}
esac
