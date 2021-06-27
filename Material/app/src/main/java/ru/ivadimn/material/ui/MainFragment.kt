package ru.ivadimn.material.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivadimn.material.R
import ru.ivadimn.material.databinding.FragmentMainBinding
import ru.ivadimn.material.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menuItemFavorites -> {
                    runStub("1 ${item.title}")
                    true
                }
                R.id.menuItemPeople ->  { true }
                R.id.menuItemMusic -> { true }
                R.id.menuItemNotes -> {
                    runStub("4 ${item.title}")
                    true
                }
                else -> false
            }
        }
    }

    private fun runStub(caption : String) {
        val action = MainFragmentDirections.actionMainFragmentToStubFragment(caption)
        findNavController().navigate(action)
    }
}