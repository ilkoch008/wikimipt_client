package com.example.wikimipt

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import okhttp3.*

var message_to_next_fragment : String = " "

class Teachers : Fragment() {
    private lateinit var router: Router
    private val client = OkHttpClient()
    var res1: String = " "
    private lateinit var recycler : RecyclerView
    //private lateinit var mAdapter: SearchClickableAdapter

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
        //var message_to_next_fragment: String
        //val teext: TextView = view.findViewById(R.id.search_results)
        var list = ArrayList<Teacher>()
        button.setOnClickListener {
            message_to_next_fragment = editText.text.toString()
            hideKeyboard()
            router.navigateTo(true, ::SearchViewFragment)
        }
    }

    fun Fragment.hideKeyboard() {
        activity!!.hideKeyboard(view)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View?) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

}

class Teacher(val name: String, val url: String) {
}
