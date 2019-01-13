package com.prof.youtubeparser.engine

import com.prof.youtubeparser.core.CoreJsonFetcher
import java.lang.Exception
import java.util.concurrent.Callable

class JsonFetcher(private val url: String) : Callable<String> {

    @Throws(Exception::class)
    override fun call(): String {
        return CoreJsonFetcher.fetchJson(url)
    }
}