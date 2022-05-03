package com.example.allias.ui.utilily

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object Util {
    @SuppressLint("SimpleDateFormat")
    fun getDate(milliSeconds: Long, dateFormat: String?): String {
        val formatter = SimpleDateFormat(dateFormat)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.millsToDateFormat(): String {
        val date = Date(this)
        val formatter: DateFormat = SimpleDateFormat("mm:ss")
        return formatter.format(date)
    }
}
