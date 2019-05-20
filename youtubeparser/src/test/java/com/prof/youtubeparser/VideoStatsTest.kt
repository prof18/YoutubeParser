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