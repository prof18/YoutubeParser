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
import com.prof.youtubeparser.models.stats.Item;
import com.prof.youtubeparser.models.stats.Main;
import com.prof.youtubeparser.models.stats.Statistics;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class parses the statistics of a video.
 * <p>
 * Created by Marco Gomiero on 19/06/2017.
 */

public class VideoStats extends AsyncTask<String, Void, String> {

    private OnTaskCompleted onComplete;

    /**
     * This method generate the url to retrieve statistic of a video
     *
     * @param videoID The ID of the youtube video
     * @param key     Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get video statistic
     */
    public String generateStatsRequest(String videoID, String key) {

        String urlString = "https://www.googleapis.com/youtube/v3/videos?key=";
        urlString = urlString + key + "&part=statistics&id=" + videoID;
        return urlString;
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ulr[0])
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
            onComplete.onError();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if (result != null) {

            try {
                Gson gson = new GsonBuilder().create();
                Main data = gson.fromJson(result, Main.class);

                List<Item> itemList = data.getItems();
                Statistics stats = itemList.get(0).getStatistics();

                Log.i("YoutubeParser", "Video stats parsed correctly");
                onComplete.onTaskCompleted(stats);

            } catch (Exception e) {
                e.printStackTrace();
                onComplete.onError();
            }
        } else
            onComplete.onError();
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Statistics stats);
        void onError();
    }
}
