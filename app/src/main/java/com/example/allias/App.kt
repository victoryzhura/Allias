package com.example.allias

import android.app.Application
import android.content.SharedPreferences

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        pref = getSharedPreferences("pref", MODE_PRIVATE)
        pref.edit().putInt("pref", 0).apply()
    }
    companion object {
        lateinit var pref: SharedPreferences
    }

}