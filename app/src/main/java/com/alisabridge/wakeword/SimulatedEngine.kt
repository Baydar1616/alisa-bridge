package com.alisabridge.wakeword

import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * A simulated wake-word engine for demo and testing.
 * It triggers a detection event every 20 seconds and provides a manual trigger via API.
 * Intended ONLY for testing UI and assistant invocation flow. Production requires real models.
 */
class SimulatedEngine(private val onDetect: (String) -> Unit) : WakeWordEngine {
    private var running = false
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (running) {
                Log.i("SimulatedEngine", "Simulated detection: Alisa")
                onDetect.invoke("Alisa")
                handler.postDelayed(this, 20000)
            }
        }
    }

    override fun initialize(): Result<Unit> {
        return Result.success(Unit)
    }

    override fun start(): Result<Unit> {
        running = true
        handler.postDelayed(runnable, 5000)
        return Result.success(Unit)
    }

    override fun stop(): Result<Unit> {
        running = false
        handler.removeCallbacks(runnable)
        return Result.success(Unit)
    }

    override fun release(): Result<Unit> {
        stop()
        return Result.success(Unit)
    }

    override fun isRunning(): Boolean = running

    fun manualTrigger() {
        onDetect.invoke("Alisa")
    }
}
