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

import com.prof.youtubeparser.engine.JsonFetcher
import com.prof.youtubeparser.engine.JsonVideoParser
import com.prof.youtubeparser.enginecoroutines.CoroutineEngine
import com.prof.youtubeparser.models.videos.Video
import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class Parser {

    private lateinit var onComplete: OnTaskCompleted

    private lateinit var service: ExecutorService

    private val parserJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parserJob + Dispatchers.Default

    /**
     * This method generates the url that retrieves Youtube video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     */
    @Deprecated(
        message = "This method is deprecated. Please use the new version that allows to choose the type of order of the videos: {@link #generateRequest(String, int, int, String)}",
        replaceWith = ReplaceWith(
            "generateRequest(channelID, maxResult, ORDER_DATE ,key)",
            "com.prof.youtubeparser.Parser.Companion.ORDER_DATE"
        )
    )
    fun generateRequest(channelID: String, maxResult: Int, key: String): String {
        return "$BASE_ADDRESS$channelID&maxResults=$maxResult&order=date&key=$key"
    }

    /**
     * This method generates the url to retrieve Youtube Video data
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: [.ORDER_DATE] and by view count [.ORDER_VIEW_COUNT].
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get data
     */
    fun generateRequest(channelID: String, maxResult: Int, orderType: Int, key: String): String {
        val order = when (orderType) {
            ORDER_DATE -> "date"
            ORDER_VIEW_COUNT -> "viewcount"
            else -> ""
        }
        return "$BASE_ADDRESS$channelID&maxResults=$maxResult&order=$order&key=$key"
    }

    /**
     * This method generates the url to retrieve more video data. Remember that first you have to call
     * [.generateRequest] to get the next page token
     *
     * @param channelID The ID of the desired channel. Ex: https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg
     * channel id = UCVHFbqXqoYvEWM1Ddxl0QDg
     * @param maxResult The number of video to get. The maximum value is 50
     * @param orderType The type of ordering. You can choose an order by date: [.ORDER_DATE] and by view count [.ORDER_VIEW_COUNT].
     * @param key       Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @param nextToken The token necessary to load more data
     * @return The url required to get more data
     */
    fun generateMoreDataRequest(
        channelID: String,
        maxResult: Int,
        orderType: Int,
        key: String,
        nextToken: String
    ): String {
        val urlString = "https://www.googleapis.com/youtube/v3/search?pageToken="
        val order = when (orderType) {
            ORDER_DATE -> "date"
            ORDER_VIEW_COUNT -> "viewcount"
            else -> ""
        }
        return "$urlString$nextToken&part=snippet&channelId=$channelID&maxResults=$maxResult&order=$order&key=$key"
    }

    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    fun execute(url: String) {
        Executors.newSingleThreadExecutor().submit {
            service = Executors.newFixedThreadPool(2)
            val f1 = service.submit(JsonFetcher(url))
            try {
                val videoJson = f1.get()
                val f2 = service.submit(JsonVideoParser(videoJson))
                onComplete.onTaskCompleted(f2.get().videos, f2.get().nextToken)
            } catch (e: Exception) {
                onComplete.onError(e)
            } finally {
                service.shutdown()
            }
        }
    }

    suspend fun getVideos(url: String) = withContext(coroutineContext) {
        val json = CoroutineEngine.fetchJson(url)
        return@withContext CoroutineEngine.parseVideo(json)
    }

    /**
     *  Cancel the execution of the fetching and the parsing.
     */
    fun cancel() {
        if (::service.isInitialized) {
            // The client is using Java!
            try {
                if (!service.isShutdown) {
                    service.shutdownNow()
                }
            } catch (e: Exception) {
                onComplete.onError(e)
            }
        } else {
            // The client is using Kotlin and coroutines
            if (coroutineContext.isActive) {
                coroutineContext.cancel()
            }
        }
    }

    companion object {
        const val BASE_ADDRESS =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="
        const val ORDER_DATE = 1
        const val ORDER_VIEW_COUNT = 2
    }

    interface OnTaskCompleted {
        fun onTaskCompleted(list: List<Video>, nextPageToken: String?)
        fun onError(e: Exception)
    }
}