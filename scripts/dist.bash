#!/bin/bash
echo "Prepares the distribution"


# The directory used by netbeans to create the jar
DIST_DIR=~/Software/IDE/NetBeansProjects/kbm/dist
DEST_DIR=dist/kbmaster

mkdir dist
rm -rf $DEST_DIR
#rm dist/kbmaster.tar.bz2

mkdir dist/kbmaster
cp $DIST_DIR/KeyboardingMaster.jar dist/kbmaster/.
cp -r $DIST_DIR/lib $DEST_DIR/.
cp -r libs/native $DEST_DIR/lib/.
cp -r libs/jre/jre1.7.0_25-linux-x64/java-linux-x64 $DEST_DIR/lib/.
cp -r libs/jre/jre1.7.0_25-linux/java-linux $DEST_DIR/lib/.
cp scripts/run.bash $DEST_DIR/.
cp COPYING $DEST_DIR/.
cp docs/readme.txt $DEST_DIR/.
cp art_assets/banner\ and\ logo/logo_48x48.png $DEST_DIR/desktop_icon.png
