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

## Use FileDownload instance
You can see a demo source [here](https://github.com/najoan125/FileUtils/blob/master/src/test/java/FileDownloadTest.java)

## Editing
This source is licensed under the **MIT license**. Modify or distribute as you like, **but be sure to cite the source!**

## Dependencies
- [Apache HttpClient](https://hc.apache.org/httpcomponents-client-4.5.x/index.html)
- [Apache Commons Configuration](https://commons.apache.org/proper/commons-configuration/)
- [Apache Commons Codec](https://commons.apache.org/proper/commons-codec/)
- [JSON In Java](https://github.com/stleary/JSON-java)