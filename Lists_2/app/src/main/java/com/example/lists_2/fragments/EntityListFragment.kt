package com.example.lists_2.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lists_2.*
import com.example.lists_2.adapters.EntityListAdapter
import com.example.lists_2.adapters.EntityListAdapter2
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


    private var entityAdapter by AutoClearedValue<EntityListAdapter2>()

    private var entities : List<Entity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            entities = savedInstanceState.getParcelableArrayList<Entity>(KEY_ENTITIES)
                ?: emptyList()
        }
        else {
            entities = generateEntities()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emptyListtextView = requireView().findViewById(R.id.emptyListTextView)
        emptyListtextView.visibility = View.GONE
        entityList = requireView().findViewById(R.id.entityList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener { addEntity() }
        initList()
        entityAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                entityList.scrollToPosition(0)
            }
        })
        entityAdapter.items = entities
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_ENTITIES, ArrayList<Entity>(entities))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        showEmptyList()
    }

    private fun initList() {
        entityAdapter = EntityListAdapter2 { position -> deleteEntity(position) }
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
        Log.d("LIST_FRAGMENT", "deleteEntry - positiom = ${position}")
        entities = entities.filterIndexed {index, _ -> index != position}
        entityAdapter.items = entities
        showEmptyList()
    }

    override fun onOkButtonClick(entity: Entity) {
        entities = listOf(entity) + entities
        entityAdapter.items = entities
    }

    private fun showEmptyList() {
        if (entities.isEmpty()) {
            emptyListtextView.visibility = View.VISIBLE
        }
        else {
            emptyListtextView.visibility = View.GONE
        }
    }

    private fun generateEntities() : List<Entity>{
        return (1 .. 100).map {
            val id = Generator.nextId()
            val country = Generator.getCountry()
            val isActor = Random.nextBoolean()

            if (isActor) {
                Entity.Actor(
                    id = id,
                    name = Generator.getNameActor(),
                    imageLink = Generator.getImageActor(),
                    country = country,
                    birthday = Generator.getBirthday(),
                    films = Generator.getFilm()
                )
            }
            else {
                Entity.Car(
                    id = id,
                    name = Generator.getNameCar(),
                    imageLink = Generator.getImageCar(),
                    country = country,
                    power = Generator.getPower(),
                    maxSpeed = Generator.getMaxSpeed()
                )
            }
        }

    }
}