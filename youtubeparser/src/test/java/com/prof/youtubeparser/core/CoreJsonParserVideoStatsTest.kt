package com.prof.youtubeparser.core

import com.prof.youtubeparser.FakeDataFactory
import com.prof.youtubeparser.models.stats.Statistics
import com.prof.youtubeparser.models.videos.Video
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CoreJsonParserVideoStatsTest {

    private lateinit var statistics: Statistics

    @Before
    fun setup() {
        statistics = CoreJsonParser.parseStats(FakeDataFactory.statsResponse)
    }

    @Test
    fun viewCount_isCorrect() {
        assertEquals(3007, statistics.viewCount)
    }

    @Test
    fun likeCount_isCorrect() {
        assertEquals(96, statistics.likeCount)
    }

    @Test
    fun dislikeCount_isCorrect() {
        assertEquals(2, statistics.dislikeCount)
    }

    @Test
    fun favouriteCount_isCorrect() {
        assertEquals(0, statistics.favoriteCount)
    }

    @Test
    fun commentCount_isCorrect() {
        assertEquals(2, statistics.commentCount)
    }
}