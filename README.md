# YoutubeParser
[![Download](https://api.bintray.com/packages/prof18/maven/YoutubeParser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

This is an Android library to get information of videos from Youtube channels. You can retrieve title, link and thumbnail of a video from a specific channel. You can also get the statistics of a video like view, like, dislike, favorite and comment count. Now it is also possible to load more videos by making a new request.

## How to
#### Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof.youtubeparser:youtubeparser:2.0'
}
```
#### Usage:
##### To load videos from a specific channel:
```Java
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.videos.Video;

Parser parser = new Parser();

//(CHANNEL_ID, NUMBER_OF_RESULT_TO_SHOW, ORDER_TYPE ,BROSWER_API_KEY)
//https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
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

##### To load more videos from the same channel:    
```Java
String url = parser.generateMoreDataRequest(CHANNEL_ID, 20, Parser.ORDER_DATE, API_KEY, nextToken);
```
Remember that this request can be made only AFTER the a previous one, because you need the nextPageToken

##### To get the statistics of a video:
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

You can browse the code <a href="https://github.com/prof18/YoutubeParser/tree/master/app"> in this repo.</a>
You can also download the <a href="https://github.com/prof18/YoutubeParser/blob/master/YoutubeParser.apk"> apk file</a> to try it!

## Changelog
- 22 June 2016 - Big update: now you can load more video and get the statistic of a video - Version 2.0
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
