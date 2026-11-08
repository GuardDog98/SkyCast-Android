
package com.skycast.app

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class WeatherData(
    val temperature: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weatherCode: Int
)

object WeatherApi {

    fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherData {

        val url = URL(
            "https://api.open-meteo.com/v1/forecast" +
            "?latitude=$latitude" +
            "&longitude=$longitude" +
            "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code"
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

            val json = JSONObject(response)
            val current = json.getJSONObject("current")

            return WeatherData(
                temperature = current.getDouble("temperature_2m"),
                humidity = current.getInt("relative_humidity_2m"),
                windSpeed = current.getDouble("wind_speed_10m"),
                weatherCode = current.getInt("weather_code")
            )

        } finally {
            connection.disconnect()
        }
    }
}
