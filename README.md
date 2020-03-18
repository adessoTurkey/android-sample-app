[![CircleCI](https://circleci.com/gh/adessoTurkey/android-boilerplate.svg?style=shield)](https://circleci.com/gh/adessoTurkey/android-boilerplate)

## Development

### API Key

To run the application you need to supply an API key from [TMBD](https://developers.themoviedb.org/3/getting-started/introduction). When you get the key please add following variable to your local environment:

`` API_KEY_TMDB = Your API Key ``

How to set an environment variable in [Mac](https://medium.com/@himanshuagarwal1395/setting-up-environment-variables-in-macos-sierra-f5978369b255) / [Windows](https://www.architectryan.com/2018/08/31/how-to-change-environment-variables-on-windows-10/)

### Code style

We use [ktlint] for style consistency and thus our gradle builds depends on ``ktlint`` task to succeed, if it fails don't worry you can easily fix it with following gradle task:

`` ./gradlew ktlintFormat ``

You can spot the errors with following gradle task:

`` ./gradlew ktlint ``

If you want to know more about naming convention, code style and more please look at our [android-guideline](https://github.com/adessoTurkey/android-guideline) repository.

## Architecture

- Single Activity
- MVVM Pattern

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from UseCase

**UseCase:** Contains all business rules and they written in the manner of single responsibility principle

**Repository:** Single source of data. Responsible to get data from one or more data sources

<img src="https://raw.githubusercontent.com/adessoTurkey/android-boilerplate/develop/images/architecture-diagram.png" width="500" />

## Tech Stack

- **[Navigation Component](https://developer.android.com/jetpack/androidx/releases/navigation):** Consistent navigation between views
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata):** Lifecycle aware observable and data holder
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):** Holds UI data across configuration changes
- **[Databinding](https://developer.android.com/topic/libraries/data-binding/):** Binds UI components in layouts to data sources
- **[Dagger](https://github.com/google/dagger):** Dependency injector
- **[Coroutines](https://github.com/Kotlin/kotlinx.coroutines):** Asynchronous programming
- **[Glide](https://github.com/bumptech/glide):** Image loading and caching
- **[Lottie](https://github.com/airbnb/lottie-android):** JSON based animations
- **[Retrofit](https://github.com/square/retrofit):** Type safe HTTP client
- **[Moshi](https://github.com/square/moshi):** JSON serializer/deserializer
- **[Ktlint][ktlint]:** Kotlin linter


## License

```
Copyright 2020 adesso Turkey

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

[ktlint]: https://github.com/pinterest/ktlint