package com.example.wikimipt

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.wikimipt.R.id.async
import kotlinx.coroutines.yield
import okhttp3.*
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject
import org.json.XML
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Teachers : Fragment() {
    private lateinit var router: Router
    private val client = OkHttpClient()
    var res1: String = " "
    private lateinit var recycler : RecyclerView
    private lateinit var mAdapter: SearchClickableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
        //recycler.setHasFixedSize(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.teachers_layout, container, false)
        recycler = layout.findViewById(R.id.search_results)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.SearchButton)
        val editText: EditText = view.findViewById(R.id.editSearchingText)
        var searching: String
        //val teext: TextView = view.findViewById(R.id.search_results)
        var list = ArrayList<Teacher>()
        button.setOnClickListener {
            searching = editText.text.toString()
            //launch { searchAndDownload(searching) }.start()

            list = searchAndDownload(searching)
            createClickableList(recycler, list)
            //teext.text = res1
        }
    }

    fun searchAndDownload(searching: String) : ArrayList<Teacher> {
        //val teext: TextView = view!!.findViewById(R.id.search_results)
        //teext.text = searching
        val url = "http://wikimipt.org//api.php?action=opensearch&format=xml&search=" + searching
        val list = ArrayList<Teacher>()


        val request = Request.Builder()
                .url(url)
                .build()

        thread {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var res = response.body()?.string()
                    //teext.text = res

                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = XML.toJSONObject(res)
                    } catch (e: JSONException) {
                        Log.e("JSON exception", e.message)
                        e.printStackTrace()
                    }
                    //teext.text = jsonObject?.getJSONObject("SearchSuggestion")?.getJSONObject("Section")?.getJSONArray("Item")?.getJSONObject(0)?.getJSONObject("Text")?.getString("content")
                    Log.d("XML", res)
                    Log.d("JSON", jsonObject!!.toString())

                    var i = 0

                    while (i < jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").length()) {
                        list.add(Teacher(
                                jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").getJSONObject(i).getJSONObject("Text").getString("content"),
                                jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").getJSONObject(i).getJSONObject("Url").getString("content")))
                        i++
                    }
                    //mAdapter.listt_define(list)
                    //createClickableList(recycler, list)

                }
            })
        }.join()
        return list
//        while (!finished){ }
//        finished = false

    }

    private fun createClickableList(recycler : RecyclerView, list_t : ArrayList<Teacher>) {
        val layoutManager = GridLayoutManager(
                requireContext(),
                2,
                RecyclerView.VERTICAL,
                false
        )
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return  2
            }
        }
        val mAdapter = SearchClickableAdapter()
        mAdapter.listt_define(list_t)
        recycler.layoutManager = layoutManager
        recycler.adapter = mAdapter
    }

}


class Teacher(val name: String, val url: String) {
}
