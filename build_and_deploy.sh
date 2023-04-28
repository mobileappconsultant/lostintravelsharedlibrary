#!/bin/sh

./gradlew assembleIOSFramework || exit
./gradlew publishIOSFramework || exit