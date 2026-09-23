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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.Screen
import com.example.ui.components.CreatorAvatar
import com.example.ui.components.FloFloatingBottomBar
import com.example.ui.components.FloTopBar
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

@Composable
fun UploadSelectionScreen(
    selectedIndices: Set<Int>,
    onTogglePhoto: (Int) -> Unit,
    onProceedToDetails: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    val photoAssets = listOf(
        R.drawable.img_beach_escape,
        R.drawable.img_rockstar_event,
        R.drawable.img_event_night,
        R.drawable.img_auth_landscape,
        R.drawable.img_welcome_tree,
        R.drawable.img_welcome_photographer,
        R.drawable.img_beach_escape,
        R.drawable.img_rockstar_event,
        R.drawable.img_event_night
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
                    onMenuClick = onOpenDrawerMenu,
                    onLogoClick = { onNavigate(Screen.HOME) },
                    onExploreClick = { onNavigate(Screen.EXPLORE) },
                    onSearchClick = { onNavigate(Screen.SEARCH) },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    title = "Upload Media",
                    showBackButton = true,
                    onBackClick = { onNavigate(Screen.HOME) },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.UPLOAD_SELECTION,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("upload_selection_screen")
            ) {
                // Header description
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Camera Roll & RAW Files",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark
                        )
                        Text(
                            text = "${selectedIndices.size} selected for story",
                            fontSize = 12.sp,
                            color = FloTextMuted
                        )
                    }

                    if (selectedIndices.isNotEmpty()) {
                        Button(
                            onClick = onProceedToDetails,
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FloOliveMedium),
                            modifier = Modifier.testTag("upload_next_button")
                        ) {
                            Text(
                                text = "Next (${selectedIndices.size})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // 3x3 Photo Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(photoAssets.size) { index ->
                        val isSelected = selectedIndices.contains(index)
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) FloOliveDark else FloCardBorder,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { onTogglePhoto(index) }
                                .testTag("photo_tile_$index")
                        ) {
                            Image(
                                painter = painterResource(id = photoAssets[index]),
                                contentDescription = "Camera Roll Item $index",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(FloOliveDark.copy(alpha = 0.25f))
                                )
                            }

                            // Selection badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) FloOliveDark else Color.Black.copy(alpha = 0.4f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
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

@Composable
fun ProjectDetailsScreen(
    title: String,
    caption: String,
    place: String,
    gear: String,
    category: String,
    onTitleChange: (String) -> Unit,
    onCaptionChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onGearChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onPublish: () -> Unit,
    onBack: () -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .testTag("project_details_screen")
        ) {
            // Top action bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = FloOliveDark
                    )
                }

                Text(
                    text = "Story Details",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = FloOliveDark
                )

                Button(
                    onClick = onPublish,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FloOliveDark),
                    modifier = Modifier.testTag("publish_post_button")
                ) {
                    Text("Publish", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Form inputs
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Story Title",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = onTitleChange,
                        placeholder = { Text("e.g. Summer Sunset at Amalfi") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("details_title_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloOliveMedium,
                            unfocusedBorderColor = FloCardBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Artist Statement / Description",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = caption,
                        onValueChange = onCaptionChange,
                        placeholder = { Text("Describe lighting, narrative, color palette...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("details_caption_input"),
                        minLines = 3,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloOliveMedium,
                            unfocusedBorderColor = FloCardBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Location / Set",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = place,
                        onValueChange = onPlaceChange,
                        placeholder = { Text("e.g. Tokyo, Shibuya Crossing") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("details_place_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloOliveMedium,
                            unfocusedBorderColor = FloCardBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Camera, Lens & Film Kit",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = gear,
                        onValueChange = onGearChange,
                        placeholder = { Text("e.g. Sony A7IV • 50mm f/1.2 GM") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("details_gear_input"),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FloOliveMedium,
                            unfocusedBorderColor = FloCardBorder
                        )
                    )
                }
            }
        }
    }
}
