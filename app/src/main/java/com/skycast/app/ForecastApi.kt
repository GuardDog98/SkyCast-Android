
package com.skycast.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class DailyForecast(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val weatherCode: Int,
    val precipitationProbability: Int,
    val windSpeed: Double
)

object ForecastApi {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): List<DailyForecast> = withContext(Dispatchers.IO) {

        val url = URL(
            "https://api.open-meteo.com/v1/forecast" +
            "?latitude=$latitude" +
            "&longitude=$longitude" +
            "&daily=weather_code,temperature_2m_max," +
            "temperature_2m_min,precipitation_probability_max," +
            "wind_speed_10m_max" +
            "&forecast_days=7" +
            "&timezone=auto"
        )

        val connection =
            url.openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val response = connection.inputStream
                .bufferedReader()
                .use { it.readText() }

            val daily = JSONObject(response)
                .getJSONObject("daily")

            val dates = daily.getJSONArray("time")
            val maxTemps = daily.getJSONArray("temperature_2m_max")
            val minTemps = daily.getJSONArray("temperature_2m_min")
            val codes = daily.getJSONArray("weather_code")
            val rain = daily.getJSONArray("precipitation_probability_max")
            val wind = daily.getJSONArray("wind_speed_10m_max")

            List(dates.length()) { index ->
                DailyForecast(
                    date = dates.getString(index),
                    maxTemperature = maxTemps.getDouble(index),
                    minTemperature = minTemps.getDouble(index),
                    weatherCode = codes.getInt(index),
                    precipitationProbability = rain.optInt(index),
                    windSpeed = wind.getDouble(index)
                )
            }

        } finally {
            connection.disconnect()
        }
    }
}
