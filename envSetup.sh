APP_GRADLE_PROPERTIES="./app/gradle.properties"
export APP_GRADLE_PROPERTIES
echo "App Gradle Properties should exist at $APP_GRADLE_PROPERTIES"

if [ ! -f "$APP_GRADLE_PROPERTIES" ]; then
    echo "App Gradle Properties does not exist"

    echo "Creating App Gradle Properties file..."
    touch $APP_GRADLE_PROPERTIES

    echo "Writing PolyAPIKey to app/gradle.properties..."
    echo "PolyAPIKey=\"$PolyAPIKey\"" >> $APP_GRADLE_PROPERTIES
fi

# decode keystore to filesystem
echo $ENCODED_KEYSTORE | base64 -di >> ${HOME}/perch/upload-keystore.jks
echo 'export KEYSTORE=${HOME}/perch/upload-keystore.jks' >> $BASH_ENV