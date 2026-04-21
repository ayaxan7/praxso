package com.ayaan.praxso.ui.presentation.insights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayaan.praxso.ui.presentation.insights.components.BottomNavBar
import com.ayaan.praxso.ui.presentation.insights.components.CycleTrendsCard
import com.ayaan.praxso.ui.presentation.insights.components.LifestyleImpactCard
import com.ayaan.praxso.ui.presentation.insights.components.StabilityCard
import com.ayaan.praxso.ui.presentation.insights.components.SymptomTrendsCard
import com.ayaan.praxso.ui.presentation.insights.components.WeightTrendsCard
import com.ayaan.praxso.ui.theme.BackgroundGradientEnd
import com.ayaan.praxso.ui.theme.BackgroundGradientStart
import com.ayaan.praxso.ui.theme.StabilityLineColor
import com.ayaan.praxso.ui.theme.TextPrimary

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
        }, navigationIcon = {
            IconButton(onClick = {}) {
                // Custom Grid Icon
                GridIcon()
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
        )
    }, bottomBar = {
        BottomNavBar()
    }, containerColor = Color.Transparent // We'll handle background in a Box
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
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        StabilityLineColor.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        StabilityLineColor.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        StabilityLineColor.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        StabilityLineColor.copy(alpha = 0.5f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}
