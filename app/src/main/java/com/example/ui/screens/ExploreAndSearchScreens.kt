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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.model.CollabGig
import com.example.data.model.CreativeUser
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
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveLight
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

/**
 * Explore / Collection Screen ("flo Collection") matching the reference image mockup 6!
 */
@Composable
fun ExploreScreen(
    creators: List<CreativeUser>,
    gigs: List<CollabGig>,
    unreadCount: Int,
    onNavigate: (Screen) -> Unit,
    onOpenProfile: (CreativeUser?) -> Unit,
    onApplyGig: (CollabGig) -> Unit,
    onToggleFollow: (CreativeUser) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Party or Event", "Upcoming Trip", "Art & Photo")

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
                    onExploreClick = { /* Already here */ },
                    onSearchClick = { onNavigate(Screen.SEARCH) },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    unreadNotifications = unreadCount,
                    title = "Collection",
                    showBackButton = true,
                    onBackClick = { onNavigate(Screen.HOME) },
                    modifier = Modifier.statusBarsPadding()
                )
            },
            bottomBar = {
                FloFloatingBottomBar(
                    currentScreen = Screen.EXPLORE,
                    onNavigate = onNavigate
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("explore_screen"),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // Category Filter Pills: [All] [Party or Event] [Upcoming Trip] [Art & Photo]
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            val isSelected = category == selectedCategory
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) Color.White else Color.Transparent,
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder) else null,
                                modifier = Modifier
                                    .clickable { selectedCategory = category }
                                    .testTag("filter_$category")
                            ) {
                                Text(
                                    text = category,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) FloOliveDark else FloTextMuted,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // 1. Featured Golden Amber Card from reference ("Rockstar Perform Event")
                item {
                    RockstarEventCard(
                        onCardClick = { onNavigate(Screen.TASK_SCHEDULE) }
                    )
                }

                // 2. Second Card from reference ("Favourite ↗" Portrait card)
                item {
                    SecondFeaturedCard(
                        onCardClick = { onNavigate(Screen.CREATE_EVENT_STORY) }
                    )
                }

                // 3. Creative Production Gigs & Roles
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Production Gigs & Crews",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark
                        )
                        Text(
                            text = "${gigs.size} Open",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FloOliveMedium
                        )
                    }
                }

                items(gigs, key = { it.id }) { gig ->
                    GigCard(
                        gig = gig,
                        onApply = { onApplyGig(gig) }
                    )
                }
            }
        }
    }
}

/**
 * Big Golden Amber Hero Card from reference mockup 6:
 * - "Favourite ↗" pill tag in top left
 * - Arrow button in top right
 * - Performer in sunglasses silhouette
 * - "Rockstar Perform Event"
 * - "27th Booking a music Show that you love"
 */
@Composable
private fun RockstarEventCard(
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .testTag("rockstar_event_card"),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = FloAmberGold),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.05f)
        ) {
            // Performer visual
            Image(
                painter = painterResource(id = R.drawable.img_rockstar_event),
                contentDescription = "Rockstar Event",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Warm golden amber gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                FloAmberGold.copy(alpha = 0.35f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.75f)
                            ),
                            startY = 120f
                        )
                    )
            )

            // Top Row: "Favourite ↗" tag and circular arrow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White.copy(alpha = 0.35f)
                ) {
                    Text(
                        text = "Favourite  ↗",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NorthEast,
                        contentDescription = "Open",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom text
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Rockstar\nPerform Event",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "27th Booking a music Show\nthat you love",
                    color = Color.White.copy(alpha = 0.88f),
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

/**
 * Second Card below matching reference:
 * - "Favourite ↗"
 * - Portrait photo
 */
@Composable
private fun SecondFeaturedCard(
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .testTag("second_featured_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_event_night),
                contentDescription = "Event Night",
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

            // Top row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White.copy(alpha = 0.35f)
                ) {
                    Text(
                        text = "Favourite  ↗",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.NorthEast,
                        contentDescription = "Open",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = "52 Street Night Out",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cinematic low-light street series & portraits",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun GigCard(
    gig: CollabGig,
    onApply: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
            .testTag("gig_card_${gig.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = FloBgPistachio
                ) {
                    Text(
                        text = gig.compensation,
                        color = FloOliveDark,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "${gig.applicantsCount} applied",
                    fontSize = 11.sp,
                    color = FloTextMuted
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = gig.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = FloTextDark
            )

            Text(
                text = gig.description,
                fontSize = 13.sp,
                color = FloTextMuted,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = FloOliveMedium,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = gig.location,
                        fontSize = 12.sp,
                        color = FloTextDark
                    )
                }

                Button(
                    onClick = onApply,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (gig.isApplied) FloBgSage else FloOliveMedium,
                        contentColor = if (gig.isApplied) FloOliveDark else Color.White
                    ),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = if (gig.isApplied) "Applied ✓" else "Apply Here",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Search Screen with category filter pills and live query results.
 */
@Composable
fun SearchScreen(
    searchQuery: String,
    searchFilter: String,
    creators: List<CreativeUser>,
    gigs: List<CollabGig>,
    unreadCount: Int,
    onQueryChange: (String) -> Unit,
    onFilterChange: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenProfile: (CreativeUser?) -> Unit,
    onApplyGig: (CollabGig) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    val filterTabs = listOf("Nearby", "People", "Place", "Post", "Gear")

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
                    onSearchClick = { /* already here */ },
                    onNotificationsClick = { onNavigate(Screen.NOTIFICATIONS) },
                    unreadNotifications = unreadCount,
                    title = "Search",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("search_screen")
            ) {
                // Search Input Box
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = { Text("Search creators, cities, lenses, film...", color = FloTextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = FloOliveDark
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = FloTextMuted)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .testTag("search_input_field"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = FloOliveMedium,
                        unfocusedBorderColor = FloCardBorder
                    ),
                    singleLine = true
                )

                // Category filter chips
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filterTabs) { tab ->
                        val isSelected = tab == searchFilter
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = if (isSelected) FloOliveDark else Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder),
                            modifier = Modifier.clickable { onFilterChange(tab) }
                        ) {
                            Text(
                                text = tab,
                                color = if (isSelected) Color.White else FloTextDark,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }

                // Results list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val filteredCreators = if (searchQuery.isBlank()) {
                        creators
                    } else {
                        creators.filter {
                            it.name.contains(searchQuery, ignoreCase = true) ||
                                    it.handle.contains(searchQuery, ignoreCase = true) ||
                                    it.role.contains(searchQuery, ignoreCase = true) ||
                                    it.location.contains(searchQuery, ignoreCase = true)
                        }
                    }

                    item {
                        Text(
                            text = "Creators & Collaborators (${filteredCreators.size})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    items(filteredCreators, key = { it.handle }) { creator ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenProfile(creator) },
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CreatorAvatar(
                                        avatarKey = creator.avatarUrl,
                                        name = creator.name,
                                        size = 44.dp
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = creator.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = FloTextDark
                                        )
                                        Text(
                                            text = "${creator.role} • ${creator.location}",
                                            fontSize = 11.sp,
                                            color = FloTextMuted
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Open",
                                    tint = FloOliveMedium,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
