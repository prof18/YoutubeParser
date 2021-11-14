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

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class ParserTest {

    private lateinit var parser: Parser

    @Before
    fun setup() {
        parser = Parser()
    }

    @Test
    fun simpleRequest_isCorrect() {
        val correctRequestUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCVHFbqXqoYvEWM1Ddxl0QDg&maxResults=20&order=date&key=fakeKey"

        val composedRequestUrl = parser.generateRequest(
                channelID = "UCVHFbqXqoYvEWM1Ddxl0QDg",
                key = "fakeKey",
                maxResult = 20,
                orderType = Parser.ORDER_DATE
        )

        assertEquals(correctRequestUrl, composedRequestUrl)
    }

    @Test
    fun moreDataRequest_isCorrect() {
        val correctRequestUrl = "https://www.googleapis.com/youtube/v3/search?pageToken=CBQQAA&part=snippet&channelId=UCVHFbqXqoYvEWM1Ddxl0QDg&maxResults=20&order=date&key=fakeKey"

        val composedRequestUrl = parser.generateMoreDataRequest(
                channelID = "UCVHFbqXqoYvEWM1Ddxl0QDg",
                nextToken = "CBQQAA",
                orderType = Parser.ORDER_DATE,
                maxResult = 20,
                key = "fakeKey"
        )

        assertEquals(correctRequestUrl, composedRequestUrl)
    }
}