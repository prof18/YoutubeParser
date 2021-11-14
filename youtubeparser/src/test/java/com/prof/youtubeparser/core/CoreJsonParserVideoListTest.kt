package com.prof.youtubeparser.core

import com.prof.youtubeparser.FakeDataFactory
import com.prof.youtubeparser.models.videos.Video
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CoreJsonParserVideoListTest {

    private lateinit var parsingResult: ParsingResult
    private lateinit var video: Video

    @Before
    fun setup() {
        parsingResult = CoreJsonParser.parseVideo(FakeDataFactory.listResponse)
        video = parsingResult.videos.first()
    }

    @Test
    fun nextToken_isCorrect() {
        assertEquals("CAIQAA", parsingResult.nextToken)
    }

    @Test
    fun videoSize_isCorrect() {
        assertEquals(2, parsingResult.videos.size)
    }

    @Test
    fun title_isCorrect() {
//        assertEquals("Paging: Live Q&A - MAD Skills", video.title)
        assertEquals("Paging: Live Q&amp;A - MAD Skills", video.title)
    }

    @Test
    fun videoId_isCorrect() {
        assertEquals("8i6vrlbIVCc", video.videoId)
    }

    @Test
    fun coverLink_isCorrect() {
        assertEquals("https://i.ytimg.com/vi/8i6vrlbIVCc/hqdefault.jpg", video.coverLink)
    }

    @Test
    fun date_isCorrect() {
//        assertEquals("2021-11-11T18:56:09Z", video.date)
        assertEquals("11 November 2021", video.date)
    }
}