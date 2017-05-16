# Reddit Sample App [![CircleCI](https://circleci.com/gh/felipeblassioli/reddit-sample-app.svg?style=svg)](https://circleci.com/gh/felipeblassioli/reddit-sample-app) [![codecov](https://codecov.io/gh/felipeblassioli/reddit-sample-app/branch/master/graph/badge.svg)](https://codecov.io/gh/felipeblassioli/reddit-sample-app)

Sample MVP using the Mosby library.

## Overview

This application has two different screens:

  1. *features.posts*: shows data from https://www.reddit.com/r/Android/new/.json
  2. *features.details*: show data from https://www.reddit.com/r/Android/<link_id>/comments/.json

Posts             |  Details 
:-------------------------:|:-------------------------:
![alt text](https://raw.githubusercontent.com/felipeblassioli/reddit-sample-app/master/screenshots/posts.png "Posts Screen")  | ![alt text](https://raw.githubusercontent.com/felipeblassioli/reddit-sample-app/master/screenshots/details.png "Details Screen")

Some relevant features are:

  1. Posts and Comment's recycler view support:
    - TapToRetry
    - Swipe to refresh
    - Endless Scrolling

## Testing

Unit testing relies on *Mockito 2*, *AssertJ* and on *Roboletric 3* (for views).
Instrumentation testing relies on *Espresso*.

To generate a coverage report:
```
./gradlew jacocoTestReport
```

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
