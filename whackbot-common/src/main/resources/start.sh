#!/bin/bash

echo Starting WhackBot...
screen -dmS WhackBot java -javaagent:WhackBot-${version}.jar -jar WhackBot-${version}.jar
