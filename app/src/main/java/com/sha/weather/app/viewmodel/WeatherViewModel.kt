package com.sha.weather.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sha.weather.app.data.model.WeatherData
import com.sha.weather.app.data.repository.WeatherRepository
import kotlinx.coroutines.launch

/**
 * Created by Shahul Hameed on 13/11/2024.
 */

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData
    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                _weatherData.postValue(response)
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }

    val weatherDataByLocation = repository.weatherData
    val error = repository.error
    fun fetchWeatherByLocation(latitude: Double, longitude: Double, apiKey: String) {
        repository.getWeatherByLocation(latitude, longitude, apiKey)
    }
}
