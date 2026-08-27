package com.alisabridge.wakeword

import android.content.Context
import android.util.Log

class WakeWordManager(private val context: Context) {
    private var engine: WakeWordEngine? = null

    init {
        // Default to simulated engine for initial builds. Real engines require model files.
        engine = SimulatedEngine { phrase -> onDetected(phrase) }
        engine?.initialize()
    }

    fun start() {
        engine?.start()
    }

    fun stop() {
        engine?.stop()
    }

    fun setEngine(e: WakeWordEngine) {
        engine?.stop()
        engine?.release()
        engine = e
        engine.initialize()
    }

    private fun onDetected(phrase: String) {
        Log.i("WakeWordManager", "Wake word detected: $phrase")
        // Launch assistant via AssistantLauncher
        com.alisabridge.assistant.AssistantLauncher(context).launchAssistant()
    }
}
