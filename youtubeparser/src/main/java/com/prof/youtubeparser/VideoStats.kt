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
import com.prof.youtubeparser.engine.JsonStatsParser
import com.prof.youtubeparser.enginecoroutines.CoroutineEngine
import com.prof.youtubeparser.models.stats.Statistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class VideoStats {

    private lateinit var onComplete: OnTaskCompleted

    /**
     * This method generate the url to retrieve statistic of a video
     *
     * @param videoID The ID of the youtube video
     * @param key     Your Browser API key. Obtain one by visiting https://console.developers.google.com
     * @return The url required to get video statistic
     */
    fun generateStatsRequest(videoID: String, key: String): String {
        return "$BASE_ADDRESS$key&part=statistics&id=$videoID"
    }

    fun execute(url: String) {
        Executors.newSingleThreadExecutor().submit {
            val service = Executors.newFixedThreadPool(2)
            val f1 = service.submit(JsonFetcher(url))
            try {
                val videoJson = f1.get()
                val f2 = service.submit(JsonStatsParser(videoJson))
                onComplete.onTaskCompleted(f2.get())
            } catch (e: Exception) {
                onComplete.onError(e)
            } finally {
                service.shutdown()
            }
        }
    }

    fun onFinish(onComplete: OnTaskCompleted) {
        this.onComplete = onComplete
    }

    @Throws(Exception::class)
    suspend fun getStats(url: String) =
            withContext(Dispatchers.IO) {
                val json = async { CoroutineEngine.fetchJson(url) }
                return@withContext CoroutineEngine.parseStats(json)
            }

    companion object {
        const val BASE_ADDRESS = "https://www.googleapis.com/youtube/v3/videos?key="
    }

    interface OnTaskCompleted {
        fun onTaskCompleted(statistics: Statistics)
        fun onError(e: Exception)
    }
}

