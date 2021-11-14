# YoutubeParser
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.prof18.youtubeparser/youtubeparser/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.prof18.youtubeparser/youtubeparser)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

This is an Android library to get information of videos from Youtube channels. You can retrieve title, link and thumbnail of a video from a specific channel. You can also get the statistics of a video like view, like, dislike, favorite and comment count. Now it is also possible to load more videos by making a new request.

## ⚠️ Important Notice

The library artifacts have been moved to MavenCentral. The group id is changed from `com.prof.youtubeparser` to `com.prof18.youtubeparser`.
Be sure to add the gradle dependency to your root `build.gradle` file.
```
allprojects {
    repositories {
        mavenCentral()
    }
}
```

## How to
### Import:
The library is uploaded on MavenCentral, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof18.youtubeparser:youtubeparser:<latest-version>'
}
```
### Usage:

Starting from the version 3.x, the library has been completely rewritten using Kotlin and the coroutines. However, The compatibility with Java has been maintained and the same code of the versions 2.x (and below) can be used.  

If you are using Kotlin you need to [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) the coroutine that retrieves the videos. 


#### To load videos from a specific channel:

```kotlin
import com.prof.youtubeparser.Parser
import com.prof.youtubeparser.models.videos.Video

coroutineScope.launch(Dispatchers.Main) {
    
    val parser = Parser()
    
    //(CHANNEL_ID, NUMBER_OF_RESULT_TO_SHOW, ORDER_TYPE ,BROSWER_API_KEY)
    //https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
    //The maximum number of result to show is 50
    //ORDER_TYPE --> by date: "Parser.ORDER_DATE" or by number of views: "ORDER_VIEW_COUNT"  
    val requestUrl = parser.generateRequest(
            channelID = channelID,
            maxResult = 20,
            orderType = Parser.ORDER_DATE,
            key = BuildConfig.KEY
    )
    try {
        val result = parser.getVideos(requestUrl)
        nextToken = result.nextToken
        videoList = result.videos
    } catch (e: Exception) {
        // handle the exception
    }
}
```

If you are still using Java, the code is very similar to the older versions of the library:

```java
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.videos.Video;

Parser mParser = new Parser();
mParser.onFinish(new Parser.OnTaskCompleted() {
    @Override
    public void onTaskCompleted(@NonNull List<Video> list, @NonNull String nextPageToken) {
        // The list contains all the videos. For example you can use it for your adapter.
        // The next page token can be used to retrieve more videos
    }

    @Override
    public void onError(@NonNull Exception e) {
        // handle the exception
    }
});

//(CHANNEL_ID, NUMBER_OF_RESULT_TO_SHOW, ORDER_TYPE ,BROSWER_API_KEY)
//https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
//The maximum number of result to show is 50
//ORDER_TYPE --> by date: "Parser.ORDER_DATE" or by number of views: "ORDER_VIEW_COUNT"  
String requestUrl = mParser.generateRequest(
            CHANNEL_ID,
            20,
            Parser.ORDER_DATE,
            BuildConfig.KEY
    );

mParser.execute(requestUrl);

```

##### Version 2.2 and belows:

```java
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.videos.Video;

Parser parser = new Parser();

//(CHANNEL_ID, NUMBER_OF_RESULT_TO_SHOW, ORDER_TYPE ,BROSWER_API_KEY)
//https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
//The maximum number of result to show is 50
//ORDER_TYPE --> by date: "Parser.ORDER_DATE" or by number of views: "ORDER_VIEW_COUNT"  
String url = parser.generateRequest(CHANNEL_ID, 20, Parser.ORDER_DATE, API_KEY);
parser.execute(url);
parser.onFinish(new Parser.OnTaskCompleted() {

    @Override
    public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
      //what to do when the parsing is done
      //the ArrayList contains all video data. For example you can use it for your adapter
    }

    @Override
    public void onError() {
        //what to do in case of error
    }
});
```
To create a BROSWER API KEY you can follow
<a href="https://support.google.com/cloud/answer/6158862?hl=en#creating-browser-api-keys"> this guide.</a>

#### To load more videos from the same channel:

To load more videos, you can use the same method described above but with the following URL: 

```kotlin
val requestUrl = parser.generateMoreDataRequest(
                    channelID = channelID,
                    maxResult = 20,
                    orderType = Parser.ORDER_DATE,
                    key = BuildConfig.KEY,
                    nextToken = pageToken
            )
