package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.*

@Composable
fun StabilityCard() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Stability Summary",
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
                Text(
                    text = "Based on your recent logs and symptom patterns.",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Column {
                        Text(
                            text = "Stability Score",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                        Text(
                            text = "78%",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Stability Chart
                Box(modifier = Modifier.height(150.dp).fillMaxWidth()) {
                    StabilityChart()
                    
                    // Tooltip (Simplified)
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-40).dp, y = 20.dp)
                            .background(Color.Black, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Stability\nImproving",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Jan", fontSize = 12.sp, color = TextSecondary)
                    Text("Feb", fontSize = 12.sp, color = TextSecondary)
                    Text("Mar", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("Apr", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun StabilityChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        val points = listOf(
            0.8f to 0.7f,
            0.2f to 0.6f,
            0.4f to 0.5f,
            0.6f to 0.65f,
            0.8f to 0.3f,
            1.0f to 0.2f
        )
        
        val path = Path().apply {
            moveTo(0f, height * 0.8f)
            cubicTo(
                width * 0.2f, height * 0.8f,
                width * 0.4f, height * 0.6f,
                width * 0.6f, height * 0.7f
            )
            cubicTo(
                width * 0.8f, height * 0.8f,
                width * 0.9f, height * 0.3f,
                width, height * 0.2f
            )
        }
        
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(StabilityGradientStart, StabilityGradientEnd)
            )
        )
        
        drawPath(
            path = path,
            color = StabilityLineColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        
        // Draw Y-axis labels (32d, 28d, 24d)
        // For simplicity, just drawing the lines
        val lineCount = 3
        for (i in 0 until lineCount) {
            val y = height * (0.3f + i * 0.25f)
            drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(width, y),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
        
        // Draw vertical indicator for Mar
        val marX = width * 0.65f
        drawLine(
            color = Color.LightGray.copy(alpha = 0.5f),
            start = androidx.compose.ui.geometry.Offset(marX, 0f),
            end = androidx.compose.ui.geometry.Offset(marX, height),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        
        drawCircle(
            color = BarGreen,
            radius = 6.dp.toPx(),
            center = androidx.compose.ui.geometry.Offset(marX, height * 0.35f)
        )
    }
}
