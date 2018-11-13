package com.example.wikimipt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class Faculties : Fragment() {

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //Toast.makeText(context, "LOL", Toast.LENGTH_SHORT).show()
        val layout = inflater.inflate(R.layout.faculties_layout, container, false)
        //Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show()
        val recycler : RecyclerView = layout.findViewById(R.id.list)
        recycler.setHasFixedSize(true)
        createClickableList(recycler)
        return layout
    }

    private fun createClickableList(recycler : RecyclerView) {
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
        recycler.layoutManager = layoutManager
        recycler.adapter = ClickableAdapter()
    }
}