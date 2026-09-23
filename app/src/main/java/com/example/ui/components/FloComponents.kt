package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.theme.FloAmberGold
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloNavActive
import com.example.ui.theme.FloNavBg
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

/**
 * Text-only wordmark: "remove the logo, just write flo only"
 * Renders stylish lowercase typography "flo." or "flo" with zero graphic logos.
 */
@Composable
fun FloWordmark(
    modifier: Modifier = Modifier,
    color: Color = FloOliveDark,
    fontSize: TextUnit = 24.sp,
    hasDot: Boolean = true
) {
    Row(
        modifier = modifier.testTag("flo_wordmark"),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "flo",
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.8).sp,
            fontFamily = FontFamily.SansSerif
        )
        if (hasDot) {
            Text(
                text = ".",
                color = FloOliveLight,
                fontSize = fontSize,
                fontWeight = FontWeight.Black
            )
        }
    }
}

/**
 * Backward compatibility wrapper: renders strictly "flo" text only.
 */
@Composable
fun FloLogo(
    modifier: Modifier = Modifier,
    color: Color = FloOliveDark,
    size: Dp = 28.dp
) {
    FloWordmark(
        modifier = modifier,
        color = color,
        fontSize = (size.value * 0.85f).sp,
        hasDot = true
    )
}

/**
 * Minimalist top header bar matching the reference design.
 * On Home: shows "flo." on left, user avatar on right.
 * On Details/Collections: shows "<", "flo Collection", and notification bell.
 */
@Composable
fun FloTopBar(
    onMenuClick: () -> Unit,
    onLogoClick: () -> Unit,
    onExploreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    unreadNotifications: Int = 3,
    modifier: Modifier = Modifier,
    title: String? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBackButton && onBackClick != null) {
                // Back button mode (like "Creating A Collection" or "flo Collection")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f))
                            .border(1.dp, FloCardBorder, CircleShape)
                            .testTag("top_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = FloOliveDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    if (title != null) {
                        Column {
                            Text(
                                text = "flo",
                                color = FloOliveLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = title,
                                color = FloOliveDark,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else {
                        FloWordmark(
                            fontSize = 22.sp,
                            modifier = Modifier.clickable { onLogoClick() }
                        )
                    }
                }
            } else {
                // Home mode: menu / "flo." wordmark
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onMenuClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.7f))
                            .border(1.dp, FloCardBorder, CircleShape)
                            .testTag("top_menu_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = FloOliveDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onLogoClick() }
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        FloWordmark(fontSize = 24.sp)
                    }
                }
            }

            // Right action icons matching reference
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                        .border(1.dp, FloCardBorder, CircleShape)
                        .testTag("top_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = FloOliveDark,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                        .border(1.dp, FloCardBorder, CircleShape)
                        .testTag("top_notifications_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (unreadNotifications > 0) {
                                Badge(
                                    containerColor = FloOliveMedium,
                                    contentColor = Color.White
                                ) {
                                    Text(
                                        text = "$unreadNotifications",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = FloOliveDark,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Profile Avatar Thumbnail on Top Right (like in reference center mockup!)
                CreatorAvatar(
                    avatarKey = "avatar_ridhwan",
                    name = "Ridhwan Nordin",
                    size = 36.dp,
                    onClick = { onExploreClick() }
                )
            }
        }
    }
}

/**
 * Floating Pill Bottom Navigation Bar matching the exact design in the user's reference mockup:
 * Soft pill with [Grid] [Home (dark olive circle)] [Profile] [Plus/Add]
 */
@Composable
fun FloFloatingBottomBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 36.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(36.dp),
                    ambientColor = FloOliveDark.copy(alpha = 0.12f),
                    spotColor = FloOliveDark.copy(alpha = 0.20f)
                ),
            shape = RoundedCornerShape(36.dp),
            color = FloNavBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Grid/Gallery Item
                FloatingPillIcon(
                    icon = Icons.Default.GridView,
                    contentDescription = "Gallery",
                    isSelected = currentScreen == Screen.GALLERY || currentScreen == Screen.EXPLORE,
                    onClick = { onNavigate(Screen.GALLERY) },
                    testTag = "nav_grid_button"
                )

                // 2. Home Item (Active in reference: solid dark olive circle with white icon)
                FloatingPillIcon(
                    icon = Icons.Default.Home,
                    contentDescription = "Home",
                    isSelected = currentScreen == Screen.HOME,
                    onClick = { onNavigate(Screen.HOME) },
                    testTag = "nav_home_button"
                )

                // 3. Profile Item
                FloatingPillIcon(
                    icon = Icons.Default.PersonOutline,
                    contentDescription = "Profile",
                    isSelected = currentScreen == Screen.PROFILE,
                    onClick = { onNavigate(Screen.PROFILE) },
                    testTag = "nav_profile_button"
                )

                // 4. Plus / Add Item (leads to Event Collection / Story / Upload)
                FloatingPillIcon(
                    icon = Icons.Default.Add,
                    contentDescription = "Create",
                    isSelected = currentScreen == Screen.CREATE_EVENT_STORY ||
                            currentScreen == Screen.TASK_SCHEDULE ||
                            currentScreen == Screen.UPLOAD_SELECTION,
                    onClick = { onNavigate(Screen.CREATE_EVENT_STORY) },
                    testTag = "nav_upload_button"
                )
            }
        }
    }
}

@Composable
private fun FloatingPillIcon(
    icon: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    if (isSelected) {
        // Active state in reference: solid dark olive circular button with white icon
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(FloNavActive)
                .clickable { onClick() }
                .testTag(testTag),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    } else {
        // Inactive icon
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .clickable { onClick() }
                .testTag(testTag),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = FloOliveDark.copy(alpha = 0.75f),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

/**
 * Organic palette avatar with initials.
 */
@Composable
fun CreatorAvatar(
    avatarKey: String,
    name: String,
    size: Dp = 44.dp,
    modifier: Modifier = Modifier,
    hasStoryBorder: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val brush = when (avatarKey) {
        "avatar_ridhwan" -> Brush.linearGradient(listOf(FloOliveMedium, FloOliveDark))
        "avatar_ekeya" -> Brush.linearGradient(listOf(FloAmberGold, Color(0xFFE07A1F)))
        "avatar_chizhua" -> Brush.linearGradient(listOf(Color(0xFF7E8A5E), FloOliveDark))
        "avatar_alino" -> Brush.linearGradient(listOf(FloOliveLight, FloOliveMedium))
        "avatar_sonia" -> Brush.linearGradient(listOf(FloAmberGold, FloOliveDark))
        "avatar_jasleen" -> Brush.linearGradient(listOf(FloOliveLight, FloOliveDark))
        else -> Brush.linearGradient(listOf(FloOliveMedium, FloOliveDark))
    }

    val initials = name.split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")
        .ifEmpty { "FL" }

    Box(
        modifier = modifier
            .size(size)
            .then(
                if (hasStoryBorder) {
                    Modifier.border(2.dp, FloOliveMedium, CircleShape)
                } else Modifier
            )
            .padding(if (hasStoryBorder) 2.dp else 0.dp)
            .clip(CircleShape)
            .background(brush)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = (size.value * 0.38f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}
