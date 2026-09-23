package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nightlife
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.Screen
import com.example.ui.components.CreatorAvatar
import com.example.ui.components.FloFloatingBottomBar
import com.example.ui.components.FloTopBar
import com.example.ui.theme.FloAmberDark
import com.example.ui.theme.FloAmberGold
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloLimeHighlight
import com.example.ui.theme.FloNavBg
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloOliveSoft
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

/**
 * Screen 4 from reference: "Every Event Deserves Its Own Story!"
 * Features stacked angled fan cards, type selection pills, and olive gradient "Create Collection" button.
 */
@Composable
fun CreateEventStoryScreen(
    onNavigate: (Screen) -> Unit,
    onClose: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("Party Or Event") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FloBgCream, FloBgSage, FloBgPistachio)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .testTag("create_event_story_screen"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Close 'X' button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .border(1.dp, FloCardBorder, CircleShape)
                        .testTag("close_event_story_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = FloOliveDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Overlapping / Fanned Stacked Cards from reference image!
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentAlignment = Alignment.Center
            ) {
                // Left card (tilted -9 degrees)
                Card(
                    modifier = Modifier
                        .width(155.dp)
                        .height(180.dp)
                        .offset(x = (-60).dp, y = 14.dp)
                        .rotate(-9f),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = FloAmberGold),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_rockstar_event),
                            contentDescription = "Tony Wick",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                    )
                                )
                        )
                        Text(
                            text = "Tony Wick's\nBirthday E.",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        )
                    }
                }

                // Right card (tilted 9 degrees)
                Card(
                    modifier = Modifier
                        .width(155.dp)
                        .height(180.dp)
                        .offset(x = 60.dp, y = 14.dp)
                        .rotate(9f),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_event_night),
                            contentDescription = "52 Street",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                    )
                                )
                        )
                        Text(
                            text = "52 Street\nNight Out",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        )
                    }
                }

                // Center Card (elevated and prominent)
                Card(
                    modifier = Modifier
                        .width(165.dp)
                        .height(195.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_beach_escape),
                            contentDescription = "Center Card",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Add An",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Event Place",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Headline from reference: "Every Event Deserves Its Own Story!"
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Text(
                        text = "Every ",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloTextDark,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Event",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveMedium,
                        letterSpacing = (-0.5).sp
                    )
                }
                Text(
                    text = "Deserves Its Own Story!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloTextDark,
                    letterSpacing = (-0.5).sp,
                    lineHeight = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4 Option Selection Pills with Icons
            val options = listOf(
                Pair("Upcoming Trip", Icons.Default.Flight),
                Pair("Anniversary", Icons.Default.Cake),
                Pair("Party Or Event", Icons.Default.Nightlife),
                Pair("Create Manually", Icons.Default.Edit)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                options.forEach { (title, icon) ->
                    val isSelected = selectedOption == title
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = title }
                            .testTag("option_$title"),
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) FloOliveMedium else FloCardBorder
                        ),
                        shadowElevation = if (isSelected) 3.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) FloBgPistachio else FloBgSage),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = FloOliveDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = title,
                                fontSize = 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = FloTextDark
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Rich Olive Gradient Action Button: "Create Collection"
            Button(
                onClick = { onNavigate(Screen.TASK_SCHEDULE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("create_collection_button"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(FloOliveMedium, FloOliveDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Create Collection",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Screen 7, 8, 9 from reference: "Creating A Collection" / "Task Schedule"
 * Features duration pills, calendar days strip, Event Task Schedule cards, and highlighted description pills.
 */
@Composable
fun TaskScheduleScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    var selectedDuration by remember { mutableStateOf("One Day") }
    var selectedDayIndex by remember { mutableIntStateOf(2) } // Day 22 active

    val durationTabs = listOf("One Day", "Multi-Day", "Repeating")
    val days = listOf(
        Pair("MON", "20"),
        Pair("TUE", "21"),
        Pair("WED", "22"),
        Pair("THU", "23"),
        Pair("FRI", "24"),
        Pair("SAT", "25"),
        Pair("SUN", "26")
    )

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
                    onMenuClick = { },
                    onLogoClick = { onNavigate(Screen.HOME) },
                    onExploreClick = { onNavigate(Screen.EXPLORE) },
                    onSearchClick = { onNavigate(Screen.SEARCH) },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    title = "A Collection",
                    showBackButton = true,
                    onBackClick = onBack,
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.TASK_SCHEDULE,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("task_schedule_screen"),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // 1. Duration Tabs: [One Day] [Multi-Day] [Repeating]
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        durationTabs.forEach { tab ->
                            val isSelected = selectedDuration == tab
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) Color.White else Color.Transparent,
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder) else null,
                                modifier = Modifier.clickable { selectedDuration = tab }
                            ) {
                                Text(
                                    text = tab,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) FloOliveDark else FloTextMuted,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // 2. Calendar Header & Days Row from reference
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Saturday, 16",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = FloTextDark
                                    )
                                    Text(
                                        text = "2025",
                                        fontSize = 12.sp,
                                        color = FloTextMuted
                                    )
                                }

                                Text(
                                    text = "JULY",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Black,
                                    color = FloOliveDark,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Days Row: MON 20, TUE 21, WED 22 (active dark olive circle), etc.
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                days.forEachIndexed { index, (dayName, dayNum) ->
                                    val isActive = index == selectedDayIndex
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .clickable { selectedDayIndex = index }
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = dayName,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (isActive) FloOliveMedium else FloTextMuted
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(if (isActive) FloOliveDark else Color.Transparent),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = dayNum,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isActive) Color.White else FloTextDark
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. "Event Task Schedule" Header with Tune / Filter icon
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Event\nTask Schedule",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark,
                            lineHeight = 20.sp
                        )

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(1.dp, FloCardBorder, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter Schedule",
                                tint = FloOliveDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // 4. Task Schedule Cards matching reference
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TaskItemCard(
                            date = "22 Mar",
                            avatarKey = "avatar_ridhwan",
                            title = "Tony wick's Birthday",
                            subtitle = "Pickup Tony from hou..."
                        )

                        TaskItemCard(
                            date = "22 Mar",
                            avatarKey = "avatar_alino",
                            title = "Prepare moodboard",
                            subtitle = "Review client feedback"
                        )

                        TaskItemCard(
                            date = "22 Mar",
                            avatarKey = "avatar_ekeya",
                            title = "Confirm guest artist",
                            subtitle = "Record promocliips for..."
                        )
                    }
                }

                // 5. "Event Description" Card with highlighted tags from reference
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Event Description",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = FloOliveDark
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Highlighted pills text matching reference:
                            // "Golden hour gathering [Salted air], [soft beats] and endless smiles. The beach was alive with us."
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Golden hour gathering ",
                                    fontSize = 13.sp,
                                    color = FloTextMuted
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = FloLimeHighlight
                                ) {
                                    Text(
                                        text = "Salted air",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = ",",
                                    fontSize = 13.sp,
                                    color = FloTextMuted
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = FloLimeHighlight
                                ) {
                                    Text(
                                        text = "soft beats",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = " and endless smiles. The",
                                    fontSize = 13.sp,
                                    color = FloTextMuted
                                )
                            }

                            Text(
                                text = "beach was alive with us.",
                                fontSize = 13.sp,
                                color = FloTextMuted,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                // 6. Time and Location Inputs from reference
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ScheduleInputRow(
                            icon = Icons.Default.AccessTime,
                            title = "Start Time",
                            subtitle = "One Day",
                            actionText = "Add Time"
                        )

                        ScheduleInputRow(
                            icon = Icons.Default.AccessTime,
                            title = "End Time",
                            subtitle = "One Day",
                            actionText = "Add Time"
                        )

                        ScheduleInputRow(
                            icon = Icons.Default.LocationOn,
                            title = "Location Tag",
                            subtitle = "Event Place",
                            actionText = "Add Location"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskItemCard(
    date: String,
    avatarKey: String,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date pill on left
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(FloBgSage)
                    .padding(vertical = 6.dp)
            ) {
                Text(
                    text = date.split(" ").firstOrNull() ?: "22",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloOliveDark
                )
                Text(
                    text = date.split(" ").getOrNull(1) ?: "Mar",
                    fontSize = 10.sp,
                    color = FloTextMuted
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Avatar
            CreatorAvatar(
                avatarKey = avatarKey,
                name = title,
                size = 38.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Title & Subtitle with small yellow bullet
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloTextDark
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(FloAmberGold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = FloTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleInputRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    actionText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(FloBgSage),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = FloOliveDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloTextDark
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        color = FloTextMuted
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = FloBgPistachio,
                modifier = Modifier.clickable { }
            ) {
                Text(
                    text = actionText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloOliveDark,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

/**
 * Screen 8 from reference: "flo Gallery"
 * Features masonry photo grid, offer promo, and image collections.
 */
@Composable
fun GalleryScreen(
    onNavigate: (Screen) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
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
                    title = "Gallery",
                    showBackButton = true,
                    onBackClick = { onNavigate(Screen.HOME) },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.GALLERY,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("gallery_screen"),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Top Staggered Grid Row 1
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left tall card: "Motor Show Road Photoshoot"
                        Card(
                            modifier = Modifier
                                .weight(1.1f)
                                .height(210.dp),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_welcome_photographer),
                                    contentDescription = "Motor Show",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                            )
                                        )
                                )
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Motor Show",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Road Photoshoot",
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 10.sp
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "Like",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // Right column: Vibrant neon face + Small square
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_auth_landscape),
                                    contentDescription = "Neon Portrait",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_event_night),
                                    contentDescription = "Small Square",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }

                // Staggered Grid Row 2 from reference
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left card: "160+ Spring and Winter Img Collection"
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(190.dp),
                            shape = RoundedCornerShape(22.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_beach_escape),
                                    contentDescription = "Spring & Winter",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                            )
                                        )
                                )
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(12.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color.White.copy(alpha = 0.35f)
                                    ) {
                                        Text(
                                            text = "160+",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Spring and Winter\nImg Collection",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }

                        // Right card: "Office Project Meet Up Image folder" with heart icon
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(190.dp),
                            shape = RoundedCornerShape(22.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_welcome_tree),
                                    contentDescription = "Office Project",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                                            )
                                        )
                                )
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Office Project",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Meet Up Image folder",
                                            color = Color.White.copy(alpha = 0.8f),
                                            fontSize = 10.sp
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = "Like",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
