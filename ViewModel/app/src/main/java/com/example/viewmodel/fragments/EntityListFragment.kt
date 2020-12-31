package com.example.viewmodel.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodel.DialogListener
import com.example.viewmodel.EntityListViewModel
import com.example.viewmodel.Generator
import com.example.viewmodel.R
import com.example.viewmodel.adapters.EntityListAdapter
import com.example.viewmodel.model.Entity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlin.random.Random

class EntityListFragment : Fragment(R.layout.fragment_entity_list), DialogListener {
    companion object {
        private const val KEY_ENTITIES = "EntitiesList"
    }
    private lateinit var entityList : RecyclerView
    private lateinit var fab : FloatingActionButton
    private lateinit var emptyListtextView: TextView


    private var entityAdapter :EntityListAdapter? = null
    private val entityListViewModel : EntityListViewModel by viewModels()



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emptyListtextView = requireView().findViewById(R.id.emptyListTextView)
        emptyListtextView.visibility = View.GONE
        entityList = requireView().findViewById(R.id.entityList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener { addEntity() }
        initList()
        entityAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                entityList.scrollToPosition(0)
            }
        })
        observeViewModelState()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        showEmptyList()
    }

    private fun initList() {
        entityAdapter = EntityListAdapter { id ->
            val action = EntityListFragmentDirections.actionEntityListFragmentToEntityDetailFragment(id)
            findNavController().navigate(action)
        }
        with(entityList) {
            adapter = entityAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val divider  = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            itemAnimator = ScaleInAnimator()
            setHasFixedSize(true)
        }
    }

    private fun addEntity() {
        AddDialogFragment()
                .show(childFragmentManager, "ENTITY_DIALOG")
    }

    private fun deleteEntity(position : Int) {
        entityListViewModel.deleteEntity(position)
    }

    override fun onOkButtonClick(entity: Entity) {
        entityListViewModel.addEntity(entity)
    }

    private fun showEmptyList() {
        if (entityListViewModel.entities.value?.isEmpty() == true) {
            emptyListtextView.visibility = View.VISIBLE
        }
        else {
            emptyListtextView.visibility = View.GONE
        }
    }

    private fun observeViewModelState() {
        entityListViewModel.entities
            .observe(viewLifecycleOwner) {newEntities -> entityAdapter?.submitList(newEntities)}

        entityListViewModel.showToast
            .observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Элемент добавлен", Toast.LENGTH_SHORT).show()
            }
    }


}