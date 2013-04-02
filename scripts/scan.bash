#!/bin/bash
java -cp .:KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware >& HardwareScan.log

