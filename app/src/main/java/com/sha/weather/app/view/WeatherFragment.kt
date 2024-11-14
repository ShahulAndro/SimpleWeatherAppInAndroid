package com.sha.weather.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sha.weather.app.R
import com.sha.weather.app.data.model.WeatherData
import com.sha.weather.app.data.shared.Utils
import com.sha.weather.app.data.shared.WeatherUtils
import com.sha.weather.app.databinding.FragmentWeatherBinding
import com.sha.weather.app.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.sha.weather.app.data.shared.SharedPrefUtils


/**
 * Created by Shahul Hameed on 13/11/2024.
 */

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.viewModel = weatherViewModel
        binding.lifecycleOwner = this

        bindViewModelData()

        setSearchClick()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")

        if (latitude != null && longitude != null) {
            weatherViewModel.fetchWeatherByLocation(latitude, longitude, getString(R.string.api_key))
        } else {
            val city = SharedPrefUtils.getCity(requireContext())
            if (!city.isNullOrEmpty()) {
                weatherViewModel.fetchWeather(city, getString(R.string.api_key))
            }
        }
    }

    private fun setSearchClick() {
        binding.buttonSearch.setOnClickListener {
            val city = binding.cityInput.text.toString()
            if (city.isNotEmpty()) {
                weatherViewModel.fetchWeather(city, getString(R.string.api_key))
            }
        }
    }

    private fun bindViewModelData() {
        // Observe the weather  by City
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            displayWeather(weatherData)
        }

        // Observe the weather  by Location
        weatherViewModel.weatherDataByLocation.observe(viewLifecycleOwner) { weatherData ->
            displayWeather(weatherData)
        }

        // Observe error messages
        weatherViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayWeather(weatherData: WeatherData) {
        binding.cityInput.setText("")
        binding.locationTextView.text = weatherData.name
        binding.dateTextView.text = Utils.convertEpochToDate(weatherData.dt)
        loadWeatherImage(Utils.getWeatherIconUrl(weatherData.weather.first().icon))
        "${Math.round(weatherData.main.temp).toInt()}°C".also { binding.currentTempTextView.text = it }
        binding.weatherDescriptionTextView.text = weatherData.weather.first().description
        "H:${Math.round(weatherData.main.temp_max).toInt()}°  ".also { binding.tempMaxTextView.text = it }
        "  L:${Math.round(weatherData.main.temp_min).toInt()}°  ".also { binding.tempMinTextView.text = it }


        // Apply gradient background based on weather description
        val weatherGradient = WeatherUtils.getWeatherGradient(
            requireContext(),
            weatherData.weather[0].main
        )

        binding.weatherRootLayout.background = weatherGradient

        if (weatherData.name.isNotEmpty()) {
            SharedPrefUtils.saveCity(requireContext(), weatherData.name)
        }
    }


    private fun loadWeatherImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions().placeholder(android.R.drawable.btn_star)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
            )
            .into(binding.weatherIconImageView)
    }
}