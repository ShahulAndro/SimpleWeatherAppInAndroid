package com.sha.weather.app

import android.app.Application
import com.sha.weather.app.data.network.networkModule
import com.sha.weather.app.data.repository.repositoryModule
import com.sha.weather.app.viewmodel.viewModelModule
import org.koin.core.context.startKoin

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }

}