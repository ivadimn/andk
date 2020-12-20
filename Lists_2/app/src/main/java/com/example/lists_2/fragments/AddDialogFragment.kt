package com.example.lists_2.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.lists_2.DialogListener
import com.example.lists_2.Entity
import com.example.lists_2.Generator
import com.example.lists_2.R

class AddDialogFragment :DialogFragment() {

    private lateinit var dialogView : View
    private lateinit var radioGroup : RadioGroup

    private lateinit var nameEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var dop1EditText: EditText
    private lateinit var dop2EditText: EditText

    private var dialogListener : DialogListener? = null

    private val radioGroupClick : (checkedId: Int) ->Unit = { checkedId ->
        clearFields()
        when(checkedId) {
            R.id.actorTypeRadioButton -> {
                nameEditText.hint = getString(R.string.input_actor_name)
                countryEditText.hint = getString(R.string.input_actor_country)
                dop1EditText.apply {
                    hint = getString(R.string.input_birthday_hint)
                    inputType = InputType.TYPE_DATETIME_VARIATION_DATE
                }
                dop2EditText.apply {
                    hint = getString(R.string.input_films_hint)
                    inputType = InputType.TYPE_CLASS_TEXT
                }
            }
            R.id.carTypeRadioButton -> {
                nameEditText.hint = getString(R.string.input_car_name)
                countryEditText.hint = getString(R.string.input_car_country)
                dop1EditText.text.clear()
                dop1EditText.apply {
                    hint = getString(R.string.input_power_hint)
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
                dop2EditText.text.clear()
                dop2EditText.apply {
                    hint = getString(R.string.input_max_speed_hint)
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogListener = parentFragment?.let { it as DialogListener }
        initViews()
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_entity))
            .setView(dialogView)
            .create()
    }

    private fun initViews() {
        dialogView = requireActivity().layoutInflater.inflate(R.layout.fragment_dialog, null)
        radioGroup = dialogView.findViewById(R.id.typeEntityRadioGroup)

        nameEditText = dialogView.findViewById(R.id.nameEditText)
        countryEditText = dialogView.findViewById(R.id.countryEditText)
        dop1EditText = dialogView.findViewById(R.id.dop1EditText)
        dop2EditText = dialogView.findViewById(R.id.dop2EditText)


        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener { saveEntity() }
        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener { dismiss() }

        radioGroup.setOnCheckedChangeListener { _ , checkedId ->
            radioGroupClick(checkedId)
        }
    }

    private fun  clearFields() {
        nameEditText.text.clear()
        countryEditText.text.clear()
        dop1EditText.text.clear()
        dop2EditText.text.clear()
    }

    private fun saveEntity() {
        if (!isFieldsFilled()) {
            toast("Необходимо заполнить все поля")
            return
        }
        val entity = when(radioGroup.checkedRadioButtonId) {
            R.id.actorTypeRadioButton -> {
                Entity.Actor(
                    id = Generator.nextId(),
                    name = nameEditText.text.toString(),
                    imageLink = Generator.getImageActor(),
                    country = countryEditText.text.toString(),
                    birthday = dop1EditText.text.toString(),
                    films = dop2EditText.text.toString()
                )
            }
            R.id.carTypeRadioButton -> {
                Entity.Car(
                    id = Generator.nextId(),
                    name = nameEditText.text.toString(),
                    imageLink = Generator.getImageCar(),
                    country = countryEditText.text.toString(),
                    power = dop1EditText.text.toString().toInt(),
                    maxSpeed = dop2EditText.text.toString().toInt()
                )
            }
            else -> error("Unexpected identifier radioButton ${radioGroup.checkedRadioButtonId} ")
        }
        dialogListener?.onOkButtonClick(entity)
        dismiss()
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun isFieldsFilled()  : Boolean
            = nameEditText.text.isNotEmpty() && countryEditText.text.isNotEmpty()
            && dop1EditText.text.isNotEmpty() && dop2EditText.text.isNotEmpty()
}