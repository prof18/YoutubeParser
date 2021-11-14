/*
*   Copyright 2019 Marco Gomiero
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

package com.prof.youtubeparser.core

import com.google.gson.GsonBuilder
import com.prof.youtubeparser.models.stats.Statistics
import com.prof.youtubeparser.models.videos.internal.Main
import com.prof.youtubeparser.models.videos.Video
import java.text.SimpleDateFormat
import java.util.*

internal object CoreJsonParser {

    @Throws(Exception::class)
    fun parseVideo(json: String): ParsingResult {
        val videos = mutableListOf<Video>()

        val gson = GsonBuilder().create()
        val data = gson.fromJson(json, Main::class.java)

        //Begin parsing Json data
        val itemList = data.items
        val nextToken = data.nextPageToken

        for (i in itemList.indices) {

            val item = itemList[i]
            val snippet = item.snippet

            val id = item.id

            val image = snippet!!.thumbnails

            val high = image?.high

            val title = snippet.title
            val videoId = id?.videoId
            val imageLink = high?.url
            val sDate = snippet.publishedAt

            var pubDateString = "N/A"

            Locale.setDefault(Locale.getDefault())
            val tz = TimeZone.getDefault()
            val cal = Calendar.getInstance(tz)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.calendar = cal
            if (sDate != null) {
                val dateObject = sdf.parse(sDate)
                if (dateObject != null) {
                    cal.time = dateObject
                    val date = cal.time

                    val sdf2 = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    pubDateString = sdf2.format(date)
                }
            }

            val tempVideo = Video(title, videoId, imageLink, pubDateString)
            videos.add(tempVideo)
        }

        return ParsingResult(videos, nextToken)
    }

    @Throws(Exception::class)
    fun parseStats(json: String): Statistics {
        val gson = GsonBuilder().create()
        val data = gson.fromJson(json, com.prof.youtubeparser.models.stats.internal.Main::class.java)

        val itemList = data.items

        return itemList[0].statistics
    }


}
