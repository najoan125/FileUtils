# FileUtils
[![](https://jitpack.io/v/najoan125/FileUtils.svg)](https://jitpack.io/#najoan125/FileUtils)

This is the File Utils library. it can easy to use file download, read and write from local files or url. and ini files too. also, this library included [JsonUtility](https://github.com/najoan125/JsonUtility) library. so, you can use JsonReader and more!

FileDownloader.download(String url, path) to download, FileUtil.class to read and write from local files and URL! INIFileUtil.class to read from local files and URL!

## gradle(Kotlin) Setup
Add it in your root build.gradle.kts:
```kts
repositories {
    maven { url = uri("https://jitpack.io") }
}
```
```kts
dependencies {
    implementation("com.github.najoan125:FileUtils:1.0.3")
}
```

## Editing
This source is licensed under the **MIT license**. Modify or distribute as you like, **but be sure to cite the source!**
