package ru.ivadimn.notifications.firebase

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import ru.ivadimn.notifications.R
import ru.ivadimn.notifications.databinding.FirebaseFragmentBinding
import ru.ivadimn.notifications.viewBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseFragment : Fragment(R.layout.firebase_fragment) {

    private val binding : FirebaseFragmentBinding by viewBinding(FirebaseFragmentBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getTokenButton.setOnClickListener {
            getToken()
        }
    }

    private fun getToken() {
        lifecycleScope.launch {
            val token = getTokenSuspend()
            Log.d("Notification", "Token $token")
        }
    }

    private suspend fun getTokenSuspend() : String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener {
                continuation.resume(null)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
    }
}