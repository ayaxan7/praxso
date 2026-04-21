package com.ayaan.praxso.ui.presentation.insights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.theme.*

@Composable
fun LifestyleImpactCard() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Lifestyle Impact",
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
                    Text(
                        text = "Correlation Strength",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    
                    Row(
                        modifier = Modifier
                            .background(ToggleInactive, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("4 months", fontSize = 12.sp, color = TextSecondary)
                        CustomIcon(Icons.Default.KeyboardArrowDown, contentDescription = null, size = 16.dp, tint = TextSecondary)
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                CorrelationRow("Sleep", 7, BarPurple)
                Spacer(modifier = Modifier.height(12.dp))
                CorrelationRow("Hydration", 3, BarRed)
                Spacer(modifier = Modifier.height(12.dp))
                CorrelationRow("Caffeine", 5, BarGreen)
                Spacer(modifier = Modifier.height(12.dp))
                CorrelationRow("Exercise", 4, BarRed.copy(alpha = 0.5f)) // Using a slightly different red/pink
            }
        }
    }
}

@Composable
fun CorrelationRow(label: String, count: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.width(70.dp)
        )
        
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(10) { index ->
                Box(
                    modifier = Modifier
                        .size(width = 24.dp, height = 16.dp)
                        .background(
                            if (index < count) color else ToggleInactive,
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun CustomIcon(icon: ImageVector, contentDescription: String?, size: Dp, tint: Color) {
   Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        tint = tint
    )
}
