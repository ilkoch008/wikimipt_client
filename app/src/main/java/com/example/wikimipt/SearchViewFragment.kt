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
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.json.XML
import java.io.IOException

class SearchViewFragment : Fragment() {

    val apologize = "Подождите пожалуйста"

    val mAdapter = SearchClickableAdapter()

    var searching = message_to_next_fragment

    private val client = OkHttpClient()

    var listt = ArrayList<Teacher>()

    fun listt_define(list: ArrayList<Teacher>){
        listt = list
    }

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
        //message_to_next_fragment = savedInstanceState?.getString("message_to_next_fragment", "a") ?: "a"

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //Toast.makeText(context, "LOL", Toast.LENGTH_SHORT).show()
        val layout = inflater.inflate(R.layout.search_view_layout, container, false)
        //Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show()
        val recycler : RecyclerView = layout.findViewById(R.id.search_view_recycler)
        recycler.setHasFixedSize(true)

        //listt.add(Teacher(apologize, ""))
        if(listt.isEmpty()) {
            listt = searchAndDownload(searching)
        }
        createClickableList(recycler, listt)
        for(i in 1..10) {
            Handler().postDelayed({
                mAdapter.notifyDataSetChanged()
            }, (i*250).toLong())
        }
        return layout
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

        mAdapter.listt_define(list_t)
        mAdapter.activity_define(requireActivity())
        recycler.layoutManager = layoutManager
        recycler.adapter = mAdapter

        mAdapter.notifyDataSetChanged()
    }

    fun searchAndDownload(searching: String) : ArrayList<Teacher> {
        //val teext: TextView = view!!.findViewById(R.id.search_results)
        //teext.text = message_to_next_fragment
        val url = "http://wikimipt.org//api.php?action=opensearch&format=xml&limit=100&search=" + searching
        val list = ArrayList<Teacher>()
        list.add(Teacher(apologize, ""))


        val request = Request.Builder()
                .url(url)
                .build()

        //thread {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    var res = response.body()?.string()
                    //teext.text = res
                    list.clear()
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
                    var test = jsonObject.getJSONObject("SearchSuggestion").getString("Section")
                    if (test.contains("{\"Item\":[{", false)) {
                        var length = jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").length()
                        while (i < length) {
                            list.add(Teacher(
                                    jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").getJSONObject(i).getJSONObject("Text").getString("content"),
                                    jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONArray("Item").getJSONObject(i).getJSONObject("Url").getString("content")))
                            i++
                        }
                    } else if (test.contains("{\"Item\":{", false)){
                        list.add(Teacher(
                                jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONObject("Item").getJSONObject("Text").getString("content"),
                                jsonObject.getJSONObject("SearchSuggestion").getJSONObject("Section").getJSONObject("Item").getJSONObject("Url").getString("content")))
                    } else {
                        list.add(Teacher("Ничего не найдено", ""))
                    }
                }
            })
        //}.join()
        return list

//        while (!finished){ }
//        finished = false

    }
}