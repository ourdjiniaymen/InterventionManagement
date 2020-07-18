package com.example.interventionManagement.ui.editIntervention


import android.app.DatePickerDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interventionManagement.R
import com.example.interventionManagement.data.repository.InterventionsRepository.Companion.listInterventions
import com.example.interventionManagement.data.repository.PlumberRepository
import com.example.interventionManagement.data.repository.TypeRepository
import com.example.interventionManagement.ui.addIntervention.ListAdapter
import com.example.interventionManagement.utility.DatePickerFragment
import kotlinx.android.synthetic.main.edit_intervention_fragment.date_view
import kotlinx.android.synthetic.main.edit_intervention_fragment.list_plumbers
import kotlinx.android.synthetic.main.edit_intervention_fragment.list_types
import kotlinx.android.synthetic.main.edit_intervention_fragment.plumber_view
import kotlinx.android.synthetic.main.edit_intervention_fragment.save_button
import kotlinx.android.synthetic.main.edit_intervention_fragment.type_view
import java.text.DateFormat
import java.util.*

class EditInterventionFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var navController: NavController
    private lateinit var plumberAdapter: ListAdapter
    private lateinit var typeAdapter: ListAdapter
    private var position = -1//position of intervention clicked

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_intervention_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController =
                Navigation.findNavController(requireActivity(), R.id.navigation_fragment)
        plumberAdapter = ListAdapter()
        typeAdapter = ListAdapter()

        //initialize fields
        initFields()

        //set plumbers recycle view adapter
        val plumbersRecyclerView: RecyclerView = list_plumbers as RecyclerView
        plumbersRecyclerView.layoutManager = LinearLayoutManager(activity)
        plumbersRecyclerView.setHasFixedSize(true)
        plumbersRecyclerView.adapter = plumberAdapter
        plumberAdapter.setItems(PlumberRepository.getPlumbers())

        //set types recycle view adapter
        val typesRecyclerView: RecyclerView = list_types as RecyclerView
        typesRecyclerView.layoutManager = LinearLayoutManager(activity)
        typesRecyclerView.setHasFixedSize(true)
        typesRecyclerView.adapter = typeAdapter
        typeAdapter.setItems(TypeRepository.getTypes())

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

    private fun initFields() {
        position = arguments?.getInt("position")!!
        plumber_view.text = listInterventions[position].plumber
        type_view.text = listInterventions[position].type
        date_view.text = listInterventions[position].date
    }

    private fun saveIntervention() {
        listInterventions[position].plumber = plumber_view.text.toString()
        listInterventions[position].type = type_view.text.toString()
        listInterventions[position].date = date_view.text.toString()
        navController.navigate(R.id.to_list_interventions_fragment_action)
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
}
