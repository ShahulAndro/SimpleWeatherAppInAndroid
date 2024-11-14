package com.sha.weather.app.data.repository

import org.koin.dsl.module

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
val repositoryModule = module {
    single { WeatherRepository(get()) }
}