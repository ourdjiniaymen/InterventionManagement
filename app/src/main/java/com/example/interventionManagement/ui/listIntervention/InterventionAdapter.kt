package com.example.interventionManagement.ui.listIntervention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.interventionManagement.R
import com.example.interventionManagement.data.entity.Intervention
import java.util.*
import kotlin.collections.ArrayList


@Suppress("UNCHECKED_CAST")
class InterventionAdapter : RecyclerView.Adapter<InterventionAdapter.InterventionHolder>(), Filterable {

    private var interventions: MutableList<Intervention> = ArrayList()
    private var interventionsFull: MutableList<Intervention> = ArrayList()
    private var listener: OnItemLongClickListener? = null
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): InterventionHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.intervention_item, parent, false)
        return InterventionHolder(itemView)
    }

    override fun onBindViewHolder(@NonNull holder: InterventionHolder, position: Int) {
        val currentIntervention = interventions[position]
        holder.interventionDate.text = currentIntervention.date
        holder.interventionNumber.text = currentIntervention.id.toString()
        holder.interventionPlumber.text = currentIntervention.plumber
        holder.interventionType.text = currentIntervention.type

    }

    override fun getItemCount(): Int {
        return interventions.size
    }

    fun setInterventions(interventions: List<Intervention>) {
        this.interventions = interventions as MutableList<Intervention>
        this.interventionsFull = ArrayList(interventions)
        notifyDataSetChanged()
    }

    fun getInterventionAt(position: Int): Intervention? {
        return interventions[position]
    }

    inner class InterventionHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        internal val interventionNumber: TextView = itemView.findViewById(R.id.text_view_number)
        internal val interventionType: TextView = itemView.findViewById(R.id.text_view_type)
        internal val interventionDate: TextView = itemView.findViewById(R.id.text_view_date)
        internal val interventionPlumber: TextView = itemView.findViewById(R.id.text_view_plumber)

        init {

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemLongClick(position)
                }
                true
            }
        }
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.listener = listener
    }

    override fun getFilter(): Filter {
        return interventionsFilter
    }

    private val interventionsFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Intervention> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(interventionsFull)
            } else {
                val filterPattern =
                        constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in interventionsFull) {
                    if (item.date.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
        ) {
            interventions.clear()
            interventions.addAll(results.values as List<Intervention>)
            notifyDataSetChanged()
        }
    }


}
