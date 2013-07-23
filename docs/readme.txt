=== Notes === 
This release works on 32-bit & 64-bit linux systems.
keyboarding master depends on jinput with modifications.  My modifications
to jinput only support linux at this time.

=== Instructions For Use ===
BitRocks InstallBuilder automatically installs all necessary components
for keyboarding master to function with your devices including the java
runtime environment (JRE).  

To run, double click on the desktop icon or
cd <kbmaster install dir>
./run.bash

To uninstall
cd <kbmaster install dir>
sudo ./uninstall

== Versions ===

[0.1.3]
* Added System Tray support

[0.1.2]
* Added import/export of profiles
* Added proper delete of profile support

[0.1.1]
* Fixed a performance issue with opening the configuration menu multiple times.
* When the configuration menu is opened, the profile showing in the device menu will be used.

=== Known Bugs / Disabled Features ===
This is a beta release so there are none bugs/issues.

BUG: Windows do not popup in the center of the screen 
Remedy: This is a JavaFX bug and will be fixed in the next release of java 7u40 due in August, 2013

BUG: If Caps lock is on, buttons do not respond
Remedy: This is a JavaFX bug and hopefully will be fixed in the next release of java 7u40
Temporary Solution: User must turn caps lock off

Disabled: Details button in Device Menu disabled
Reason: planned for a future release

Disabled: Edit Mode in Configuration UI disabled
Reason: planned for a future release
