package com.example.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ActivityBackstack : AppCompatActivity(R.layout.activity_backstack) {

    private lateinit var addToBackstackCheckBox: CheckBox
    private lateinit var stateNameInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {

        addToBackstackCheckBox = findViewById(R.id.addToBackstackCheckBox)
        stateNameInput = findViewById(R.id.stateNameInput)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
                    .add(R.id.popContainer, InfoFragment.newInstance("text"))
            if (needAddToBackstack()) {
                transaction.addToBackStack(getStateName())
            }
            transaction.commit()
        }

        findViewById<Button>(R.id.replaceButton).setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
                    .add(R.id.popContainer, InfoFragment.newInstance("text"))
                    .apply {
                        if (needAddToBackstack()) addToBackStack(getStateName())
                    }
                    .commit()
        }

        findViewById<Button>(R.id.popButton).setOnClickListener {
            val stateName  = getStateName()
            if (stateName != null) {
                supportFragmentManager.popBackStack(stateName, 0)
            }
            else {
                supportFragmentManager.popBackStack()
            }
        }
        addToBackstackCheckBox.setOnCheckedChangeListener { _, isChecked ->
            stateNameInput.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

    }

    private fun needAddToBackstack() : Boolean {
        return addToBackstackCheckBox.isChecked
    }


    private fun getStateName() : String ? {
        return stateNameInput.text.toString().takeIf { it.isNotBlank() }
    }

}