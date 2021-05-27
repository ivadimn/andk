package ru.ivadimn.servicestudy.works

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.ivadimn.servicestudy.R
import ru.ivadimn.servicestudy.databinding.FragmentWorkManagerBinding
import ru.ivadimn.servicestudy.viewBinding

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

    private val binding : FragmentWorkManagerBinding by viewBinding(FragmentWorkManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadButton.setOnClickListener {
            startDownload()
        }

    }

    private fun startDownload() {

    }
}