package pl.inpost.recruitmenttask.data.source.local

import android.content.SharedPreferences

class SharedPreferencesManagerImpl(private val sharedPreferences: SharedPreferences) : SharedPreferencesManager {
    override fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

}