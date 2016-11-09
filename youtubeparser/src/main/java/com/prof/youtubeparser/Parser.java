/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*   
*/

package com.prof.youtubeparser;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prof.youtubeparser.models.High;
import com.prof.youtubeparser.models.Id;
import com.prof.youtubeparser.models.Item;
import com.prof.youtubeparser.models.Main;
import com.prof.youtubeparser.models.Snippet;
import com.prof.youtubeparser.models.Thumbnails;
import com.prof.youtubeparser.models.Video;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Marco Gomiero on 6/9/16.
 */
public class Parser extends AsyncTask<String, Void, String>{

    static ArrayList<Video> videos = new ArrayList<>();
    private static final String TAG = "ObtainVideo";

    private OnTaskCompleted onComplete;

    /**
     * This method generate the url that retrieves Youtube video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     *                  channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video that you want to show
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     *
     * @return Return the url required to get data
     */
    public String generateRequest(String channelID, int maxResult, String key ) {

        String urlString = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=";
        urlString = urlString + channelID + "&maxResults=" + maxResult + "&order=date&key=" + key;
        return urlString;
    }

    public interface OnTaskCompleted{
        void onTaskCompleted(ArrayList<Video> list);
        void onError();
    }

    public void onFinish (OnTaskCompleted onComplete ) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ulr[0])
                .build();

        try {

            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {

            e.printStackTrace();
            onComplete.onError();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        try {

            Gson gson = new GsonBuilder().create();
            Main data = gson.fromJson(result, Main.class);

            //Begin parsing Json data
            List<Item> itemList = data.getItems();

            for (int i = 0; i < itemList.size(); i++) {

                Item item = itemList.get(i);
                Snippet snippet = item.getSnippet();

                Id id = item.getId();

                Thumbnails image = snippet.getThumbnails();

                High high = image.getHigh();

                String title = snippet.getTitle();
                String videoId = id.getVideoId();
                String imageLink = high.getUrl();
                String sDate = snippet.getPublishedAt();

                Locale.setDefault(Locale.getDefault());
                TimeZone tz = TimeZone.getDefault();
                Calendar cal = Calendar.getInstance(tz);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setCalendar(cal);
                cal.setTime(sdf.parse(sDate));
                Date date = cal.getTime();

                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMMM yyyy");
                String pubDateString = sdf2.format(date);

                Video tempVideo = new Video(title, videoId, imageLink, pubDateString);
                videos.add(tempVideo);
            }

            Log.i("YoutubeParser", "Youtube data parsed correctly!");

            onComplete.onTaskCompleted(videos);

        } catch (Exception e) {

            e.printStackTrace();
            onComplete.onError();
        }
    }
}