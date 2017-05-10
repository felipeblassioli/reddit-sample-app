# reddit-sample-app [![CircleCI](https://circleci.com/gh/felipeblassioli/reddit-sample-app.svg?style=svg)](https://circleci.com/gh/felipeblassioli/reddit-sample-app) [![CircleCI](https://circleci.com/gh/felipeblassioli/reddit-sample-app.svg?style=svg)](https://circleci.com/gh/felipeblassioli/reddit-sample-app)
Sample MVP using the Mosby library.

## Continuos Integration

CircleCI runs "./gradlew test jacocoTestReport assembleRelease" testing, generating a signed releaseApk and the coverage report.

**Highlights**

  - Disable pre-dexing for CircleCI builds at *buildsystem/ci.gradle*
  - Download the private key for signing using *buildsystem/download_keystore.sh*

**Environment Variables**

| Variable | Description |
| --- | --- |
| KEY_PASSWORD | Signing's private key password |
| KEY_ALIAS | Signing's keyAlias |
| KEYSTORE | The target path to download the keystore |
| KEYSTORE_URI | download_keystore.sh fetches the keystore from this url |
| KEYSTORE_PASSWORD | Signing's keystore password |
| CIRCLECI | If true disables pre-dexing |

**Apk Signing**

```
release {
    storeFile file(System.getenv("KEYSTORE") ?: "keystore.jks")
    storePassword System.getenv("KEYSTORE_PASSWORD")
    keyAlias System.getenv("KEY_ALIAS")
    keyPassword System.getenv("KEY_PASSWORD")
}
```
