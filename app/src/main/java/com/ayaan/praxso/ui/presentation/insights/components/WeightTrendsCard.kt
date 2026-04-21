package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.*

@Composable
fun WeightTrendsCard() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Body & Metabolic Trends",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your weight",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                        Text(
                            text = "in kg",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    
                    // Toggle
                    Row(
                        modifier = Modifier
                            .background(ToggleInactive, RoundedCornerShape(12.dp))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(ToggleActive, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Monthly", color = Color.White, fontSize = 12.sp)
                        }
                        Box(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Weekly", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                    WeightChart()
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val months = listOf("Jan", "Feb", "Mar", "Apr", "May")
                    months.forEach { month ->
                        Text(month, fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun WeightChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        val path = Path().apply {
            moveTo(0f, height * 0.8f)
            cubicTo(
                width * 0.15f, height * 0.75f,
                width * 0.25f, height * 0.65f,
                width * 0.35f, height * 0.7f
            )
            cubicTo(
                width * 0.45f, height * 0.8f,
                width * 0.55f, height * 0.75f,
                width * 0.65f, height * 0.3f
            )
            cubicTo(
                width * 0.75f, height * 0.4f,
                width * 0.85f, height * 0.55f,
                width, height * 0.65f
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
                colors = listOf(WeightGradientStart, WeightGradientEnd)
            )
        )
        
        drawPath(
            path = path,
            color = WeightLineColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        
        // Draw some points
        val points = listOf(
            androidx.compose.ui.geometry.Offset(width * 0.35f, height * 0.7f),
            androidx.compose.ui.geometry.Offset(width * 0.68f, height * 0.3f),
            androidx.compose.ui.geometry.Offset(width * 0.85f, height * 0.55f)
        )
        
        points.forEach { point ->
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = point
            )
            drawCircle(
                color = WeightLineColor,
                radius = 4.dp.toPx(),
                center = point,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}
