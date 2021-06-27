package com.example.workstudy.works

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.example.workstudy.App
import com.example.workstudy.R
import com.example.workstudy.databinding.FragmentDownloadBinding
import com.example.workstudy.toast
import com.example.workstudy.viewBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

private val urls = listOf(
    "https://images.pexels.com/photos/2534475/pexels-photo-2534475.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "https://images.pexels.com/photos/3803593/pexels-photo-3803593.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "https://player.vimeo.com/external/274645324.sd.mp4?s=75fb119219002bc092e045853b62209ace9ef282&profile_id=164&oauth2_token_id=57447761",
    "https://player.vimeo.com/external/441810679.sd.mp4?s=cd2329aa50c4581ae60f03da0408aac2b3bcdd75&profile_id=139&oauth2_token_id=57447761",
    "https://player.vimeo.com/external/377069770.sd.mp4?s=c7aaf065ac037a1a1830527abce343cca944263e&profile_id=139&oauth2_token_id=57447761",
    "https://player.vimeo.com/external/363625144.sd.mp4?s=3216e0d893a4e33af6af941737afe256ef6c42b0&profile_id=139&oauth2_token_id=57447761",
    "https://player.vimeo.com/external/538033350.sd.mp4?s=a562b2743d5e01836ffaa43769629eda0c205017&profile_id=165&oauth2_token_id=57447761",
    "https://images.freeimages.com/images/large-previews/5b8/starfish-1377326.jpg",
    "https://images.freeimages.com/images/large-previews/fa8/swimming-pool-3-1378269.jpg",
    "https://images.freeimages.com/images/large-previews/f5d/butterfly-1378183.jpg",
    "https://images.freeimages.com/images/small-previews/25d/eagle-1523807.jpg",
    "https://images.freeimages.com/images/small-previews/0db/tropical-bird-1390996.jpg",
    "https://images.freeimages.com/images/small-previews/1c1/blue-water-sailing-1-1437302.jpg",
    "https://images.pexels.com/photos/7456416/pexels-photo-7456416.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "https://player.vimeo.com/external/526493830.sd.mp4?s=a4ddffae21c9cd5f2c767e9e68f5369dde7e53de&profile_id=165&oauth2_token_id=57447761",
    "https://player.vimeo.com/external/531195010.sd.mp4?s=74320c75574f8ddc3b67048645f6612a5f9abd44&profile_id=165&oauth2_token_id=57447761"
)

class DownloadFragment : Fragment(R.layout.fragment_download) {
    private val binding : FragmentDownloadBinding by viewBinding(FragmentDownloadBinding::bind)

    val workInfo : LiveData<MutableList<WorkInfo>> =
        WorkManager.getInstance(App.context)
            .getWorkInfosForUniqueWorkLiveData(Downloader.DOWNLOAD_WORK_ID)

    private var isWorkRunning : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        workInfo.observe(viewLifecycleOwner, ::handelWorkInfo)
    }



    private fun initViews() {
        Log.d("Work", "Init views")
        createUrlList()
        binding.startDownloadButton.setOnClickListener {
            runWork()
        }
        binding.stopDownloadButton.setOnClickListener {
            stopDownload()
        }
        binding.retryButton.setOnClickListener {
            runWork()
        }
        binding.cancelButton.setOnClickListener {
            cancel()
        }
    }

    private fun handelWorkInfo(workInfos : List<WorkInfo>) {
        if(workInfos.isEmpty() || !isWorkRunning ) return
        Log.d("Work", "Work state - ${workInfos[0].state}")

        val errorData = workInfos[0].outputData.getString(Downloader.DOWNLOAD_RESULT_KEY)

        when(workInfos[0].state) {
            WorkInfo.State.ENQUEUED -> showStateText("Ожидание загрузки")
            WorkInfo.State.RUNNING -> showStateText("Загрузка")
            WorkInfo.State.CANCELLED -> {
                showStateText("Загрузка была прервана")
                isWorkRunning = false
            }
            WorkInfo.State.FAILED ->  {
                showStateText("Загрузка завершена с ошибкой")
                isWorkRunning = false
            }
            WorkInfo.State.SUCCEEDED -> {
                showStateText("Загружка завершена успешно")
                toast("Download was finished success")
                isWorkRunning = false
            }
        }

        if (errorData != null) {
            showStateText(errorData)
        }

        updateLoadingState(!workInfos[0].state.isFinished, errorData != null)
    }

    private fun updateLoadingState(isLoading : Boolean, isError : Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.startDownloadButton.isVisible = !isLoading && !isError
        binding.stopDownloadButton.isVisible = isLoading
        binding.retryButton.isVisible = !isLoading && isError
        binding.cancelButton.isVisible = !isLoading && isError
    }

    private fun showStateText(stateText : String) {
        binding.stateTextView.text = stateText
    }

    private fun createUrlList() {
        val adapter = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_spinner_item,
            urls)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.urlSpinner.adapter = adapter

        binding.urlSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.urlEditText.setText(adapter.getItem(position) ?: "")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) { }

            }

    }

    private fun runWork() {
        val url = binding.urlEditText.text.toString()
        if (url.isNotEmpty()) {
            val fileName = buildFileName(url)
            startDownload(url, fileName)
            isWorkRunning = true
        }
    }

    private fun startDownload(url: String, fileName : String) {
        val downloadData = workDataOf(
            Downloader.DOWNLOAD_URL_KEY to url,
            Downloader.DOWNLOAD_FILENAME_KEY to fileName
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<Downloader>()
            .setInputData(downloadData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.SECONDS)
           // .setConstraints(workConstraints)
            .build()


        WorkManager.getInstance(App.context)
            .enqueueUniqueWork(Downloader.DOWNLOAD_WORK_ID, ExistingWorkPolicy.REPLACE,  workRequest)
    }

    private fun stopDownload() {
        WorkManager.getInstance(App.context)
            .cancelUniqueWork(Downloader.DOWNLOAD_WORK_ID)
    }

    private fun cancel() {
        showStateText("")
        updateLoadingState(isLoading =  false, isError = false)
    }

    fun buildFileName(url : String) : String{
        return "${url.substring(url.lastIndexOf("/") + 1)}"
    }

    companion object {
        const val SELECTED_URL_KEY = "SelectedUrlKey"
    }
}