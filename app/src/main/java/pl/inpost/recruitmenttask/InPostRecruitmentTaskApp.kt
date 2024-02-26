package pl.inpost.recruitmenttask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InPostRecruitmentTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {
        private lateinit var instance: InPostRecruitmentTaskApp

        fun getAppContext(): Context {
            return instance.applicationContext
        }

        fun getSharedPreferences(): SharedPreferences {
            return instance.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        }
    }
}