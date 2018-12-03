package com.example.wikimipt

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class ClickableAdapter : RecyclerView.Adapter<ClickableViewHolder>() {

    class im_Item(var name: String = "FFFFF", var v_id: Int = R.drawable.f1)

    val imgs = arrayListOf<Int>(
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f6,
            R.drawable.f7,
            R.drawable.f8,
            R.drawable.f9,
            R.drawable.f10,
            R.drawable.f11,
            R.drawable.f1
    )

    val names = arrayListOf<String>(
            "ФРТК",
            "ФОПФ",
            "ФАКИ",
            "ФМХФ",
            "ФФКЭ",
            "ФАЛТ",
            "ФУПМ",
            "ФПФЭ",
            "ФИФТ",
            "ФНБИК",
            "ФБМФ",
            "ФГН"
    )



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //tableOfItems = def_tableOfItems(tableOfItems)
        return ClickableViewHolder(
            inflater.inflate(R.layout.faculties_buttons, parent, false),
            ::onItemClick)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: ClickableViewHolder, position: Int) {
        holder.setText(names[position])
        holder.setImage(imgs[position])
    }

    fun onItemClick(view: View, position: Int) {
        Toast.makeText(view.context, names[position], Toast.LENGTH_SHORT).show()
    }

}

class ClickableViewHolder(view : View,
                          private val clickListener : (View, Int) -> Unit ) : RecyclerView.ViewHolder(view) {
    private val title: ImageView = view.findViewById(R.id.title)
    private val text: TextView = view.findViewById(R.id.text)

    init {
        view.setOnClickListener {
            clickListener(it, adapterPosition)
        }
    }

    fun setText(text : String) {
        this.text.text = text
    }

//    fun setTitle(title : String) {
//        this.title.text = title
//    }

    fun setImage(title: Int){

        this.title.setImageResource(title)
    }
}