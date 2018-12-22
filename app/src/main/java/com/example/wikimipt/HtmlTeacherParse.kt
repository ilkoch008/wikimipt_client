package com.example.wikimipt

import okhttp3.*
import java.io.IOException

class HtmlTeacherParse {

    private val client = OkHttpClient()

    var html = ""

    fun DownloadHtml(url : String) : String {
        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                html = response.body()?.string()!!
            }
        })

        return html
    }
}