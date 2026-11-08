
package com.skycast.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForecastRepository {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Result<List<DailyForecast>> =
        withContext(Dispatchers.IO) {
            runCatching {
                ForecastApi.getForecast(
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
}
