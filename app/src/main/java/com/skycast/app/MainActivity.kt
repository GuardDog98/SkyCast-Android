
package com.skycast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                SkyCastScreen()
            }
        }
    }
}

@Composable
fun SkyCastScreen() {

    var weather by remember {
        mutableStateOf<WeatherData?>(null)
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        loading = true
        error = null

        try {
            weather = withContext(Dispatchers.IO) {
                WeatherApi.getWeather(
                    latitude = 40.4093,
                    longitude = 49.8671
                )
            }
        } catch (e: Exception) {
            error = "Unable to load weather data"
        } finally {
            loading = false
        }
    }

    val data = weather

    val condition = when (data?.weatherCode) {
        0 -> "Clear sky"
        1, 2 -> "Partly cloudy"
        3 -> "Cloudy"
        45, 48 -> "Foggy"
        51, 53, 55, 56, 57 -> "Drizzle"
        61, 63, 65, 66, 67 -> "Rainy"
        71, 73, 75, 77 -> "Snowy"
        80, 81, 82 -> "Rain showers"
        85, 86 -> "Snow showers"
        95, 96, 99 -> "Thunderstorm"
        null -> "Loading..."
        else -> "Unknown"
    }

    val icon = when (data?.weatherCode) {
        0 -> "☀️"
        1, 2 -> "⛅"
        3 -> "☁️"
        45, 48 -> "🌫️"
        51, 53, 55, 56, 57 -> "🌦️"
        61, 63, 65, 66, 67 -> "🌧️"
        71, 73, 75, 77 -> "❄️"
        80, 81, 82 -> "🌧️"
        85, 86 -> "🌨️"
        95, 96, 99 -> "⛈️"
        else -> "🌤️"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF1565C0),
                        Color(0xFF42A5F5),
                        Color(0xFFE3F2FD)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(25.dp))

            Text(
                "SkyCast",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                "Your weather companion",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(35.dp))

            Text(
                "Baku, Azerbaijan",
                fontSize = 23.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(20.dp))

            Text(icon, fontSize = 85.sp)

            if (loading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            } else if (data != null) {

                Text(
                    "${data.temperature.toInt()}°",
                    fontSize = 76.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )

                Text(
                    condition,
                    fontSize = 23.sp,
                    color = Color.White
                )

            } else {
                Text(
                    error ?: "Weather unavailable",
                    color = Color.White,
                    fontSize = 16.sp
                )

                Button(onClick = {
                    // Retry is added in the next update.
                }) {
                    Text("Weather unavailable")
                }
            }

            Spacer(Modifier.height(35.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.92f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherInfo(
                        "💧",
                        "Humidity",
                        data?.let { "${it.humidity}%" } ?: "--"
                    )

                    WeatherInfo(
                        "💨",
                        "Wind",
                        data?.let { "${it.windSpeed} km/h" } ?: "--"
                    )

                    WeatherInfo(
                        "🌡️",
                        "Temperature",
                        data?.let { "${it.temperature.toInt()}°" } ?: "--"
                    )
                }
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "Weather Information",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.92f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        "Current conditions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Temperature: ${data?.temperature ?: "--"} °C",
                        color = Color.DarkGray
                    )

                    Text(
                        "Humidity: ${data?.humidity ?: "--"}%",
                        color = Color.DarkGray
                    )

                    Text(
                        "Wind speed: ${data?.windSpeed ?: "--"} km/h",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Powered by Open-Meteo",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun WeatherInfo(
    icon: String,
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 25.sp)

        Text(
            title,
            fontSize = 11.sp,
            color = Color.Gray
        )

        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}
