
package com.skycast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyCastScreen()
        }
    }
}

@Composable
fun SkyCastScreen() {
    val blue = Color(0xFF2196F3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "SkyCast",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Your weather companion",
                fontSize = 14.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Baku, Azerbaijan",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "☀️",
                fontSize = 90.sp
            )

            Text(
                text = "24°",
                fontSize = 75.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )

            Text(
                text = "Sunny",
                fontSize = 23.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(35.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherInfo("💧", "Humidity", "45%")
                    WeatherInfo("💨", "Wind", "12 km/h")
                    WeatherInfo("🌡️", "Feels like", "25°")
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "7-Day Forecast",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ForecastRow("Monday", "☀️", "24°")
                    ForecastRow("Tuesday", "⛅", "22°")
                    ForecastRow("Wednesday", "🌧️", "19°")
                    ForecastRow("Thursday", "☀️", "25°")
                    ForecastRow("Friday", "⛅", "23°")
                }
            }
        }
    }
}

@Composable
fun WeatherInfo(icon: String, title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 25.sp)
        Text(title, fontSize = 11.sp, color = Color.Gray)
        Text(
            value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ForecastRow(day: String, icon: String, temperature: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(day, color = Color.DarkGray, fontSize = 15.sp)
        Text(icon, fontSize = 20.sp)
        Text(
            temperature,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
    }
}
