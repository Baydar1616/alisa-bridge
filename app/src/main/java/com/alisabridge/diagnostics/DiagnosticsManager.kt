package com.alisabridge.diagnostics

import android.content.Context

class DiagnosticsManager(private val context: Context) {
    fun getAssistantHandlers(): List<String> {
        val pm = context.packageManager
        val intent = android.content.Intent(android.content.Intent.ACTION_VOICE_ASSIST)
        val resolveInfo = pm.queryIntentActivities(intent, 0)
        return resolveInfo.map { it.activityInfo.packageName }
    }
}
