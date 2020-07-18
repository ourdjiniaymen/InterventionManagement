package com.example.interventionManagement.ui.addIntervention

import android.annotation.SuppressLint
import android.app.DatePickerDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interventionManagement.R
import com.example.interventionManagement.data.entity.Intervention
import com.example.interventionManagement.data.repository.InterventionsRepository.Companion.listInterventions
import com.example.interventionManagement.data.repository.PlumberRepository.Companion.getPlumbers
import com.example.interventionManagement.data.repository.TypeRepository.Companion.getTypes
import com.example.interventionManagement.utility.DatePickerFragment
import kotlinx.android.synthetic.main.add_intervention_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddInterventionFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var navController: NavController
    private lateinit var plumberAdapter: ListAdapter
    private lateinit var typeAdapter: ListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_intervention_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController =
                Navigation.findNavController(requireActivity(), R.id.navigation_fragment)
        plumberAdapter = ListAdapter()
        typeAdapter = ListAdapter()
        date_view.text = getDate()

        //set plumbers recycle view adapter
        val plumbersRecyclerView: RecyclerView = list_plumbers as RecyclerView
        plumbersRecyclerView.layoutManager = LinearLayoutManager(activity)
        plumbersRecyclerView.setHasFixedSize(true)
        plumbersRecyclerView.adapter = plumberAdapter
        plumberAdapter.setItems(getPlumbers())

        //set types recycle view adapter
        val typesRecyclerView: RecyclerView = list_types as RecyclerView
        typesRecyclerView.layoutManager = LinearLayoutManager(activity)
        typesRecyclerView.setHasFixedSize(true)
        typesRecyclerView.adapter = typeAdapter
        typeAdapter.setItems(getTypes())

        plumber_view.setOnClickListener {
            list_plumbers.visibility = View.VISIBLE
        }

        type_view.setOnClickListener {
            list_types.visibility = View.VISIBLE
        }

        //select plumber on item click
        plumberAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {
                plumber_view.text = item
                list_plumbers.visibility = View.GONE
            }
        })

        //select type on item click
        typeAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {
                type_view.text = item
                list_types.visibility = View.GONE
            }
        })

        date_view.setOnClickListener {
            showDatePicker()
        }

        save_button.setOnClickListener {
            saveIntervention()
        }

    }

    private fun saveIntervention() {
        val max = listInterventions.max()?.id
        val id: Int = if (max != null) {
            max + 1
        } else {
            1
        }
        if(plumber_view.text == "Nom du plombier" || type_view.text =="Type d\'intervention"){
            Toast.makeText(activity,"remplir toutes les champs",Toast.LENGTH_SHORT).show()
        }else{
            listInterventions.add(Intervention(id,date_view.text.toString(), plumber_view.text.toString(), type_view.text.toString()))
            navController.navigate(R.id.to_list_interventions_fragment_action)
        }
    }

    private fun showDatePicker() {
        val datePicker: DialogFragment = DatePickerFragment(this)
        datePicker.show(
                (activity as AppCompatActivity?)!!.supportFragmentManager,
                "date picker"
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val currentDateString: String =
                DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
        date_view.text = currentDateString
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate() : String{
        val sdf = SimpleDateFormat("dd/M/yyyy")
        return sdf.format(Date())
    }
}
