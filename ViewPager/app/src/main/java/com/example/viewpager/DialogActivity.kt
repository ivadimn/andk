package com.example.viewpager

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DialogActivity : AppCompatActivity(R.layout.activity_dialog) {

    private var dialog : AlertDialog? = null

    private lateinit var simpleDialogButton: Button
    private lateinit var showDialogWithButton: Button
    private lateinit var showSingleChoiceDialogButton: Button
    private lateinit var showMultiChoiceDialogButton: Button
    private lateinit var showDialogWithCustomLayoutButton: Button
    private lateinit var showDialogFragmentButton: Button
    private lateinit var showBottomSheetDialogButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        simpleDialogButton = findViewById(R.id.simpleDialogButton)
        showDialogWithButton = findViewById(R.id.showDialogWithButton)
        showSingleChoiceDialogButton = findViewById(R.id.showSingleChoiceDialogButton)
        showMultiChoiceDialogButton = findViewById(R.id.showMultiChoiceDialogButton)
        showDialogWithCustomLayoutButton = findViewById(R.id.showDialogWithCustomLayoutButton)
        showDialogFragmentButton = findViewById(R.id.showDialogFragmentButton)
        showBottomSheetDialogButton = findViewById(R.id.showBottomSheetDialogButton)


        simpleDialogButton.setOnClickListener { showSimpleDialog() }
        showDialogWithButton.setOnClickListener { showDialogWithButton() }
        showSingleChoiceDialogButton.setOnClickListener { showSingleChoiceDialog() }
        showMultiChoiceDialogButton.setOnClickListener { showMultiChoiceDialog() }
        showDialogWithCustomLayoutButton.setOnClickListener { showWithCustomLayoutDialog() }
        showDialogFragmentButton.setOnClickListener { showDialogFragment() }
        showBottomSheetDialogButton.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showSimpleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Dialog title")
            .setMessage("This a message for dialog")
            .create()

        dialog.show()
    }

    private fun showDialogWithButton() {
        AlertDialog.Builder(this)
                .setTitle("Delete item")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    toast("Clicked Yes")})
                .setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
                    toast("Clicked No")})
                .setNeutralButton("Neutral", DialogInterface.OnClickListener { _, _ ->
                    toast("Clicked Neutral")})
                .create()
                .show()
    }

    private fun showSingleChoiceDialog() {
        val mailProviders = arrayOf("google", "hotmail", "mail", "yandex", "rambler")
        AlertDialog.Builder(this)
                .setTitle("Select mail provider")
                .setItems(mailProviders) { _, which -> toast("Selected - ${mailProviders[which]}")}
                .create()
                .show()
    }

    private fun showMultiChoiceDialog() {
        val things = arrayOf("glass", "plate", "termos", "kinfe", "bottler", "other")
        val checkItems = BooleanArray(6)
        AlertDialog.Builder(this)
                .setTitle("Select things to way")
                .setMultiChoiceItems(things, checkItems,
                        DialogInterface.OnMultiChoiceClickListener {
                            _, which, isChecked ->  toast("Selected $which - $isChecked")})
                .create()
                .show()
    }

    private fun showWithCustomLayoutDialog() {
        val linearLayout = layoutInflater.inflate(R.layout.dialog_attension, null)
        dialog = AlertDialog.Builder(this)
                //.setView(R.layout.dialog_attension)
                .setView(linearLayout)
                .setPositiveButton("Ok") {_, _ -> }
                .create()
        dialog?.show()
    }

    private fun showDialogFragment() {
        ConfirmationDialogFragment()
                .show(supportFragmentManager,"ConfirmationDialog")
    }

    private fun hideDialog() {
        supportFragmentManager.findFragmentByTag("ConfirmationDialog")
                ?.let { it as? ConfirmationDialogFragment }
                ?.dismiss()
    }

    private fun showBottomSheetDialog() {

    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}