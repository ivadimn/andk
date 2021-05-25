package ru.ivadimn.servicestudy.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object DownloadState {

    private val downloadStateFlow = MutableStateFlow(false)
    val downloadState : StateFlow<Boolean>
    get() = downloadStateFlow

    fun changeDownloadState(isDownloading : Boolean) {
        downloadStateFlow.value = isDownloading
    }
}