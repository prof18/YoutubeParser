package com.prof.youtubeparser

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideoStatsTest {

    private lateinit var videoStats: VideoStats

    @Before
    fun setup() {
        videoStats = VideoStats()
    }

    @Test
    fun request_isCorrect() {
        val correctRequestUrl = "https://www.googleapis.com/youtube/v3/videos?key=fakeKey&part=statistics&id=WyIcJd55l1M"

        val generatedRequestUrl = videoStats.generateStatsRequest(
                videoID = "WyIcJd55l1M",
                key = "fakeKey"
        )

        assertEquals(correctRequestUrl, generatedRequestUrl)
    }

}