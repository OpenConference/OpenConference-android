# OpenConference

This repository offers an android app that can be used by any conference to display information like sessions, speaker etc. about the conference in a native android app

Originally this app has been build for droidcon Berlin, but the app can be styled and configured freely so that it can be used by other conferences as well.

[![Build Status](https://travis-ci.org/OpenConference/OpenConference-android.svg?branch=master)](https://travis-ci.org/OpenConference/OpenConference-android)

# For your conference

The documentation is not available yet, but basically there are two ways to create your own android app for your conference (based on this source code):

1. Create a product flavor for your conference: Android's gradle plugin allows us to have multiple product flavors. If you use product flavors, you can benefit from some features like CI and testing. If you need help, please create an issue here on github.

2. Fork the whole repository and do your own thing.


# Connect to your backend
This app is has been built with changeable, modular and components in mind. We use dagger 2 for dependency injection and so you can override almost all components to match your needs. The most important one is: [BackendScheduleAdapter](https://github.com/OpenConference/OpenConference-android/blob/master/app/src/main/java/com/openconference/model/backend/schedule/BackendScheduleAdapter.kt) where you define how to load data from your backend (i.e. by using Retrofit).

Custom styling can be applied by overriding default xml styles and layouts files.


# License
```
Copyright 2016 Open Conference

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


