package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.FloWordmark
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloOliveSoft
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        FloOliveDark,
                        Color(0xFF263712),
                        Color(0xFF1D2A0D)
                    )
                )
            )
            .clickable { onTimeout() }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // "remove the logo, just write flo only"
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "flo",
                    color = Color.White,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = ".",
                    color = FloOliveSoft,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "stories • memories • creative portfolios",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.2.sp
            )
        }

        Text(
            text = "tap anywhere to continue",
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
fun WelcomeScreen(
    step: Int, // 1 or 2
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    val imageRes = if (step == 1) R.drawable.img_beach_escape else R.drawable.img_welcome_tree
    val title = if (step == 1) "Connect." else "See And Show."
    val subtitle = if (step == 1)
        "Turn real-time memories and visual events into beautiful digital stories."
    else
        "Showcase high-resolution work portfolios & collaborate on productions seamlessly."

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("welcome_screen_$step")
    ) {
        // Full bleed photography
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Welcome Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark gradient overlay for bottom legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.35f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.88f)
                        ),
                        startY = 0f
                    )
                )
        )

        // Top bar with flo text only & Skip ("remove the logo, just write flo only")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloWordmark(
                color = Color.White,
                fontSize = 28.sp
            )

            Text(
                text = "Skip",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSkip() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        // Bottom content card
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Text(
                text = title,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = subtitle,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Step dots & action button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Step indicators
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (step == 1) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (step == 1) FloOliveSoft else Color.White.copy(alpha = 0.4f))
                    )
                    Box(
                        modifier = Modifier
                            .height(6.dp)
                            .width(if (step == 2) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (step == 2) FloOliveSoft else Color.White.copy(alpha = 0.4f))
                    )
                }

                // Forward button
                Button(
                    onClick = onNext,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = FloOliveMedium),
                    modifier = Modifier
                        .size(56.dp)
                        .testTag("welcome_next_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
