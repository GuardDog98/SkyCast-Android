
package com.skycast.app

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class CityLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

object GeocodingApi {

    fun searchCity(city: String): List<CityLocation> {
        val encodedCity = URLEncoder.encode(city, "UTF-8")

        val url = URL(
            "https://geocoding-api.open-meteo.com/v1/search" +
            "?name=$encodedCity&count=5&language=en&format=json"
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
            val results = json.optJSONArray("results")
                ?: return emptyList()

            return List(results.length()) { index ->
                val item = results.getJSONObject(index)

                CityLocation(
                    name = item.getString("name"),
                    country = item.optString("country", "Unknown"),
                    latitude = item.getDouble("latitude"),
                    longitude = item.getDouble("longitude")
                )
            }

        } finally {
            connection.disconnect()
        }
    }
}
