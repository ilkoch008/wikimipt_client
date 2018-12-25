package com.example.wikimipt

import okhttp3.*
import java.io.IOException

class HtmlTeacherParse__ {

    private val client = OkHttpClient()

    var html = ""
    var url = ""

    fun urlInit(str : String) {
        url = str
    }

    fun DownloadHtml() : String {
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