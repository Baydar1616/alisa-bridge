package com.alisabridge.wakeword

import android.content.Context
import android.util.Log

/**
 * Placeholder for Porcupine engine integration.
 * This implementation is a stub: it does not include Picovoice libraries or models.
 * To enable real detection, add Picovoice/Porcupine SDK and place compiled keyword models in assets.
 */
class PorcupineEngine(private val context: Context) : WakeWordEngine {
    private var running = false

    override fun initialize(): Result<Unit> {
        // TODO: initialize native libraries and load .ppn models from assets/porcupine/
        Log.i("PorcupineEngine", "Initialized stub (no models). Place models in assets/porcupine/")
        return Result.success(Unit)
    }

    override fun start(): Result<Unit> {
        running = true
        Log.i("PorcupineEngine", "Start called (stub)")
        return Result.success(Unit)
    }

    override fun stop(): Result<Unit> {
        running = false
        Log.i("PorcupineEngine", "Stop called (stub)")
        return Result.success(Unit)
    }

    override fun release(): Result<Unit> {
        running = false
        Log.i("PorcupineEngine", "Release called (stub)")
        return Result.success(Unit)
    }

    override fun isRunning(): Boolean = running
}
