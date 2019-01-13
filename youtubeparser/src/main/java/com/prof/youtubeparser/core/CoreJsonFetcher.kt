package com.prof.youtubeparser.core

import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

object CoreJsonFetcher {

    @Throws(Exception::class)
    fun fetchJson(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()

        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }
}







