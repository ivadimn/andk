package ru.ivadimn.material.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.material.R
import ru.ivadimn.material.databinding.FragmentMainBinding
import ru.ivadimn.material.model.BottomNavigationListener
import ru.ivadimn.material.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel : MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomNavigation()
        initViewModel()
        viewModel.loadImages()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoadingFlow.collect(::updateLoadingState)
        }
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menuItemImages -> {
                    runImageListFragment()
                    true
                }
                R.id.menuItemPeople ->  {
                    runPeopleFragment()
                    true
                }
                R.id.menuItemFavorites -> {
                    runStub("3 ${item.title}")
                    true
                }
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

    private fun runImageListFragment() {
        val action = MainFragmentDirections.actionMainFragmentToImageListFragment()
        findNavController().navigate(action)
    }

    private fun runPeopleFragment() {
        val action = MainFragmentDirections.actionMainFragmentToPeopleFragment()
        findNavController().navigate(action)
    }


    private fun updateLoadingState(isLoading : Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.bottomNavigationView.isEnabled = !isLoading

    }

    companion object {
        const val TAG = "Material"
    }

}