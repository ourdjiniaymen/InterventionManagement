package com.example.tdm2_td1_exo2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.tdm2_td1_exo2.R.layout.item_list_layout
import kotlinx.android.synthetic.main.item_list_layout.view.*


class PlombierAdapter(context: Context, private var listPlombierAdapter: List<String>) :
    BaseAdapter() {

    private var context: Context?= context

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view=inflater.inflate(item_list_layout,null)
        val plombier=listPlombierAdapter[position]
        view.item_nom.text = plombier
        return view
    }

    override fun getItem(position: Int): Any {
        return listPlombierAdapter[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listPlombierAdapter.size
    }
}