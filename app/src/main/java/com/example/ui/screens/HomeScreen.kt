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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

@Composable
fun HomeScreen(
    posts: List<CreativePost>,
    creators: List<CreativeUser>,
    unreadCount: Int,
    onNavigate: (Screen) -> Unit,
    onOpenProfile: (CreativeUser?) -> Unit,
    onOpenChat: (String) -> Unit,
    onLikePost: (CreativePost) -> Unit,
    onSavePost: (CreativePost) -> Unit,
    onToggleFollow: (CreativeUser) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    var activeCommentingPost by remember { mutableStateOf<CreativePost?>(null) }
    var commentText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        FloBgCream,
                        FloBgSage,
                        FloBgPistachio
                    )
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
                    .testTag("home_feed_list"),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // 1. "Good Morning" Display Greeting from reference image center mockup
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Good",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveLight,
                            letterSpacing = (-1).sp,
                            lineHeight = 44.sp
                        )
                        Text(
                            text = "Morning",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Black,
                            color = FloOliveDark,
                            letterSpacing = (-1.5).sp,
                            lineHeight = 44.sp
                        )
                    }
                }

                // 2. Large Arched Hero Card matching reference ("Nacpan Beach - Drift into Wave A Summer Escape")
                item {
                    ArchedHeroCard(
                        onCardClick = { onNavigate(Screen.EXPLORE) }
                    )
                }

                // 3. Quick Offer & Memory Collection Banners from reference image
                item {
                    QuickCollectionsRow(
                        onNavigateToGallery = { onNavigate(Screen.GALLERY) },
                        onNavigateToSchedule = { onNavigate(Screen.TASK_SCHEDULE) }
                    )
                }

                // 4. Live Creator Stories Spotlight
                item {
                    StoriesBar(
                        creators = creators,
                        onCreatorClick = { creator -> onOpenProfile(creator) }
                    )
                }

                // 5. Section Header for Feed
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Stories & Portfolios",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark
                        )
                        Text(
                            text = "View All",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FloOliveMedium,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onNavigate(Screen.EXPLORE) }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // 6. Feed posts
                items(posts, key = { it.id }) { post ->
                    val associatedCreator = creators.firstOrNull { it.handle == post.creatorHandle }
                    FeedPostCard(
                        post = post,
                        creator = associatedCreator,
                        onProfileClick = { onOpenProfile(associatedCreator) },
                        onLikeClick = { onLikePost(post) },
                        onSaveClick = { onSavePost(post) },
                        onCommentClick = { activeCommentingPost = post },
                        onCollaborateChat = { onOpenChat(post.creatorHandle) }
                    )
                }
            }
        }
    }

    // Comment / critique dialog
    if (activeCommentingPost != null) {
        AlertDialog(
            onDismissRequest = { activeCommentingPost = null },
            title = {
                Text(
                    text = "Collaborate on ${activeCommentingPost?.title}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = FloOliveDark
                )
            },
            text = {
                Column {
                    Text(
                        text = "Share production feedback, lighting suggestions, or color grade tips with ${activeCommentingPost?.creatorName}:",
                        fontSize = 13.sp,
                        color = FloTextMuted
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text("e.g. Love the natural bokeh! Which stop?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        commentText = ""
                        activeCommentingPost = null
                    }
                ) {
                    Text("Post Feedback", color = FloOliveMedium, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { activeCommentingPost = null }) {
                    Text("Cancel", color = FloTextMuted)
                }
            }
        )
    }
}

/**
 * Arched Hero Card matching reference mockup center:
 * Rounded top arch with child at beach playing with raft, "Nacpan Beach", "Drift into Wave A Summer Escape"
 */
@Composable
private fun ArchedHeroCard(
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { onCardClick() }
            .testTag("home_arched_hero_card"),
        shape = RoundedCornerShape(
            topStart = 44.dp,
            topEnd = 44.dp,
            bottomStart = 28.dp,
            bottomEnd = 28.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.05f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_beach_escape),
                contentDescription = "Summer Escape Story",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark gradient overlay for typography
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.25f),
                                Color.Black.copy(alpha = 0.85f)
                            ),
                            startY = 200f
                        )
                    )
            )

            // Bottom title overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = "Nacpan Beach",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Drift into Wave\nA Summer Escape",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
            }
        }
    }
}

/**
 * Quick memory & collection promo cards from reference:
 * - "flo Gallery Offers you to get V.2 in 30% off"
 * - "Spring and Winter Img Collection" (pink tag)
 * - "Office Project Meet Up Image folder"
 */
@Composable
private fun QuickCollectionsRow(
    onNavigateToGallery: () -> Unit,
    onNavigateToSchedule: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        // Promo Offer Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToGallery() }
                .testTag("gallery_offer_card"),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "flo.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Gallery Offers you to get V.2 in 30% off",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloTextDark,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(FloOliveMedium),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open Gallery",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Folders Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Card 1: Spring and Winter
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToGallery() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFFE4E6)
                    ) {
                        Text(
                            text = "Spring & Winter",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFBE123C),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Img Collection",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloTextDark
                    )
                }
            }

            // Card 2: Office Project Meet Up
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToSchedule() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = FloBgPistachio
                    ) {
                        Text(
                            text = "Task Schedule",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = FloOliveDark,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Office Meet Up",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FloTextDark
                    )
                }
            }
        }
    }
}

@Composable
private fun StoriesBar(
    creators: List<CreativeUser>,
    onCreatorClick: (CreativeUser) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(creators, key = { it.handle }) { creator ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onCreatorClick(creator) }
                        .padding(vertical = 4.dp)
                ) {
                    CreatorAvatar(
                        avatarKey = creator.avatarUrl,
                        name = creator.name,
                        size = 56.dp,
                        hasStoryBorder = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = creator.name.split(" ").firstOrNull() ?: creator.name,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = FloTextDark,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedPostCard(
    post: CreativePost,
    creator: CreativeUser?,
    onProfileClick: () -> Unit,
    onLikeClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCommentClick: () -> Unit,
    onCollaborateChat: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .testTag("feed_card_${post.id}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Creator info row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProfileClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CreatorAvatar(
                        avatarKey = post.creatorAvatar,
                        name = post.creatorName,
                        size = 38.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = post.creatorName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = FloTextDark
                        )
                        Text(
                            text = post.creatorHandle,
                            fontSize = 11.sp,
                            color = FloTextMuted
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = FloBgSage
                ) {
                    Text(
                        text = post.cameraGear.split("•").firstOrNull()?.trim() ?: "RAW",
                        color = FloOliveDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Photography view
            CreativeMediaView(
                mediaKey = post.imageUrls,
                aspectRatio = 1.25f,
                overlayLabel = post.location
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Post Title & Statement
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = FloTextDark
            )
            Text(
                text = post.caption,
                fontSize = 13.sp,
                color = FloTextMuted,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onLikeClick() }
                    ) {
                        Icon(
                            imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (post.isLiked) Color(0xFFE11D48) else FloTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${post.likesCount}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = FloTextDark
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onCommentClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = "Comment",
                            tint = FloTextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${post.commentsCount}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = FloTextDark
                        )
                    }

                    IconButton(
                        onClick = { onCollaborateChat() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Collab",
                            tint = FloOliveMedium,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { onSaveClick() },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (post.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save",
                        tint = if (post.isSaved) FloOliveDark else FloTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
