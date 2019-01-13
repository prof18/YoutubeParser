package com.prof.youtubeparser.core

import com.prof.youtubeparser.models.videos.Video

data class ParsingResult(
        val videos: MutableList<Video>,
        val nextToken: String
)