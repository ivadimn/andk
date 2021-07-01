package ru.ivadimn.material.ui.people

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.material.R
import ru.ivadimn.material.adapters.PeopleAdapter
import ru.ivadimn.material.databinding.FragmentPeopleBinding
import ru.ivadimn.material.haveQ
import ru.ivadimn.material.toast
import ru.ivadimn.material.ui.images.ImageItemDecoration
import ru.ivadimn.material.viewBinding

class PeopleFragment : Fragment(R.layout.fragment_people) {

    private val binding : FragmentPeopleBinding by viewBinding(FragmentPeopleBinding::bind)

    private val viewModel : PeopleViewModel by viewModels()
    private var peopleAdapter : PeopleAdapter? = null

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRequestPermissionLauncher()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermissions())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initViewModel()
        initToolBar()
        if (!hasPermissions()) {
            requestPermissions()
        }

    }

    private fun initList() {
        peopleAdapter = PeopleAdapter()
        binding.peopleList.apply {
            adapter = peopleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ImageItemDecoration(requireContext(), 8))
            setHasFixedSize(true)
        }
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.peopleFlow.collect {
                peopleAdapter?.items = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessageFlow.collect {
                if (it.isNotEmpty())
                    toast(it)
            }
        }
    }

    private fun initToolBar() {
        binding.toolbar.title = "People"
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun requestPermissions() {
        requestPermissionLauncher.launch(PERMISSIONS.toTypedArray())
    }

    private fun hasPermissions() : Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun initRequestPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap : Map<String, Boolean> ->
            if (permissionsMap.values.all { it }) {
                viewModel.permissionGranted()
            }
            else {
                viewModel.permissionDenied()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        peopleAdapter = null
    }

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
        )
    }

}