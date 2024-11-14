package com.sha.weather.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sha.weather.app.data.network.WeatherApiService
import com.sha.weather.app.data.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
open class WeatherRepository(private val apiService: WeatherApiService) {

    // LiveData to hold the weather data
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    // LiveData for error handling
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Function to get weather data by city
    suspend fun getWeather(city: String, apiKey: String): WeatherData {
        return apiService.getWeather(city, apiKey)
    }

    // Function to get weather data by location
    fun getWeatherByLocation(latitude: Double, longitude: Double, apiKey: String) {
        apiService.getWeatherByLocation(latitude, longitude, apiKey)
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        // Success: Update LiveData with the weather data
                        _weatherData.postValue(response.body())
                    } else {
                        // Error: Update LiveData with error message
                        _error.postValue("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    // Failure: Update LiveData with error message
                    _error.postValue("Network failure: ${t.message}")
                }
            })
    }
}
