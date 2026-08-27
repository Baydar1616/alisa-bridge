package com.alisabridge.assistant

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log

/**
 * Attempts to launch the system assistant using documented intents:
 * - ACTION_VOICE_ASSIST
 * - ACTION_ASSIST
 * Resolves candidate activities and attempts to start them. Does not use undocumented APIs.
 */
class AssistantLauncher(private val context: Context) {
    fun launchAssistant() {
        val pm = context.packageManager
        val voiceAssist = Intent(Intent.ACTION_VOICE_ASSIST)
        val assist = Intent(Intent.ACTION_ASSIST)

        val resolvedVoice = pm.resolveActivity(voiceAssist, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedVoice != null) {
            try {
                voiceAssist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(voiceAssist)
                Log.i("AssistantLauncher", "Launched ACTION_VOICE_ASSIST via ${'$'}{resolvedVoice.activityInfo.packageName}")
                return
            } catch (e: Exception) {
                Log.w("AssistantLauncher", "Failed to launch ACTION_VOICE_ASSIST: ${'$'}{e.message}")
            }
        }

        val resolvedAssist = pm.resolveActivity(assist, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedAssist != null) {
            try {
                assist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(assist)
                Log.i("AssistantLauncher", "Launched ACTION_ASSIST via ${'$'}{resolvedAssist.activityInfo.packageName}")
                return
            } catch (e: Exception) {
                Log.w("AssistantLauncher", "Failed to launch ACTION_ASSIST: ${'$'}{e.message}")
            }
        }

        // Enumerate handlers for debugging
        val handlers = pm.queryIntentActivities(voiceAssist, PackageManager.MATCH_DEFAULT_ONLY)
        val pkgs = handlers.map { it.activityInfo.packageName }
        Log.w("AssistantLauncher", "No assistant resolved. Known handlers: ${'$'}pkgs")
        // In diagnostics, report inability to invoke assistant on this device/configuration
    }
}
