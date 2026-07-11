#!/usr/bin/env sh
mkdir -p gradle/wrapper
curl -sLo gradle/wrapper/gradle-wrapper.jar "https://raw.githubusercontent.com/gradle/gradle/v7.4.2/gradle/wrapper/gradle-wrapper.jar"
java -jar gradle/wrapper/gradle-wrapper.jar "$@"
