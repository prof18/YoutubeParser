package com.prof.youtubeparser.core

import com.google.gson.GsonBuilder
import com.prof.youtubeparser.models.stats.Statistics
import com.prof.youtubeparser.models.videos.Main
import com.prof.youtubeparser.models.videos.Video
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object CoreJsonParser {

    @Throws(Exception::class)
    fun parseVideo(json: String): ParsingResult {
        val videos = mutableListOf<Video>()

        val gson = GsonBuilder().create()
        val data = gson.fromJson<Main>(json, Main::class.java)

        //Begin parsing Json data
        val itemList = data.items
        val nextToken = data.nextPageToken

        for (i in itemList.indices) {

            val item = itemList[i]
            val snippet = item.snippet

            val id = item.id

            val image = snippet!!.thumbnails

            val high = image.high

            val title = snippet.title
            val videoId = id.videoId
            val imageLink = high.url
            val sDate = snippet.publishedAt

            Locale.setDefault(Locale.getDefault())
            val tz = TimeZone.getDefault()
            val cal = Calendar.getInstance(tz)
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.calendar = cal
            cal.time = sdf.parse(sDate)
            val date = cal.time

            val sdf2 = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val pubDateString = sdf2.format(date)

            val tempVideo = Video(title, videoId, imageLink, pubDateString)
            videos.add(tempVideo)
        }

        return ParsingResult(videos, nextToken)
    }

    @Throws(Exception::class)
    fun parseStats(json: String): Statistics {
        val gson = GsonBuilder().create()
        val data = gson.fromJson<com.prof.youtubeparser.models.stats.Main>(json, com.prof.youtubeparser.models.stats.Main::class.java)

        val itemList = data.items

        return itemList[0].statistics
    }


}