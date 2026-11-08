
package com.skycast.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository {

    suspend fun searchCity(
        city: String
    ): Result<List<CityLocation>> =
        withContext(Dispatchers.IO) {
            runCatching {
                require(city.isNotBlank()) {
                    "City name cannot be empty"
                }

                GeocodingApi.searchCity(city)
            }
        }

    suspend fun getWeather(
        city: CityLocation
    ): Result<WeatherData> =
        withContext(Dispatchers.IO) {
            runCatching {
                WeatherApi.getWeather(
                    latitude = city.latitude,
                    longitude = city.longitude
                )
            }
        }
}
