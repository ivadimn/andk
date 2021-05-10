package ru.ivadimn.flowstudy

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.changedTextFlow() : Flow<String> {
    return callbackFlow<String> {
        val changeTextListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) { }

        }
        this@changedTextFlow.addTextChangedListener(changeTextListener)
        awaitClose {
            Log.d("Flow", "Flow finished")
            this@changedTextFlow.removeTextChangedListener(changeTextListener)
        }
    }
}