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
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.example.tdm2_td1_exo2.MainActivity.Companion.replaceFragment

import com.example.tdm2_td1_exo2.R
import com.example.tdm2_td1_exo2.adapter.PlombierAdapter
import com.example.tdm2_td1_exo2.adapter.TypeAdapter
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.listIntervention
import com.example.tdm2_td1_exo2.data.ListPlombier
import com.example.tdm2_td1_exo2.data.ListType
import com.example.tdm2_td1_exo2.service.DatePickerFragment
import kotlinx.android.synthetic.main.edit_intervention_fragmenet.*

import java.util.ArrayList

class EditInterventionFragment : Fragment() {

    companion object {
        fun newInstance() = AddInterventionFragment()
        const val REQUEST_CODE_DATE_PICKER = 100
    }
    var position : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_intervention_fragmenet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val plombierAdapter= PlombierAdapter(context!!, ListPlombier.getPlombier() as ArrayList<String>)
        list_plombier.adapter=plombierAdapter
        val typeAdapter= TypeAdapter(context!!, ListType.getType() as ArrayList<String>)
        list_type.adapter=typeAdapter
        val bundle = arguments
        position = bundle!!.getInt("position")
        plombier_view.text = listIntervention[position].plombier
        type_view.text = listIntervention[position].type
        date_view.text = listIntervention[position].date

        plombier_view.setOnClickListener {
            list_plombier.visibility = View.VISIBLE
        }

        type_view.setOnClickListener {
            list_type.visibility = View.VISIBLE
        }
        /***************************/
        list_plombier.onItemClickListener =
            AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
                list_plombier.visibility = View.GONE
                plombier_view.text = list_plombier.adapter.getItem(position).toString()
            }
        list_type.onItemClickListener =
            AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
                list_type.visibility = View.GONE
                type_view.text = list_type.adapter.getItem(position).toString()
            }

        date_view.setOnClickListener {
            val fm: FragmentManager = (activity as AppCompatActivity?)!!.supportFragmentManager
            val newFragment: AppCompatDialogFragment = DatePickerFragment()
            newFragment.setTargetFragment(this, REQUEST_CODE_DATE_PICKER)
            newFragment.show(fm, "datePicker")
        }

        souvegarder_button.setOnClickListener {
            listIntervention[position].date = date_view.text.toString()
            listIntervention[position].plombier = plombier_view.text.toString()
            listIntervention[position].type = type_view.text.toString()
            replaceFragment(activity!!, R.id.navigation_fragment,ListInterventionFragment())
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_DATE_PICKER) {
                date_view.text=  data?.getStringExtra("selectedDate").toString()
                //hide keyboard
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

}
