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

package com.prof.youtubeparser.sample.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.youtubeparser.Parser
import com.prof.youtubeparser.VideoStats
import com.prof.youtubeparser.models.stats.Statistics
import com.prof.youtubeparser.models.videos.Video
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>>
        get() = _videoList

    private val _snackbar = MutableLiveData<String>()
    val snackbar: LiveData<String>
        get() = _snackbar

    private val _stats = MutableLiveData<Statistics>()
    val stats: LiveData<Statistics>
        get() = _stats

    private var nextToken: String? = null
    private val channelID = "UCVHFbqXqoYvEWM1Ddxl0QDg"
    private val parser = Parser()

    fun onSnackbarShowed() {
        _snackbar.value = null
    }

    fun fetchVideos() {
        viewModelScope.launch(Dispatchers.Main) {
            val requestUrl = parser.generateRequest(
                    channelID = channelID,
                    maxResult = 20,
                    orderType = Parser.ORDER_DATE,
                    key = BuildConfig.KEY
            )
            try {
                val result = parser.getVideos(requestUrl)
                nextToken = result.nextToken
                _videoList.postValue(result.videos)
            } catch (e: Exception) {
                e.printStackTrace()
                _snackbar.value = "An error has occurred. Please retry"
                _videoList.postValue(mutableListOf())
            }
        }
    }

    fun fetchMoreVideos() {
        nextToken?.let { pageToken ->
            val requestUrl = parser.generateMoreDataRequest(
                    channelID = channelID,
                    maxResult = 20,
                    orderType = Parser.ORDER_DATE,
                    key = BuildConfig.KEY,
                    nextToken = pageToken
            )

            viewModelScope.launch {
                val result = parser.getVideos(requestUrl)
                nextToken = result.nextToken
                val oldList = _videoList.value?.toMutableList() ?: mutableListOf()
                oldList.addAll(result.videos)
                _videoList.postValue(oldList)
            }
        }
    }

    fun fetchStatistics(videoId: String) {
        val videoStats = VideoStats()
        val requestUrl = videoStats.generateStatsRequest(
                videoID = videoId,
                key = BuildConfig.KEY
        )
        viewModelScope.launch {
            try {
                _stats.postValue(videoStats.getStats(requestUrl))
            } catch (e: Exception) {
                e.printStackTrace()
                _snackbar.value = "An error has occurred. Please retry"
            }
        }
    }
}