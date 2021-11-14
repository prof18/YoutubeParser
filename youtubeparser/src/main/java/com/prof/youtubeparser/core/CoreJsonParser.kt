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
import com.prof.youtubeparser.models.videos.Video
import com.prof.youtubeparser.models.videos.internal.Item
import com.prof.youtubeparser.models.videos.internal.Main

internal object CoreJsonParser {

    fun parseVideo(json: String): ParsingResult {
        val gson = GsonBuilder().create()
        val data = gson.fromJson(json, Main::class.java)

        //Begin parsing Json data
        val nextToken = data.nextPageToken
        val videos = data.items.map { item ->
            Video(
                title = item.snippet?.title,
                videoId = item.id?.videoId,
                coverLink = item.getHighQualityThumbnailUrl(),
                date = item.snippet?.publishedAt
            )
        }
        return ParsingResult(videos, nextToken)
    }

    private fun Item.getHighQualityThumbnailUrl(): String? = this.snippet?.thumbnails?.high?.url

    fun parseStats(json: String): Statistics {
        val gson = GsonBuilder().create()
        val data =
            gson.fromJson(json, com.prof.youtubeparser.models.stats.internal.Main::class.java)

        val itemList = data.items

        return itemList[0].statistics
    }
}
