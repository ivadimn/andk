package ru.ivadimn.flowstudy.flowoperators

import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ivadimn.flowstudy.R
import ru.ivadimn.flowstudy.changedTextFlow
import ru.ivadimn.flowstudy.checkedChangesFlow
import ru.ivadimn.flowstudy.databinding.FragmentFlowOperatorsBinding
import ru.ivadimn.flowstudy.flowbasic.Gender
import ru.ivadimn.flowstudy.flowbasic.User
import ru.ivadimn.flowstudy.viewBinding
import java.io.IOException

class FlowOperatorsFragment : Fragment(R.layout.fragment_flow_operators) {

    private val binding : FragmentFlowOperatorsBinding by viewBinding(FragmentFlowOperatorsBinding::bind)

    private val users = listOf(
        User(1, "Ivan Petrov", 18, Gender.MALE),
        User(2, "Ivan Sergeev", 22, Gender.MALE),
        User(3, "Anna Ivanova", 25, Gender.FEMALE),
        User(4, "Elena Sidorova", 11, Gender.FEMALE),
        User(5, "Sergey Semenov", 28, Gender.MALE),
        User(6, "Elena Vasilieva", 33, Gender.FEMALE),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*viewLifecycleOwner.lifecycleScope.launch {

            flow {
                Log.d("Flow", "Thread name - ${Thread.currentThread().name}")
                    delay(1000)
                    emit(1)
                    delay(1000)
                    emit(2)
            }
                .flowOn(Dispatchers.IO)
                .onEach {  Log.d("Flow", "On each 1 - ${Thread.currentThread().name}") }
                .flowOn(Dispatchers.Main)
                .onEach {  Log.d("Flow", "On each 2 - ${Thread.currentThread().name}") }
                .map { it * it }
                .flowOn(Dispatchers.IO)
                .onEach {  Log.d("Flow", "Before collect - ${Thread.currentThread().name}") }
                .collect {
                    Log.d("Flow", "Thread name - ${Thread.currentThread().name}")
                    Log.d("Flow", "Collect - $it") }

            combine(
                binding.genderCheckBox.checkedChangesFlow().onStart { emit(false) },
                binding.searchEditText.changedTextFlow().onStart { emit("") }
            ) { isFemale, text  -> isFemale to text}
                .debounce(500)
                .distinctUntilChanged()
                .onEach {
                    showProgress(true)
                    Log.d("Flow", "before search - $it") }
                .mapLatest { (isFemale, text) -> searchUsers(isFemale, text) }
                .onEach {
                    showProgress(false)
                    Log.d("Flow", "After search - $it") }
                .map { user -> user.map { it.toString() }.joinToString("\n") }
                .collect {
                    binding.foundedTextView.text = it
                }
        }*/

        errorHandling()

    }

    private suspend fun searchUsers(isFemale : Boolean, query : String) : List<User> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            users.filter {
                val isGenderCorrect = (!isFemale || it.gender == Gender.FEMALE)
                it.name.contains(query, ignoreCase = true) && isGenderCorrect
            }
        }

    }

    private fun showProgress(show : Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun errorHandling() {
        flow {
            delay(1000)
            throw IOException("Network unavailable")
            emit(1)

        }
            .catch { emit(-1) }
            .map { it * 2 }
            .catch { Log.d("Flow", "Map exception - $it") }
            .map { error("Test exception") }
            .catch {
                Log.d("Flow", "map 2 exception - $it") }
            .onEach { Log.d("Flow", "element - $it")  }
            .catch { Log.d("Flow", "after oneach exception - $it") }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }
}