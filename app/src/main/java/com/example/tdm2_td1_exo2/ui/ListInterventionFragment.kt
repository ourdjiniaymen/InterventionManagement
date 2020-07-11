package com.example.tdm2_td1_exo2.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.example.tdm2_td1_exo2.MainActivity.Companion.replaceFragment
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.listIntervention

import com.example.tdm2_td1_exo2.R
import com.example.tdm2_td1_exo2.adapter.InterventionAdapter
import com.example.tdm2_td1_exo2.data.ListIntervention
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.sauvegarderIntervention
import com.example.tdm2_td1_exo2.entite.Intervention
import com.example.tdm2_td1_exo2.service.DatePickerFragment
import kotlinx.android.synthetic.main.add_intervention_fragment.*
import kotlinx.android.synthetic.main.intervention_layout.*
import kotlinx.android.synthetic.main.list_intervention_fragment.*
import java.util.ArrayList

class ListInterventionFragment : Fragment() {

    companion object {
        fun newInstance() = ListInterventionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_intervention_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = InterventionAdapter(
            context!!,
            listIntervention
        )
        list_intervention.adapter = adapter
        add_intervention.setOnClickListener {
            replaceFragment(activity!!, R.id.navigation_fragment, AddInterventionFragment())
        }
        search_intervention.setOnClickListener {
            val fm: FragmentManager = (activity as AppCompatActivity?)!!.supportFragmentManager
            val newFragment: AppCompatDialogFragment = DatePickerFragment()
            newFragment.setTargetFragment(this, AddInterventionFragment.REQUEST_CODE_DATE_PICKER)
            newFragment.show(fm, "datePicker")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddInterventionFragment.REQUEST_CODE_DATE_PICKER) {
                val date = data?.getStringExtra("selectedDate").toString()
                val searchFragment = SearchFragment()
                val bundle = Bundle()
                bundle.putString("date",date)
                searchFragment.arguments = bundle
                replaceFragment(activity!!,R.id.navigation_fragment,searchFragment)
            }
        }
    }
}


