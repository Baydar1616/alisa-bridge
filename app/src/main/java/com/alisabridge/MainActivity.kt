package com.alisabridge

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val requestMicPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            AlertDialog.Builder(this)
                .setTitle("Microphone required")
                .setMessage("Alisa Bridge uses the microphone for local wake-word detection.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                requestMicPermission.launch(Manifest.permission.RECORD_AUDIO)
                return@setOnClickListener
            }
            startService(Intent(this, service.WakeWordService::class.java))
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            stopService(Intent(this, service.WakeWordService::class.java))
        }

        findViewById<Button>(R.id.btn_wake_phrases).setOnClickListener {
            startActivity(Intent(this, WakePhrasesActivity::class.java))
        }

        findViewById<Button>(R.id.btn_settings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        findViewById<Button>(R.id.btn_diagnostics).setOnClickListener {
            startActivity(Intent(this, DiagnosticsActivity::class.java))
        }

        findViewById<Button>(R.id.btn_privacy).setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        findViewById<Button>(R.id.btn_battery).setOnClickListener {
            // Open battery optimization settings for user
            startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
        }
    }
}
