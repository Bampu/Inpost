package pl.inpost.recruitmenttask

import pl.inpost.recruitmenttask.data.source.local.SharedPreferencesManager

class MockSharedPreferencesManager : SharedPreferencesManager {

    private val preferencesMap: MutableMap<String, String> = mutableMapOf()

    override fun getString(key: String, defaultValue: String): String? {
        return preferencesMap[key] ?: defaultValue
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return (preferencesMap[key] ?: defaultValue) as? Boolean ?: false
    }

    override fun putString(key: String, value: String) {
        preferencesMap[key] = value
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferencesMap[key] = value.toString()
    }

    fun clear() {
        preferencesMap.clear()
    }
}