package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloTextDark

@Composable
fun CreativeMediaView(
    mediaKey: String,
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1.15f,
    overlayLabel: String? = null
) {
    val drawableRes = when (mediaKey) {
        "img_beach_escape", "post_summer_escape" -> R.drawable.img_beach_escape
        "img_rockstar_event", "post_rockstar" -> R.drawable.img_rockstar_event
        "img_event_night", "post_night_out" -> R.drawable.img_event_night
        "post_tokyo_stairs", "img_auth_landscape" -> R.drawable.img_auth_landscape
        "post_fire_sparks", "img_welcome_tree" -> R.drawable.img_welcome_tree
        "post_bazaar_life", "img_welcome_photographer" -> R.drawable.img_welcome_photographer
        "post_misty_woods" -> R.drawable.img_welcome_photographer
        else -> R.drawable.img_beach_escape
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .clip(RoundedCornerShape(20.dp))
            .background(FloTextDark)
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = "Story Visual",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Subtle gradient overlay for typography readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.55f)
                        ),
                        startY = 220f
                    )
                )
        )

        if (overlayLabel != null) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.Black.copy(alpha = 0.55f)
            ) {
                Text(
                    text = overlayLabel,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
