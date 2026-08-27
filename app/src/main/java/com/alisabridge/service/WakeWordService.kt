package com.alisabridge.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class WakeWordService : Service() {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var running = false
    private var wakeWordManager: com.alisabridge.wakeword.WakeWordManager? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        wakeWordManager = com.alisabridge.wakeword.WakeWordManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, buildNotification())
        startDetection()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        stopDetection()
        scope.cancel()
    }

    private fun buildNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, com.alisabridge.MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, "alisa_bridge_channel")
            .setContentTitle("Alisa Bridge")
            .setContentText("Wake-word service is running")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alisa_bridge_channel",
                "Alisa Bridge",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun startDetection() {
        if (running) return
        running = true
        wakeWordManager?.start()
        Log.i("WakeWordService", "Service started and detection started")
    }

    private fun stopDetection() {
        if (!running) return
        running = false
        wakeWordManager?.stop()
        Log.i("WakeWordService", "Service stopped")
    }
}
