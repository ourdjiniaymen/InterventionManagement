package com.example.interventionManagement.ui.listIntervention

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interventionManagement.R
import com.example.interventionManagement.data.entity.Intervention
import com.example.interventionManagement.data.repository.InterventionsRepository.Companion.listInterventions
import kotlinx.android.synthetic.main.list_intervention_fragment.*


class ListInterventionFragment : Fragment() {

    private lateinit var adapter: InterventionAdapter
    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_intervention_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        navController =
                Navigation.findNavController(requireActivity(), R.id.navigation_fragment)

        //set recycle view adapter
        val recyclerView: RecyclerView = list_intervention as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        adapter = InterventionAdapter()
        recyclerView.adapter = adapter

        //set Interventions in recycler view
        adapter.setInterventions(listInterventions)

        //edit intervention on long click
        adapter.setOnItemLongClickListener(object : InterventionAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position : Int) {
                val bundle = bundleOf("position" to position)
                navController.navigate(R.id.to_edit_intervention_fragment_action,bundle)
            }

        })

        //delete interventions on swipe
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(//Drag and drop methode
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listInterventions.remove(adapter.getInterventionAt(viewHolder.adapterPosition))
                Toast.makeText(activity, "Intervention deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)

        //add intervention on button clicked
        add_intervention.setOnClickListener {
            navController.navigate(R.id.to_add_intervention_fragment_action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popup_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        //set search bar
        val searchItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.queryHint = "30/12/2020"
        searchView.inputType =InputType.TYPE_CLASS_DATETIME
        searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}


