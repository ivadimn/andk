package ru.ivadimn.coroutineotus

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import retrofit2.await
import ru.ivadimn.coroutineotus.databinding.FragmentMainBinding
import ru.ivadimn.coroutineotus.network.*

class MainFragment : Fragment(R.layout.fragment_main), CoroutineScope by MainScope() {

    val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private val service by lazy { Network.getRetrofit().create(CatService::class.java) }

    private val job = Job()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch(job) {
            Glide.with(binding.root)
                .load(loadUrl())
                .into(binding.imageView)
        }
    }

    private suspend fun loadUrl() = withContext(Dispatchers.IO) {
        service.getCat().awaitResult().file
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }
}