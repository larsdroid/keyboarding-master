#!/bin/bash
DIST=~/IDE/netbeans-projects/KeyboardingMaster/dist
java -cp .:$DIST/.:$DIST/KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native -jar $DIST/KeyboardingMaster.jar
