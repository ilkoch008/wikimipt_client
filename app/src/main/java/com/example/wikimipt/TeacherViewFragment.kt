package com.example.wikimipt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import okhttp3.*
import java.io.IOException

class TeacherViewFragment : Fragment() {
    private lateinit var router : Router
    var html : String = ""
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.teacher_page_layout, container, false)
        var text : TextView = layout.findViewById(R.id.teacher_text)
        text.text = "Подождите пожалуйста"
//        lateinit var parser : HtmlTeacherParse
        var url = message_to_next_fragment
        val webView : WebView
        webView = layout.findViewById(R.id.PageWebView)
        webView.getSettings().setJavaScriptEnabled(true)
        //webView.loadDataWithBaseURL(null, DownloadHtml(url), "text/html", "utf-8", null)
        webView.loadUrl(url)
        //text.text = message_to_next_fragment
        return layout
    }

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