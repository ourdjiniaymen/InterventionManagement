package com.example.tdm2_td1_exo2.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tdm2_td1_exo2.MainActivity

import com.example.tdm2_td1_exo2.R
import com.example.tdm2_td1_exo2.adapter.InterventionAdapter
import com.example.tdm2_td1_exo2.data.ListIntervention
import kotlinx.android.synthetic.main.list_intervention_fragment.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        val dateS = bundle!!.getString("date")
        val adapter = InterventionAdapter(
            context!!,
            ListIntervention.listIntervention.filter { it.date == dateS }
        )
        list_intervention.adapter = adapter
    }

}
