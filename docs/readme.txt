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

[0.2.2]
* Removed usage of IOUtils and instead using java.nio.file.Files 
* [KBM-15] Replaced DB4O with XStreamer so now uses XML format for saving all configuration
* [KBM-15] Also added a command line tool for converting db4o profiles into XStreamer based xml files.

[0.2.1]
* [KBM-13] Fixed bug where scroll wheel causes application to crash

[0.2.0]
* [KMB-1] Added Dropbox Integration
* [KBM-2] Migrated to Java 8
* [KBM-8] Hide Login Box Option (auto-login)
* [KBM-10] Add user settings (to assist with auto-login)
* [KMB-11] Add syncing with dropbox on exit of program

[0.1.10]
* Fixed a bug that was introduced in 0.1.9 with using the scroll wheel on the Nostromo and N52.

[0.1.9]
* Moved from using the linux Event Queue to managing events within Keyboarding Master
* Now works with Ubuntu 13.10.

[0.1.8]
* Updated java version to 1.7.0_51
* Updated udev rules
* Updated Side Image for installer

[0.1.7]
* Fixed unresponsive configuration for the Belkin n52.

[0.1.6]
* Added a new Keyboard Device: Belkin n52
* Added a new Mouse Device: Razer Naga
* Added a delete button on the profile UI for removing an app
* Fixed bugs relating to deleting apps and profiles
* Fixed a bug where multiple profiles were not selected in an app
* Fixed a bug where only the first profile can be selected in an app
* Fixed a bug where a new app could be added without a name or with the same name as an existing app

[0.1.5]
* Added a new Device: Belkin n52te
* Added a button on the profile UI for creating an App
* Removed "New" in the pulldown for creating profiles
* Changed activation of entering text in the description box for mappings
  from mouse hover to mouse click and keyboard enter key
* Implemented the device remove/delete feature from the device menu
* Fixed a bug in the device ui where device icons stacks instead of replace
* Fixed a bug that caused an empty program to be added when a profile was deleted

[0.1.4]
* Added Razer Taipan Support (note, mouse integration is not yet ready; the
x-axis and y-axis is not handled correctly)
* Added device information for the Razer Nostromo & Razer Taipan
* Implemented device remove button by removing the device
* Changed the internal data structures for the profiles
* Bug Fix - if no profile was selected and the display keymap description 
            button was pressed, the program would become unresponsive
* Bug Fix - the device information would not reset properly in the
            add device UI
* Updated to jre 1.7.0-25


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
