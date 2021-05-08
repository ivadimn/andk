package ru.ivadimn.flowstudy.flowbasic

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.ivadimn.flowstudy.R
import ru.ivadimn.flowstudy.databinding.FragmentFlowBasicBinding
import ru.ivadimn.flowstudy.viewBinding
import kotlin.random.Random

class FlowBasicFragment : Fragment(R.layout.fragment_flow_basic) {
    private val binding : FragmentFlowBasicBinding by viewBinding(FragmentFlowBasicBinding::bind)

    private var currentJob : Job? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val generator : Flow<Int> = createFlowGenerator()

        binding.startButton.setOnClickListener {
            currentJob?.cancel()
            currentJob = viewLifecycleOwner.lifecycleScope.launch {
                generator.collect {
                    Log.d("Flow", "consume value = $it")
                    binding.random1TextView.text = it.toString()
                }
            }
        }
   }

    private fun createFlowGenerator() : Flow<Int> {
        return flow {
            Log.d("Flow", "Start flow")
            while (true) {
                val value = Random.nextInt()
                Log.d("Flow", "emit value = $value")
                emit(value)
                Log.d("Flow", "delay")
                delay(1000)
            }
        }
    }
}