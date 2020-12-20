package com.example.lists_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.lists_2.fragments.EntityListFragment
import com.example.lists_2.fragments.ImageListFragment
import com.example.lists_2.fragments.NavigationFragment

class MainActivity : AppCompatActivity(), ChangeFragmentListener, NavHost {

    companion object {
        const val TAG_NAVIGATION = "Navigation"
        const val TAG_ENTITY_LIST = "EntityList"
        const val TAG_VERTICAL_LAYOUT_MANAGER = "VerticalLayoutManager"
        const val TAG_HORIZONTAL_LAYOUT_MANAGER = "HorizontalLayoutManager"
        const val TAG_GRID_LAYOUT_MANAGER = "GridLayoutManager"
        const val TAG_HORIZONTAL_GRID_LAYOUT_MANAGER = "HorizontalGridLayoutManager"
        const val TAG_DIFF_GRID_LAYOUT_MANAGER = "DiffGridLayoutManager"
        const val TAG_STAGGERED_GRID_LAYOUT_MANAGER = "StaggeredGridLayoutManager"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            with(supportFragmentManager.beginTransaction()) {
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                replace(R.id.container, NavigationFragment(), TAG_NAVIGATION)
                commit()
            }

        }
    }

    override fun changeFragment(tag : String) {
        when(tag) {
         TAG_ENTITY_LIST -> showFragment(R.id.container, EntityListFragment(), TAG_ENTITY_LIST)
         TAG_VERTICAL_LAYOUT_MANAGER -> showFragment(R.id.container,
             ImageListFragment.newInstance(R.layout.item_image_vertical), TAG_VERTICAL_LAYOUT_MANAGER)
         TAG_HORIZONTAL_LAYOUT_MANAGER -> showFragment(R.id.container,
            ImageListFragment.newInstance(R.layout.image_item_horizontal), TAG_HORIZONTAL_LAYOUT_MANAGER)
         TAG_GRID_LAYOUT_MANAGER -> showFragment(R.id.container,
            ImageListFragment.newInstance(R.layout.item_image_grid), TAG_GRID_LAYOUT_MANAGER)
         TAG_HORIZONTAL_GRID_LAYOUT_MANAGER -> showFragment(R.id.container,
            ImageListFragment.newInstance(R.layout.item_image_grid_horizontal), TAG_HORIZONTAL_GRID_LAYOUT_MANAGER)
         TAG_DIFF_GRID_LAYOUT_MANAGER -> showFragment(R.id.container,
            ImageListFragment.newInstance(R.layout.item_image_grid_diff), TAG_DIFF_GRID_LAYOUT_MANAGER)
         TAG_STAGGERED_GRID_LAYOUT_MANAGER -> showFragment(R.id.container,
            ImageListFragment.newInstance(R.layout.item_image_grid_staggered), TAG_STAGGERED_GRID_LAYOUT_MANAGER)

        }
    }

    override fun onBackPressed() {
        val navigationFragment =

        super.onBackPressed()
    }

    override fun getNavController(): NavController {
        TODO("Not yet implemented")
    }
}