package ru.ivadimn.servicestudy.works

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.work.*
import ru.ivadimn.servicestudy.R
import ru.ivadimn.servicestudy.databinding.FragmentWorkManagerBinding
import ru.ivadimn.servicestudy.viewBinding
import java.util.concurrent.TimeUnit

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

    private val binding : FragmentWorkManagerBinding by viewBinding(FragmentWorkManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            .observe(viewLifecycleOwner, {handleWorkInfo(it.first())})

        binding.loadButton.setOnClickListener {
            startDownload()
        }

        binding.stopButton.setOnClickListener {
            stopDownload()
        }
    }


    private fun stopDownload() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(DOWNLOAD_WORK_ID)
    }

    private fun startDownload() {
        val urlToDownload = binding.urlEditText.text.toString()

        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()



       WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.REPLACE,  workRequest)


    }

    private fun handleWorkInfo(workInfo: WorkInfo) {

        Log.d("Service", "WorkInfo state - ${workInfo.state}")
        val isFinished = workInfo.state.isFinished
        binding.loadButton.isVisible = isFinished
        binding.stopButton.isVisible = !isFinished
        binding.progressBar.isVisible = !isFinished

        if (isFinished) {
            Toast.makeText(requireContext(), "Work is finished with state ${workInfo.state}" , Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val DOWNLOAD_WORK_ID = "DownloadWork"
    }
}