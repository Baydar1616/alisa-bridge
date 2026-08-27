package com.alisabridge.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("alisa_prefs")

class SettingsRepository(private val context: Context) {
    companion object {
        val KEY_SERVICE_ENABLED = booleanPreferencesKey("service_enabled")
    }

    suspend fun setServiceEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_SERVICE_ENABLED] = enabled
        }
    }

    suspend fun isServiceEnabled(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[KEY_SERVICE_ENABLED] ?: false
    }
}
