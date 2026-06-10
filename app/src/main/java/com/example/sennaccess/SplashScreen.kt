package com.example.sennaccess

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sennaccess.ui.theme.LocalAppColors
import com.example.sennaccess.ui.theme.SenaGreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(isDark: Boolean = true, onFinished: () -> Unit) {
    val animProgress = remember { Animatable(0f) }
    var phase by remember { mutableStateOf(0) }
    var showSubtitle by remember { mutableStateOf(false) }
    var flashAlpha by remember { mutableStateOf(0f) }

    val pulseTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by pulseTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    LaunchedEffect(Unit) {
        animProgress.animateTo(1f, animationSpec = tween(2800, easing = LinearEasing))
        phase = 1
        flashAlpha = 0.6f
        delay(80)
        flashAlpha = 0f
        delay(70)
        showSubtitle = true
        delay(800)
        onFinished()
    }

    val logoFade = animProgress.value
    val glowSize = (0.1f + animProgress.value * 0.9f).coerceAtMost(1f)
    val scanY = -100f + animProgress.value * 200f
    val logoRotate = animProgress.value * 1.2f - 0.6f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF07090D) else Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(240.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(240.dp * glowSize)
                        .alpha(pulseAlpha * animProgress.value * 1.2f)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    SenaGreen.copy(alpha = 0.6f),
                                    SenaGreen.copy(alpha = 0.15f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .alpha(if (phase == 1) flashAlpha else 0f)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.8f),
                                    Color.White.copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_sena),
                    contentDescription = "SENA",
                    modifier = Modifier
                        .size(180.dp)
                        .alpha(logoFade)
                        .scale(1f + animProgress.value * 0.05f)
                        .rotate(logoRotate)
                )

                if (phase == 0) {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(8.dp)
                            .offset(y = scanY.dp)
                            .alpha(logoFade * 0.35f)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SenaGreen.copy(alpha = 0.2f),
                                        SenaGreen.copy(alpha = 0.4f),
                                        SenaGreen.copy(alpha = 0.2f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(3.dp)
                            .offset(y = scanY.dp)
                            .alpha(logoFade)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        SenaGreen.copy(alpha = 0.7f),
                                        Color.White.copy(alpha = 1f),
                                        SenaGreen.copy(alpha = 0.9f),
                                        Color.White.copy(alpha = 1f),
                                        SenaGreen.copy(alpha = 0.7f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            if (showSubtitle) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "SENA ACCESS",
                    color = SenaGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }
    }
}
