package com.prof.youtubeparser

import com.prof.youtubeparser.models.videos.Video

interface OnTaskCompleted {
    fun onTaskCompleted(list: MutableList<Video>, nextPageToken: String)
    fun onError()
}