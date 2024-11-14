package com.sha.weather.mock

import com.sha.weather.app.data.model.Clouds
import com.sha.weather.app.data.model.Coord
import com.sha.weather.app.data.model.Main
import com.sha.weather.app.data.model.Sys
import com.sha.weather.app.data.model.Weather
import com.sha.weather.app.data.model.WeatherData
import com.sha.weather.app.data.model.Wind

/**
 * Created by Shahul Hameed on 14/11/2024.
 */

val mockCoord = Coord(
    lon = -0.1278,
    lat = 51.5074
)

val mockWeather = listOf(
    Weather(
        id = 800,
        main = "Clear",
        description = "clear sky",
        icon = "01d"
    )
)

val mockMain = Main(
    temp = 285.32,
    feels_like = 283.45,
    temp_min = 282.15,
    temp_max = 287.65,
    pressure = 1012,
    humidity = 81,
    sea_level = 1012,
    grnd_level = 1010
)

val mockWind = Wind(
    speed = 4.1,
    deg = 230
)

val mockClouds = Clouds(
    all = 0
)

val mockSys = Sys(
    type = 1,
    id = 12345,
    country = "GB",
    sunrise = 1618393845,
    sunset = 1618440345
)

// Full mock WeatherData object
val mockWeatherData = WeatherData(
    coord = mockCoord,
    weather = mockWeather,
    base = "stations",
    main = mockMain,
    visibility = 10000,
    wind = mockWind,
    clouds = mockClouds,
    dt = 1618399840,
    sys = mockSys,
    timezone = 3600,
    id = 2643743,
    name = "Dallas",
    cod = 200
)
