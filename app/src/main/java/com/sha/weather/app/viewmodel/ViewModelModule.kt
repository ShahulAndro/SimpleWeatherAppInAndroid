package com.sha.weather.app.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
}