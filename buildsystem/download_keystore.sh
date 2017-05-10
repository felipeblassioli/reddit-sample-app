# Source: https://raw.githubusercontent.com/halfhp/androidplot/master/misc/download_keystore.sh
# use curl to download a keystore from $KEYSTORE_URI, if set,
# to the path/filename set in $KEYSTORE.
if [[ $KEYSTORE && ${KEYSTORE} && $KEYSTORE_URI && ${KEYSTORE_URI} ]]
then
    echo "Keystore detected - downloading..."
    curl -L -o ${KEYSTORE} ${KEYSTORE_URI}
else
    echo "Keystore uri not set.  .APK artifact will not be signed."
fi
