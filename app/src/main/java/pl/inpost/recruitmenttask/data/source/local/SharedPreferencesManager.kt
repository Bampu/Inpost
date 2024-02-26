package pl.inpost.recruitmenttask.data.source.local

interface SharedPreferencesManager {
    fun getString(key: String, defaultValue: String): String?
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putString(key: String, value: String)
    fun putBoolean(key: String, value: Boolean)
}