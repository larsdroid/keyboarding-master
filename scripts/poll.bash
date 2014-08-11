#!/bin/bash
#java -cp .:KeyboardingMaster.jar:lib/jinput.jar -Djava.library.path=lib/native com.monkygames.kbmaster.util.ScanHardware $1
#java -cp .:KeyboardingMaster.jar:lib/jinput.jar -Djava.library.path=lib/native com.monkygames.kbmaster.util.ScanHardware 'Razer Razer Nostromo'
java -cp .:dist/kbmaster/KeyboardingMaster.jar:libs/input/jinput.jar -Djava.library.path=libs/native com.monkygames.kbmaster.util.ScanHardware 'Razer Razer Nostromo'

