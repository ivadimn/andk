package ru.ivadimn.timer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivadimn.timer.ViewBindingFragment
import ru.ivadimn.timer.databinding.FragmentCountdownBinding

class CountdownFragment : ViewBindingFragment<FragmentCountdownBinding>(FragmentCountdownBinding::inflate) {

    private val viewModel : CountdownViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingViewModel()
        initViews()

    }

    private fun bindingViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.countSeconds.collect {
                binding.timeTextView.setText(String.format("00:00:%s", it.toString()))
            }
        }
    }

    private fun initViews() {
        binding.startButton.setOnClickListener {
            viewModel.setTotalSeconds(20)
            viewModel.startCountdown()
        }
        binding.pauseButton.setOnClickListener {
            viewModel.pauseCountdown()
        }
    }

}