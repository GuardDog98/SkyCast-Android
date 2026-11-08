
package com.skycast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyCastApp()
        }
    }
}

@Composable
fun SkyCastApp() {
    var city by remember { mutableStateOf("Baku") }

    val background = Color(0xFF101827)
    val card = Color(0xFF1D2B40)
    val accent = Color(0xFF64B5F6)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "🌦️ SkyCast",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Your weather, beautifully presented",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(35.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Search city") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = card
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = city,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "☀️",
                        fontSize = 64.sp
                    )

                    Text(
                        text = "--°C",
                        color = Color.White,
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Weather data loading soon",
                        color = accent,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Weather forecast",
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "7-day forecast will appear here.",
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Powered by Open-Meteo API",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
