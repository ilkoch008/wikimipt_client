package com.example.wikimipt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Teachers : Fragment() {
    private lateinit var router : Router
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.teachers_layout, container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.SearchButton)
        val editText : EditText = view.findViewById(R.id.editSearchingText)
        var searching : String
        button.setOnClickListener {
            searching = editText.text.toString()
            searchAndDownload(searching)
        }
    }

    fun searchAndDownload(searching : String){
        val teext : TextView = view!!.findViewById(R.id.search_results)
        teext.text = searching
        val result = URL("http://wikimipt.org//api.php?action=opensearch&format=xml&utf8=1&search=Иванов").toString()
        teext.text = result
        val url = "http://wikimipt.org//api.php?action=opensearch&format=xml&utf8=1&search=Иванов"

        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                var res = response.body()?.string()
                teext.text = res
            }
        })
    }

    }
