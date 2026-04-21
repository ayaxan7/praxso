package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.*

@Composable
fun SymptomTrendsCard() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Body Signals",
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
                    text = "Symptom Trends",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Compared to last cycle",
                    fontSize = 14.sp,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Box(
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    SymptomDonutChart()
                    
                    // Center hole content or labels
                    // Labels are outside in the design, I'll place them using Box offsets
                    
                    SymptomLabel("30%\nMood", Alignment.TopStart, offset = Offset(-20f, 40f))
                    SymptomLabel("31%\nBloating", Alignment.TopEnd, offset = Offset(20f, 60f))
                    SymptomLabel("17%\nAcne", Alignment.BottomStart, offset = Offset(-20f, -40f))
                    SymptomLabel("21%\nFatigue", Alignment.BottomEnd, offset = Offset(20f, -20f))
                }
            }
        }
    }
}

data class Offset(val x: Float, val y: Float)

@Composable
fun SymptomLabel(text: String, alignment: Alignment, offset: Offset) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            // Using Box with alignment and padding for positioning
    ) {
        Card(
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.align(alignment).offset(offset.x.dp, offset.y.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun SymptomDonutChart() {
    Canvas(modifier = Modifier.size(200.dp)) {
        val strokeWidth = 40.dp.toPx()
        
        // Mood (Pink)
        drawArc(
            color = DonutPink,
            startAngle = 180f,
            sweepAngle = 108f, // 30%
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        
        // Bloating (Purple)
        drawArc(
            color = DonutPurple,
            startAngle = 288f,
            sweepAngle = 111.6f, // 31%
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        
        // Fatigue (Red)
        drawArc(
            color = DonutRed,
            startAngle = 39.6f,
            sweepAngle = 75.6f, // 21%
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        
        // Acne (Green)
        drawArc(
            color = DonutGreen,
            startAngle = 115.2f,
            sweepAngle = 64.8f, // 17%
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
    }
}
