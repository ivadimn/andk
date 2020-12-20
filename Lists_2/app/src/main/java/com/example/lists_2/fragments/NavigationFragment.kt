package com.example.lists_2.fragments

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.lists_2.ChangeFragmentListener
import com.example.lists_2.MainActivity
import com.example.lists_2.R

class NavigationFragment : Fragment(R.layout.fragment_navigation) {

   private var listener : ChangeFragmentListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listener = requireActivity().let { it as ChangeFragmentListener }

        requireView().findViewById<Button>(R.id.entityListButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_ENTITY_LIST) }
        requireView().findViewById<Button>(R.id.verticalLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_VERTICAL_LAYOUT_MANAGER)
        }
        requireView().findViewById<Button>(R.id.horizontalLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_HORIZONTAL_LAYOUT_MANAGER)
        }

        requireView().findViewById<Button>(R.id.gridLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_GRID_LAYOUT_MANAGER)
        }
        requireView().findViewById<Button>(R.id.horizontalGridLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_HORIZONTAL_GRID_LAYOUT_MANAGER)
        }
        requireView().findViewById<Button>(R.id.diffgridLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_DIFF_GRID_LAYOUT_MANAGER)
        }
        requireView().findViewById<Button>(R.id.staggeredGridLayoutManagerButton).setOnClickListener {
            listener?.changeFragment(MainActivity.TAG_STAGGERED_GRID_LAYOUT_MANAGER)
        }

    }
}