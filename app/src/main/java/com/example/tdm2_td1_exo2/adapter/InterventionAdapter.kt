package com.example.tdm2_td1_exo2.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.tdm2_td1_exo2.MainActivity
import com.example.tdm2_td1_exo2.MainActivity.Companion.replaceFragment
import com.example.tdm2_td1_exo2.R
import com.example.tdm2_td1_exo2.entite.Intervention
import com.example.tdm2_td1_exo2.R.layout.intervention_layout
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.listIntervention
import com.example.tdm2_td1_exo2.ui.EditInterventionFragment
import com.example.tdm2_td1_exo2.ui.ListInterventionFragment
import kotlinx.android.synthetic.main.intervention_layout.view.*

class InterventionAdapter(context: Context, private var listInterventionAdapter: List<Intervention>) :
    BaseAdapter() {

    private var context: Context?= context

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view=inflater.inflate(intervention_layout,null)
        val intervention=listInterventionAdapter[position]
        view.num_intervention.text = intervention.id.toString()
        view.date_intervention.text = intervention.date
        view.nom_plombier.text = intervention.plombier
        view.type.text = intervention.type
        view.modifier.setOnClickListener {
            val editFragment = EditInterventionFragment()
            val bundle = Bundle()
            bundle.putInt("position",position)
            editFragment.arguments = bundle
            replaceFragment((context as FragmentActivity?)!!,R.id.navigation_fragment,editFragment )
        }
        view.supprimer.setOnClickListener {
            listIntervention.removeAt(position)
            replaceFragment((context as FragmentActivity?)!!,R.id.navigation_fragment,ListInterventionFragment())
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return listInterventionAdapter[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listInterventionAdapter.size
    }
}