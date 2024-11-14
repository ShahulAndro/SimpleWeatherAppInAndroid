package com.sha.weather.app.data.shared

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Shahul Hameed on 14/11/2024.
 */

object SharedPrefUtils {
    private const val PREFS_NAME = "weather_prefs"
    private const val CITY_KEY = "city_key"

    fun saveCity(context: Context, city: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(CITY_KEY, city).apply()
    }

    fun getCity(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(CITY_KEY, null)
    }
}