package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.BarGreen
import com.ayaan.praxso.ui.theme.BarPurple
import com.ayaan.praxso.ui.theme.BarRed
import com.ayaan.praxso.ui.theme.CardBackground
import com.ayaan.praxso.ui.theme.IconInactive
import com.ayaan.praxso.ui.theme.TextPrimary
import com.ayaan.praxso.ui.theme.TextSecondary

@Composable
fun CycleTrendsCard() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Cycle Trends",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // Navigation Arrows
                    IconButton(
                        onClick = {}, modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(32.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = IconInactive
                        )
                    }

                    IconButton(
                        onClick = {}, modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(32.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = IconInactive
                        )
                    }

                    CycleBarChart(
                        modifier = Modifier
                            .padding(horizontal = 40.dp)
                            .fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
                    months.forEach { month ->
                        Text(month, fontSize = 10.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun CycleBarChart(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barWidth = 12.dp.toPx()
        val spacing = (width - (barWidth * 6)) / 5

        val barHeights = listOf(
            listOf(0.4f, 0.3f, 0.2f),
            listOf(0.3f, 0.5f, 0.1f),
            listOf(0.4f, 0.2f, 0.3f),
            listOf(0.3f, 0.4f, 0.2f),
            listOf(0.4f, 0.3f, 0.2f),
            listOf(0.5f, 0.2f, 0.2f)
        )

        val colors = listOf(BarPurple, BarGreen, BarRed)
        listOf("28", "30", "28", "32", "29", "28")

        for (i in 0 until 6) {
            val x = i * (barWidth + spacing)
            var currentY = height

            // Draw segments
            for (j in 2 downTo 0) {
                val segmentHeight = height * barHeights[i][j] * 0.8f
                drawRoundRect(
                    color = colors[j],
                    topLeft = Offset(x, currentY - segmentHeight),
                    size = Size(barWidth, segmentHeight),
                    cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                )
                currentY -= segmentHeight
            }

            // Add dots/icons on segments (simplified)
            // Skip for now to keep it clean
        }
    }
}
