package ru.ivadimn.di.ui.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.di.DiContainer
import ru.ivadimn.di.R
import ru.ivadimn.di.ViewModelFactory
import ru.ivadimn.di.adapters.user.UserListAdapter
import ru.ivadimn.di.data.database.Db
import ru.ivadimn.di.databinding.FragmentListUserBinding
import ru.ivadimn.di.utils.ItemDecoration
import ru.ivadimn.di.viewBinding

class UserListFragment : Fragment(R.layout.fragment_list_user) {

    private val binding : FragmentListUserBinding by viewBinding(FragmentListUserBinding::bind)

    private val viewModel : UserViewModel by viewModels { ViewModelFactory() }

    private var userListAdapter : UserListAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initViewModel()
        initViews()
        viewModel.selectAll()
    }

    private fun initList() {
        userListAdapter = UserListAdapter(::call, ::onClick)
        binding.userList.apply {
            adapter = userListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 5))
            setHasFixedSize(true)
        }
    }


    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userListFlow.collect {
                userListAdapter?.items = it
            }
        }
    }

    private fun initViews() {
        binding.addUserFab.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(0)
            findNavController().navigate(action)
        }
    }

    private fun call(number : String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    private fun onClick(userId : Long) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(userId)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        userListAdapter = null
    }

}