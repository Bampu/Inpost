package pl.inpost.recruitmenttask.data.source.local

import android.content.Context
import android.content.SharedPreferences

class CustomSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "shipment_preferences", Context.MODE_PRIVATE
    )

    // Methods for reading data

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    // Methods for writing data

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    // Additional methods if needed

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun setActionExecuted(actionKey: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(actionKey, true)
        editor.apply()
    }

    fun isActionExecuted(actionKey: String): Boolean {
        return sharedPreferences.getBoolean(actionKey, false)
    }
}