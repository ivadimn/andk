package ru.ivadimn.flowroom.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ivadimn.flowroom.R
import ru.ivadimn.flowroom.databinding.FragmentUserBinding
import ru.ivadimn.flowroom.model.Sex
import ru.ivadimn.flowroom.viewBinding

class UsersFragment : Fragment(R.layout.fragment_user) {

    private val binding : FragmentUserBinding by viewBinding(FragmentUserBinding::bind)

    private val viewModel : UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.addButton.setOnClickListener {
            addUser()
        }

        viewModel.selectAll()
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collect {
                binding.usersTextView.text = it.joinToString("\n")
            }
        }
    }


    private fun addUser() {
        val name = binding.nameTextView.text.toString()
        val age = binding.ageTextView.text.toString().toInt()
        val sex = if (binding.maleRadioButton.isChecked) Sex.MALE else Sex.FEMALE
        viewModel.insert(name, age, sex)
    }
}