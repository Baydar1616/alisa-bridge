package com.alisabridge.wakeword

import android.content.Context
import android.util.Log

/**
 * PorcupineEngine wiring helper.
 * This implementation doesn't link against the native Porcupine SDK (no proprietary binaries included).
 * It enumerates any .ppn files placed under the assets/porcupine/ directory and exposes their names
 * for diagnostics and for consumers to decide whether to fall back to the SimulatedEngine.
 */
class PorcupineEngine(private val context: Context) : WakeWordEngine {
    private var running = false
    private val discoveredModels = mutableListOf<String>()

    companion object {
        /**
         * Returns list of filenames under assets/porcupine/ or an empty list if none found.
         */
        fun listModels(context: Context): List<String> {
            return try {
                val assets = context.assets.list("porcupine") ?: emptyArray()
                assets.filter { it.endsWith(".ppn") }.toList()
            } catch (e: Exception) {
                Log.w("PorcupineEngine", "Failed to list porcupine assets: ${'$'}{e.message}")
                emptyList()
            }
        }
    }

    override fun initialize(): Result<Unit> {
        return try {
            discoveredModels.clear()
            val models = listModels(context)
            discoveredModels.addAll(models)
            if (models.isEmpty()) {
                Log.i("PorcupineEngine", "No .ppn models found in assets/porcupine/. Using SimulatedEngine fallback.")
            } else {
                Log.i("PorcupineEngine", "Discovered Porcupine models: ${'$'}models")
                // NOTE: Actual native SDK initialization would occur here when linked.
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun start(): Result<Unit> {
        running = true
        Log.i("PorcupineEngine", "Start called. Models loaded=${'$'}{discoveredModels.size}")
        // Real engine would begin audio capture / native callbacks here.
        return Result.success(Unit)
    }

    override fun stop(): Result<Unit> {
        running = false
        Log.i("PorcupineEngine", "Stop called")
        return Result.success(Unit)
    }

    override fun release(): Result<Unit> {
        running = false
        discoveredModels.clear()
        Log.i("PorcupineEngine", "Release called")
        return Result.success(Unit)
    }

    override fun isRunning(): Boolean = running

    fun getDiscoveredModels(): List<String> = discoveredModels.toList()
}
