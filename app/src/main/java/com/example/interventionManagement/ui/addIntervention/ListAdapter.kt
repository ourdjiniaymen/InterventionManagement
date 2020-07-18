package com.example.interventionManagement.ui.addIntervention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.interventionManagement.R
import com.example.interventionManagement.ui.listIntervention.InterventionAdapter
import kotlin.collections.ArrayList


class ListAdapter : RecyclerView.Adapter<ListAdapter.ListHolder>() {

    private var items: MutableList<String> = ArrayList()
    private var listener: OnItemClickListener? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ListHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ListHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: ListHolder, position: Int) {
        val currentItem = items[position]
        holder.item.text = currentItem

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<String>) {
        this.items = items as MutableList<String>
        notifyDataSetChanged()
    }

    inner class ListHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        internal val item: TextView = itemView.findViewById(R.id.item_view)

        init {

            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(items[position])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item : String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

}