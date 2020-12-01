package com.example.intents

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    private val launcher = prepareCall(ActivityResultContracts.TakePicture()) {bitmap ->
        bitmap ?: toast("Photo capture was canceled")
        resultImageView.setImageBitmap(bitmap)
    }

    private lateinit var emailAddressEditText : TextView
    private lateinit var subjectEditText : TextView
    private lateinit var resultImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailAddressEditText = findViewById(R.id.emailAddressEditText)
        subjectEditText = findViewById(R.id.subjectEditText)
        resultImageView = findViewById(R.id.resultPhotoImageView)

        findViewById<Button>(R.id.startExplicitButton).setOnClickListener {

           val message  = messageEditText.text.toString()

           startActivity(SecondActivity.getIntent(this, message))
        }

        findViewById<Button>(R.id.sendEmailButton).setOnClickListener {
            val emailAddress = emailAddressEditText.text.toString()
            val subject = subjectEditText.text.toString()

            val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
            if (!isEmailValid) {
                toast("Invalid email address")
            }
            else {
                val intentEmail = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                }
                if (intentEmail.resolveActivity(packageManager) != null) {
                    startActivity(intentEmail)
                }
                else {
                    toast("No component to handle intent")
                }
            }
        }

        findViewById<Button>(R.id.takePhotoButton).setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun toast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun dispatchTakePictureIntent() {
        val isCameraPermissionGranted = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

        if (!isCameraPermissionGranted) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA), 1)
        }
        else {
            /*val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(cameraIntent, PHOTO_REQUEST_CODE)
            } */
            launcher.launch(null)
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PHOTO_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val previewBitmap = data?.getParcelableExtra<Bitmap>("data")
                resultImageView.setImageBitmap(previewBitmap)
            }
            else {
                toast("Photo capture was canceled")
            }

        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    } */

    companion object {
        private val PHOTO_REQUEST_CODE = 111
    }

}