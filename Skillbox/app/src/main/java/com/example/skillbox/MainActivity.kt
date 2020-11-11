package com.example.skillbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView.text = s?.takeIf { it.isNotBlank() }
                ?.let { name ->  resources.getString(R.string.helloName, name)}
                clearButton.isEnabled = s?.let { it.isNotEmpty() } ?: false
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        clearButton.setOnClickListener { v ->
            inputName.setText("")
            Toast.makeText(this, R.string.cleared_text, Toast.LENGTH_SHORT ).show()
        }

        makeLongOperation.setOnClickListener{v ->
            makeLongOperation()
        }

        isInput.setOnCheckedChangeListener { buttonView, isChecked ->
            inputName.isEnabled = isChecked
        }
    }

    private fun makeLongOperation() {

        longOperationProgress.visibility = View.VISIBLE
        makeLongOperation.isEnabled = false
        Handler(Looper.myLooper()!!).postDelayed({
            longOperationProgress.visibility = View.GONE
            makeLongOperation.isEnabled = true
            Toast.makeText(this, R.string.long_operation_complete, Toast.LENGTH_SHORT ).show()
        }, 2000 )
    }
}