```

    
```Java
String requestUrl = mParser.generateMoreDataRequest(
                CHANNEL_ID,
                20,
                Parser.ORDER_DATE,
                BuildConfig.KEY,
                mNextToken
        );
```
Remember that this request can be made only AFTER the a previous one, because you need the nextPageToken. Remember also that every request can get a maximum of 50 elements.

#### To get the statistics of a video:

If you are using Kotlin you need to [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) the coroutine that retrieves the videos. 


```kotlin
import com.prof.youtubeparser.VideoStats
import com.prof.youtubeparser.models.videos.Video

val videoStats = VideoStats()
val requestUrl = videoStats.generateStatsRequest(
        videoID = videoId,
        key = BuildConfig.KEY
)
coroutineScope.launch(Dispatchers.Main) {
    try {
        _stats.postValue(videoStats.getStats(requestUrl))
    } catch (e: Exception) {
        e.printStackTrace()
        _snackbar.value = "An error has occurred. Please retry"
    }
}
```

If you are still using Java, the code is very similar to the older versions of the library:

```java
import com.prof.youtubeparser.VideoStats;
import com.prof.youtubeparser.models.stats.Statistics;

VideoStats videoStats = new VideoStats();
videoStats.onFinish(new VideoStats.OnTaskCompleted() {
    @Override
    public void onTaskCompleted(@NonNull Statistics stats) {
        //Here you can set the statistic to a Text View for instance
        
        //for example:
        String body = "Views: " + stats.getViewCount() + "\n" +
                    "Like: " + stats.getLikeCount() + "\n" +
                    "Dislike: " + stats.getDislikeCount() + "\n" +
                    "Number of comment: " + stats.getCommentCount() + "\n" +
                    "Number of favourite: " + stats.getFavoriteCount();
    }

    @Override
    public void onError(@NonNull Exception e) {
        // handle the exception
    }
});

String requestUrl = videoStats.generateStatsRequest(videoId, BuildConfig.KEY);
videoStats.execute(requestUrl);
```

##### Version 2.2 and belows:


```Java
import com.prof.youtubeparser.VideoStats;
import com.prof.youtubeparser.models.stats.Statistics;

VideoStats videoStats = new VideoStats();
String url = videoStats.generateStatsRequest(videoId, API_KEY);
videoStats.execute(url);
videoStats.onFinish(new VideoStats.OnTaskCompleted() {
  @Override
  public void onTaskCompleted(Statistics stats) {
      //Here you can set the statistic to a Text View for instance

      //for example:
      String body = "Views: " + stats.getViewCount() + "\n" +
                    "Like: " + stats.getLikeCount() + "\n" +
                    "Dislike: " + stats.getDislikeCount() + "\n" +
                    "Number of comment: " + stats.getCommentCount() + "\n" +
                    "Number of favourite: " + stats.getFavoriteCount();
  }

  @Override
  public void onError() {
      //what to do in case of error
  }
});
```
## Sample app
I wrote a simple app that shows videos from Android Developer Youtube Channel.

<img src="https://raw.githubusercontent.com/prof18/YoutubeParser/master/Screen.png" width="30%" height="30%">

The sample is written both in Kotlin and Java. You can browse the Kotlin code [here](https://github.com/prof18/YoutubeParser/tree/master/samplekotlin) and the Java code [here](https://github.com/prof18/YoutubeParser/tree/master/samplejava)

Please use the issues tracker only to report issues. If you have any kind of question you can ask them on [the blog post](https://medium.com/@marcogomiero/new-big-update-for-youtube-parser-video-stats-and-much-more-79dde73f21e6)

## Changelog
From version 1.4.4 and above, the changelog is available in the [release section.](https://github.com/prof18/YoutubeParser/releases)

- 21 May 2019 - Rewrote library with Kotlin - Version 3.0.0
- 14 December 2017 - Improved Error Management - Version 2.2
- 12 August 2017 - Fixed Library Manifest - Version 2.1
- 22 June 2017 - Big update: now you can load more video and get the statistic of a video - Version 2.0
- 17 June 2016 - Fixed a bug on Locale - Version 1.1
- 15 June 2016 - First release  - Version 1.0

## License
```
   Copyright 2016 Marco Gomiero

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

## Apps using RSS Parser
If you are using Youtube Parser in your app and would like to be listed here, please open a pull request!

<details>
  <summary>List of Apps using Youtube Parser</summary>

* [Your App Name](www.yourapplink.com)

</details>
