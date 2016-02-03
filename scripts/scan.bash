#!/bin/bash
## maybe running from dev env
java -cp .:KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware >& HardwareScan.log

## running from deployment
#java -cp .:KeyboardingMaster.jar:lib/input/jinput.jar -Djava.library.path=lib/native com.monkygames.kbmaster.util.ScanHardware >& HardwareScan.log


