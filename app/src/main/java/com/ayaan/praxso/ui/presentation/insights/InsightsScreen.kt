package com.ayaan.praxso.ui.presentation.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.presentation.insights.components.*
import com.ayaan.praxso.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Insights",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        // Custom Grid Icon
                        GridIcon()
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            BottomNavBar()
        },
        containerColor = Color.Transparent // We'll handle background in a Box
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                StabilityCard()
                CycleTrendsCard()
                WeightTrendsCard()
                SymptomTrendsCard()
                LifestyleImpactCard()
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun GridIcon() {
    Column(modifier = Modifier.size(24.dp), verticalArrangement = Arrangement.Center) {
        Row {
            Box(modifier = Modifier.size(8.dp).background(StabilityLineColor.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.CircleShape))
            Spacer(modifier = Modifier.width(2.dp))
            Box(modifier = Modifier.size(8.dp).background(StabilityLineColor.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.CircleShape))
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row {
            Box(modifier = Modifier.size(8.dp).background(StabilityLineColor.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.CircleShape))
            Spacer(modifier = Modifier.width(2.dp))
            Box(modifier = Modifier.size(8.dp).background(StabilityLineColor.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.CircleShape))
        }
    }
}
