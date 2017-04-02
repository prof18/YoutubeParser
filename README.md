# YoutubeParser
[![Download](https://api.bintray.com/packages/prof18/maven/YoutubeParser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

This is an Android library to get video's information from Youtube channels. You can retrieve title, link and thumbnails
of a video from a specific channel. 

## How to
#### Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof.youtubeparser:youtubeparser:1.1'
}
```
#### Use:
```Java
import com.prof.youtubeparser.Parser;
import com.prof.youtubeparser.models.Video;

Parser parser = new Parser();

/*https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg --> channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
                                   ("CHANNEL_ID", RESULT_TO_SHOW, "BROSWER_API_KEY)*/
String url = parser.generateRequest("UCVHFbqXqoYvEWM1Ddxl0QDg", 20, "YOUR_BROSWER_API_KEY");
parser.execute(url);
parser.onFinish(new Parser.OnTaskCompleted() {

    @Override
    public void onTaskCompleted(ArrayList<Video> list) {
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
## Sample app
I wrote a simple app that shows videos from Android Developer Youtube Channel. 

<img src="https://raw.githubusercontent.com/prof18/YoutubeParser/master/Screen.png" width="30%" height="30%">

You can browse the code <a href="https://github.com/prof18/YoutubeParser/tree/master/app"> in this repo.</a> 
You can also download the <a href="https://github.com/prof18/YoutubeParser/blob/master/YoutubeParser.apk"> apk file</a> to try it!

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
