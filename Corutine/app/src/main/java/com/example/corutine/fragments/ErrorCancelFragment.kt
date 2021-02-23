package com.example.corutine.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.corutine.R
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class ErrorCancelFragment : Fragment(R.layout.fragment_error_cancel) {

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("ErrorCancelFragment", "error from CoroutineExceptionHandler", throwable)
        launchCoroutineAfterError()
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)

    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelScopeWithYield()
    }

    private fun errorHandleExample() {
        scope.launch {
            error("Unexpected error!")
        }
    }

    private fun launchCoroutineAfterError() {
        scope.launch {
            Log.d("ErrorCancelFragment", "launched after error ")
        }
    }

    private fun nonCancellableExample() {
        scope.launch {
            var i = 0
            withContext(Dispatchers.Default) {

                while (true) {
                    Thread.sleep(500)
                    Log.d("ErrorCancelFragment", "log = $i")
                    i++
                }
            }
        }
    }

    private fun cancellableExample() {
        scope.launch {
            var i = 0
            withContext(Dispatchers.Default) {
                suspendCancellableCoroutine<Unit> { coroutine ->
                    coroutine.invokeOnCancellation {
                        Log.d("ErrorCancelFragment", "invokeOnCancellation ")
                    }
                    while (true) {
                        Thread.sleep(500)
                        Log.d("ErrorCancelFragment", "log = $i")
                        i++
                    }
                }
            }
        }
    }

    private fun cancelScopeChildrenCoroutine() {
        scope.launch {
            delay(3000)
            error("test exception!!")
        }
        scope.launch {
            var i = 0
            while (true) {

                delay(500)
                Log.d("ErrorCancelFragment", "log = $i")
                i++
            }
        }
    }

    private fun cancelScopeWithYield() {
        scope.launch {
            var i = 0
            withContext(Dispatchers.Default) {
                while (true) {
                    yield()
                    Thread.sleep(500)
                    Log.d("ErrorCancelFragment", "log = $i")
                    i++
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelButton = requireView().findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            scope.coroutineContext.cancelChildren()
        }
    }
}