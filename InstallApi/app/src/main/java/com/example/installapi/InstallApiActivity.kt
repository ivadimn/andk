package com.example.installapi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.instantapps.InstantApps
import com.google.android.instantapps.samples.install.R


class InstallApiActivity : AppCompatActivity() {
    private val postInstallIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://install-api.instantappsample.com/")
    ).addCategory(Intent.CATEGORY_BROWSABLE).putExtras(Bundle().apply {
        putString("The key to", "sending data via intent")
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isInstantApp = InstantApps.getPackageManagerCompat(this).isInstantApp
        findViewById<Button>(R.id.start_installation).apply {
            isEnabled = isInstantApp
            // Show the installation prompt only for an instant app.
            if (isInstantApp) {
                setOnClickListener {
                    InstantApps.showInstallPrompt(
                            this@InstallApiActivity,
                            postInstallIntent,
                            REQUEST_CODE,
                            REFERRER
                    )
                }
            }
        }
    }

    companion object {
        private val REFERRER = "InstallApiActivity"
        private val REQUEST_CODE = 7
    }
}