package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.*

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
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    // Navigation Arrows
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.CenterStart).size(32.dp)
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = IconInactive)
                    }
                    
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.CenterEnd).size(32.dp)
                    ) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = IconInactive)
                    }
                    
                    CycleBarChart(modifier = Modifier.padding(horizontal = 40.dp).fillMaxSize())
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
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
        val labels = listOf("28", "30", "28", "32", "29", "28")
        
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
