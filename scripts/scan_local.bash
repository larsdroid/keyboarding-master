#!/bin/bash
DIST=~/IDE/netbeans-projects/KeyboardingMaster/dist
java -cp .:$DIST/KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware "$1"

