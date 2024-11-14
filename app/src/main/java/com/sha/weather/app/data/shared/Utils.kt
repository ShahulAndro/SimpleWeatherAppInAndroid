package com.sha.weather.app.data.shared

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
class Utils {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"

        fun convertEpochToDate(epochTimeMillis: Long): String {
            // Create a Date object from the epoch time (milliseconds)
            val date = Date(epochTimeMillis)

            // Format the date using SimpleDateFormat
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd")
            return dateFormat.format(date)
        }

        fun getWeatherIconUrl(weatherCode: String): String {
            // OpenWeatherMap URL format for weather icons
            return "https://openweathermap.org/img/wn/$weatherCode@2x.png"
        }
    }
}