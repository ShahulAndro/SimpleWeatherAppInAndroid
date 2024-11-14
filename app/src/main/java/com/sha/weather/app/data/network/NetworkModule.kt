package com.sha.weather.app.data.network

import com.sha.weather.app.data.shared.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Shahul Hameed on 13/11/2024.
 */
val networkModule = module {
    single {
        // Create a logging interceptor and set its logging level to BODY for full logs
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // Use BODY to log everything (headers, body, etc.)
        }

        // Create an OkHttpClient and attach the logging interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)  // Set timeout if needed
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(WeatherApiService::class.java)
    }
}