package com.example.tdm2_td1_exo2.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.example.tdm2_td1_exo2.MainActivity.Companion.replaceFragment

import com.example.tdm2_td1_exo2.R
import com.example.tdm2_td1_exo2.adapter.PlombierAdapter
import com.example.tdm2_td1_exo2.adapter.TypeAdapter
import com.example.tdm2_td1_exo2.data.ListIntervention
import com.example.tdm2_td1_exo2.data.ListIntervention.Companion.listIntervention
import com.example.tdm2_td1_exo2.data.ListPlombier
import com.example.tdm2_td1_exo2.data.ListType
import com.example.tdm2_td1_exo2.entite.Intervention
import com.example.tdm2_td1_exo2.service.DatePickerFragment
import kotlinx.android.synthetic.main.add_intervention_fragment.*
import kotlinx.android.synthetic.main.intervention_layout.*
import kotlinx.android.synthetic.main.list_intervention_fragment.*
import java.util.ArrayList

class AddInterventionFragment : Fragment() {

    companion object {
        fun newInstance() = AddInterventionFragment()
        const val REQUEST_CODE_DATE_PICKER = 100
    }

    lateinit var plombier : String
    lateinit var type : String
    lateinit var date : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_intervention_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val plombierAdapter= PlombierAdapter(context!!, ListPlombier.getPlombier() as ArrayList<String>)
        list_plombier.adapter=plombierAdapter
        val typeAdapter= TypeAdapter(context!!, ListType.getType() as ArrayList<String>)
        list_type.adapter=typeAdapter

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
                plombier = list_plombier.adapter.getItem(position).toString()
                plombier_view.text = plombier
            }
        list_type.onItemClickListener =
            AdapterView.OnItemClickListener { arg0, arg1, position, arg3 ->
                list_type.visibility = View.GONE
                type = list_type.adapter.getItem(position).toString()
                type_view.text = type
            }

        date_view.setOnClickListener {
            val fm: FragmentManager = (activity as AppCompatActivity?)!!.supportFragmentManager
            val newFragment: AppCompatDialogFragment = DatePickerFragment()
            newFragment.setTargetFragment(this, REQUEST_CODE_DATE_PICKER)
            newFragment.show(fm, "datePicker")
        }

        souvegarder_button.setOnClickListener {
            val max= listIntervention.max()?.id
            var id =0
            if(max!=null) id = max +1
            listIntervention.add(Intervention(id,date,plombier,type))
            replaceFragment(activity!!, R.id.navigation_fragment,ListInterventionFragment())
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_DATE_PICKER) {
                date = data?.getStringExtra("selectedDate").toString()
                date_view.text= date
                //hide keyboard
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }

}
