#!/bin/bash
function upload {
    sh scripts/upload-to-bitbucket.sh $USER $PASSWORD /conzar/keyboarding-master/downloads $1
}

function usage {
echo "Usage: upload <username> <password>"
}

if [ -z "$1" ] 
then
    usage
  exit 1
fi

if [ -z "$2" ] 
then
    usage
  exit 1
fi

USER=$1
PASSWORD=$2

# get version
VERSION=$(java -cp dist/kbmaster/KeyboardingMaster.jar:scripts GetVersion)
echo "Building Keyboarding Master $VERSION"

FILES=dist/kbmaster-$VERSION-*
for f in $FILES
do
    echo "Uploading $f ..."
    upload $f
done
