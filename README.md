![Perch](/res/perch_header.png)

[![GitHub release](https://img.shields.io/github/release/xgi/perch.svg)](https://github.com/xgi/perch/releases)
[![CircleCI](https://circleci.com/gh/xgi/perch/tree/master.svg?style=svg)](https://circleci.com/gh/xgi/perch/tree/master)

Perch is an augmented reality (AR) Android app for viewing 3D models from [Google Poly](https://poly.google.com).

---

![todo: 3 side-by-side screenshots here](/res/screenshots.png)

---

# Download

Download Perch from [the releases page](https://github.com/xgi/houdoku/releases).

# Testing

Deploying the application for development requires an Android emulator or a connected Android device
with [USB debugging enabled](https://developer.android.com/studio/debug/dev-options.html).
Developing with [Android Studio](https://developer.android.com/studio) is highly recommended, but
the project can be built and tested with the external Gradle tasks:

* `./gradlew installDebug` - builds/install the debug build
* `./gradlew lintDebug` - lint debug build; report generates in `./app/build/reports`
* `./gradlew testDebugUnitTest` - unit test the debug build

# License

[MIT License](https://github.com/xgi/perch/blob/master/LICENSE)
