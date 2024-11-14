package com.sha.weather.app.data.shared

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.sha.weather.app.R

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
object WeatherUtils {

    fun getWeatherColor(context: Context, weatherDescription: String): Int {
        return when (weatherDescription.lowercase()) {
            "clear", "sunny" -> ContextCompat.getColor(context, R.color.sunny_color_alpha)
            "cloudy", "overcast" -> ContextCompat.getColor(context, R.color.cloudy_color_alpha)
            "rainy", "drizzle" -> ContextCompat.getColor(context, R.color.rainy_color_alpha)
            "storm", "thunderstorm" -> ContextCompat.getColor(context, R.color.stormy_color_alpha)
            else -> ContextCompat.getColor(context, R.color.cloudy_color_alpha) // Default fallback
        }
    }

    fun getWeatherGradient(context: Context, weatherDescription: String): GradientDrawable {
        val colors = when (weatherDescription.lowercase()) {
            "clear", "sunny" -> intArrayOf(
                ContextCompat.getColor(context, R.color.sunny_start),
                ContextCompat.getColor(context, R.color.sunny_end)
            )
            "cloudy", "overcast" -> intArrayOf(
                ContextCompat.getColor(context, R.color.cloudy_start),
                ContextCompat.getColor(context, R.color.cloudy_end)
            )
            "rainy", "drizzle" -> intArrayOf(
                ContextCompat.getColor(context, R.color.rainy_start),
                ContextCompat.getColor(context, R.color.rainy_end)
            )
            "storm", "thunderstorm" -> intArrayOf(
                ContextCompat.getColor(context, R.color.stormy_start),
                ContextCompat.getColor(context, R.color.stormy_end)
            )
            else -> intArrayOf(
                ContextCompat.getColor(context, R.color.cloudy_start),
                ContextCompat.getColor(context, R.color.cloudy_end)
            )
        }

        return GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors).apply {
            cornerRadius = 0f
        }
    }
}