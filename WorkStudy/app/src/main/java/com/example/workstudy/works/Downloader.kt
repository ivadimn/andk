package com.example.workstudy.works

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workstudy.App
import com.example.workstudy.exceptions.DownloadException
import com.example.workstudy.exceptions.NetException
import com.example.workstudy.network.Network
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Downloader(
    val context: Context,
    val params : WorkerParameters
) : CoroutineWorker(context, params) {

    private val fileApi = Network.fileApi
    private var call : Call<ResponseBody>? = null

    override suspend fun doWork(): Result {

        Log.d("Work", "DoWork running ...")
        val url = inputData.getString(DOWNLOAD_URL_KEY)
        val fileName = inputData.getString(DOWNLOAD_FILENAME_KEY)
        if (url == null || fileName == null) {
            return Result.failure()
        }

        return try {
            downloadCall(url, fileName)
            Result.success()
        }
        catch (ex : DownloadException) {
            Log.d("Work", "Download exception - ${ex.message} ")
            val errorData = workDataOf(DOWNLOAD_RESULT_KEY to ex.message)
            call?.cancel()
            Result.failure(errorData)
        }
        catch (ex : NetException) {
            Log.d("Work", "Newwork exception - ${ex.message} ")
            call?.cancel()
            Result.retry()
        }

        finally {
            call = null
            Log.d("Work", "DoWork was finishing ... ")
        }
    }


    private suspend fun download(url : String, fileName : String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw RuntimeException("External storage unavailable")
        }
        val folder = App.context.getExternalFilesDir(FILES_DATASTORE_NAME)
        val file = File(folder, fileName)
        file.outputStream().use { outStream ->
            fileApi.get(url)
                .byteStream().use { inStream ->
                    delay(7000)
                    inStream.copyTo(outStream)
                }
        }
    }

    private suspend fun downloadCall(url: String, fileName: String) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            throw RuntimeException("External storage unavailable")
        }
        val folder = App.context.getExternalFilesDir(FILES_DATASTORE_NAME)
        val file = File(folder, fileName)
        delay(7000)
        suspendCoroutine<String> {
            continuation ->
            call = fileApi.get1(url)
            call?.enqueue(
                object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            file.outputStream().use { outStream ->
                                response.body()?.bytes()?.inputStream().use { inputStream ->
                                    inputStream?.copyTo(outStream)
                                }
                            }
                            continuation.resume("Download complete")
                        }
                        else {
                            val msg = "Error code - ${response.code()},  ${response.message()}"
                            continuation.resumeWithException(DownloadException(msg))
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        continuation.resumeWithException(NetException(t.message ?: "сеть недоступна" ))
                    }
                }
            )
        }
   }

    companion object {
        private const val FILES_DATASTORE_NAME = "FilesDatastore"
        const val DOWNLOAD_URL_KEY = "DownloadUrl"
        const val DOWNLOAD_FILENAME_KEY = "DownloadFileName"
        const val DOWNLOAD_RESULT_KEY = "DownloadResult"
        const val DOWNLOAD_WORK_ID = "DownloadWorkId"
    }

}