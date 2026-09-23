package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.model.CreativePost
import com.example.data.model.CreativeUser
import com.example.ui.Screen
import com.example.ui.components.CreativeMediaView
import com.example.ui.components.CreatorAvatar
import com.example.ui.components.FloFloatingBottomBar
import com.example.ui.components.FloTopBar
import com.example.ui.theme.FloAmberGold
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloOliveSoft
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

@Composable
fun ProfileScreen(
    user: CreativeUser?,
    selectedProjectIndex: Int,
    unreadCount: Int,
    onSelectProjectIndex: (Int) -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenChat: (String) -> Unit,
    onToggleFollow: (CreativeUser) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    val activeUser = user ?: CreativeUser(
        handle = "@ninokaek",
        name = "Ekeya ninoka",
        role = "Street Photographer",
        location = "Istanbul",
        bio = "Exploring the busy markets and capturing unseen emotions. Street photography is passion and patience supported by observation",
        avatarUrl = "avatar_ekeya",
        isFollowing = false,
        isAvailableForHire = true,
        cameraKit = "Sony A7RV • Leica M11 • 35mm f/1.4 Summilux"
    )

    var showHireDialog by remember { mutableStateOf(false) }
    var hireShootTitle by remember { mutableStateOf("") }
    var hireBudget by remember { mutableStateOf("$800/day") }
    var hireMessage by remember { mutableStateOf("") }
    var hireSentSuccess by remember { mutableStateOf(false) }

    val projectNames = listOf("1. India Tour", "2. Tokyo Nights", "3. Raw Bosphorus", "4. Minimalism", "5. Editorial")

    val portfolioPhotos = when (selectedProjectIndex) {
        0 -> listOf(
            R.drawable.img_beach_escape,
            R.drawable.img_rockstar_event,
            R.drawable.img_event_night,
            R.drawable.img_welcome_tree
        )
        1 -> listOf(
            R.drawable.img_auth_landscape,
            R.drawable.img_welcome_photographer,
            R.drawable.img_welcome_tree
        )
        else -> listOf(
            R.drawable.img_welcome_photographer,
            R.drawable.img_auth_landscape,
            R.drawable.img_welcome_tree
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FloBgCream, FloBgSage, FloBgPistachio)
                )
            )
    ) {
        Scaffold(
            topBar = {
                FloTopBar(
                    onMenuClick = onOpenDrawerMenu,
                    onLogoClick = { onNavigate(Screen.HOME) },
                    onExploreClick = { onNavigate(Screen.EXPLORE) },
                    onSearchClick = { onNavigate(Screen.SEARCH) },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    unreadNotifications = unreadCount,
                    title = "Profile",
                    showBackButton = true,
                    onBackClick = { onNavigate(Screen.HOME) },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.PROFILE,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("profile_screen"),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // Profile Top Header
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = activeUser.role,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FloOliveDark
                                )
                                Text(
                                    text = activeUser.location,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = FloTextMuted
                                )
                            }

                            // "Hire" pill button
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = FloOliveMedium,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { showHireDialog = true }
                                    .testTag("profile_hire_button")
                            ) {
                                Text(
                                    text = "Hire",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Large circular avatar in center
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CreatorAvatar(
                                avatarKey = activeUser.avatarUrl,
                                name = activeUser.name,
                                size = 88.dp,
                                hasStoryBorder = true
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = activeUser.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = FloOliveDark
                            )

                            Text(
                                text = activeUser.handle,
                                fontSize = 13.sp,
                                color = FloTextMuted
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Stats Row
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StatItem(count = "24", label = "Stories")
                                Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(FloCardBorder))
                                StatItem(count = "1.8k", label = "Followers")
                                Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(FloCardBorder))
                                StatItem(count = "430", label = "Collaborators")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Action buttons row: Follow / Message / Collab
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { onToggleFollow(activeUser) },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (activeUser.isFollowing) FloBgSage else FloOliveDark,
                                        contentColor = if (activeUser.isFollowing) FloOliveDark else Color.White
                                    ),
                                    modifier = Modifier.weight(1f).testTag("profile_follow_button")
                                ) {
                                    Text(
                                        text = if (activeUser.isFollowing) "Following ✓" else "Follow",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }

                                Button(
                                    onClick = { onOpenChat(activeUser.handle) },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White,
                                        contentColor = FloOliveDark
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder),
                                    modifier = Modifier.weight(1f).testTag("profile_chat_button")
                                ) {
                                    Text(
                                        text = "Chat / Collab",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Bio Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = activeUser.bio,
                                    fontSize = 13.sp,
                                    color = FloTextDark,
                                    lineHeight = 19.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = "Gear",
                                        tint = FloOliveMedium,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = activeUser.cameraKit,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = FloTextMuted
                                    )
                                }
                            }
                        }
                    }
                }

                // Project Portfolio Tabs
                item {
                    Text(
                        text = "Curated Projects & Series",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark,
                        modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 10.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(projectNames.indices.toList()) { index ->
                            val isSelected = index == selectedProjectIndex
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = if (isSelected) FloOliveDark else Color.White,
                                border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder),
                                modifier = Modifier
                                    .clickable { onSelectProjectIndex(index) }
                                    .testTag("project_tab_$index")
                            ) {
                                Text(
                                    text = projectNames[index],
                                    color = if (isSelected) Color.White else FloTextDark,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // Portfolio Photos
                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        portfolioPhotos.forEachIndexed { idx, resId ->
                            PortfolioThumbnail(resId = resId, height = if (idx % 2 == 0) 220.dp else 180.dp)
                        }
                    }
                }
            }
        }
    }

    if (showHireDialog) {
        AlertDialog(
            onDismissRequest = { showHireDialog = false },
            title = {
                Text(
                    text = "Book / Hire ${activeUser.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = FloOliveDark
                )
            },
            text = {
                Column {
                    Text(
                        text = "Direct booking proposal via flo escrow & real-time messaging:",
                        fontSize = 12.sp,
                        color = FloTextMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = hireShootTitle,
                        onValueChange = { hireShootTitle = it },
                        placeholder = { Text("Shoot Title e.g. Autumn Fashion Campaign") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = hireBudget,
                        onValueChange = { hireBudget = it },
                        placeholder = { Text("Budget (e.g. $1,200/day)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = hireMessage,
                        onValueChange = { hireMessage = it },
                        placeholder = { Text("Shoot details, dates, gear requirements...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showHireDialog = false
                        hireSentSuccess = true
                    }
                ) {
                    Text("Send Proposal", color = FloOliveMedium, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showHireDialog = false }) {
                    Text("Cancel", color = FloTextMuted)
                }
            }
        )
    }
}

@Composable
private fun StatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FloTextDark)
        Text(text = label, fontSize = 11.sp, color = FloTextMuted)
    }
}

@Composable
private fun PortfolioThumbnail(resId: Int, height: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(20.dp))
            .background(FloCardBorder)
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "Portfolio Still",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Creative Collaboration Moodboard & Production Planner Screen
 */
@Composable
fun MoodboardCollabScreen(
    onNavigate: (Screen) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    var shotlistNote by remember { mutableStateOf("1. Golden hour backlighting at Old Quarter\n2. Low angle anamorphic 2.39:1 motion shot\n3. High-speed shutter (1/1000s) water spark freeze") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FloBgCream, FloBgSage, FloBgPistachio)
                )
            )
    ) {
        Scaffold(
            topBar = {
                FloTopBar(
                    onMenuClick = onOpenDrawerMenu,
                    onLogoClick = { onNavigate(Screen.HOME) },
                    onExploreClick = { onNavigate(Screen.EXPLORE) },
                    onSearchClick = { onNavigate(Screen.SEARCH) },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    title = "Moodboard",
                    showBackButton = true,
                    onBackClick = { onNavigate(Screen.HOME) },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.HOME,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("moodboard_collab_screen"),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
            ) {
                item {
                    Text(
                        text = "Live Collaboration Moodboard",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Text(
                        text = "Shared canvas with @ridzjcob & @kimikach for Tokyo Shoot",
                        fontSize = 12.sp,
                        color = FloTextMuted,
                        modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Color Palette & Film Emulation",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = FloOliveDark
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                PaletteSwatch(color = FloOliveDark, label = "Moss Olive")
                                PaletteSwatch(color = FloOliveLight, label = "Organic Sage")
                                PaletteSwatch(color = FloAmberGold, label = "Golden Sun")
                                PaletteSwatch(color = FloBgPistachio, label = "Pistachio")
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Shared Shotlist & Call Sheet",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = FloOliveDark
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = shotlistNote,
                                onValueChange = { shotlistNote = it },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 4,
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaletteSwatch(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(color)
                .border(1.dp, FloCardBorder, RoundedCornerShape(14.dp))
        )
        Text(text = label, fontSize = 9.sp, color = FloTextMuted, modifier = Modifier.padding(top = 4.dp))
    }
}
