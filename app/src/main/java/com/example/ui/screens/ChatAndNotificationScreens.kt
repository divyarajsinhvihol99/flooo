package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChatMessage
import com.example.data.model.CollabRequest
import com.example.data.model.CreativeUser
import com.example.ui.Screen
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
fun ChatListScreen(
    creators: List<CreativeUser>,
    unreadCount: Int,
    onSelectChat: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenDrawerMenu: () -> Unit
) {
    var searchChatQuery by remember { mutableStateOf("") }

    val chatUsers = listOf(
        Pair(creators.find { it.handle == "@ridzjcob" } ?: CreativeUser("@ridzjcob", "Ridhwan Nordin", "Photographer", "Tokyo", "", "avatar_ridhwan"), "2+"),
        Pair(creators.find { it.handle == "@ssialk" } ?: CreativeUser("@ssialk", "Sonia clerk", "Fashion Director", "Paris", "", "avatar_sonia"), "3"),
        Pair(creators.find { it.handle == "@kimikach" } ?: CreativeUser("@kimikach", "Chizhua kimiko", "Cinematographer", "Kyoto", "", "avatar_chizhua"), "5+"),
        Pair(creators.find { it.handle == "@jaasmehra" } ?: CreativeUser("@jaasmehra", "Jasleen Mehra", "Producer", "London", "", "avatar_jasleen"), "1"),
        Pair(creators.find { it.handle == "@chengalino" } ?: CreativeUser("@chengalino", "Alino Cheng", "Landscape Artist", "Vancouver", "", "avatar_alino"), "9+"),
        Pair(CreativeUser("@chauhanena", "Neina chuahan", "Documentary Video", "Mumbai", "", "avatar_jasleen"), "1+"),
        Pair(creators.find { it.handle == "@ninokaek" } ?: CreativeUser("@ninokaek", "Ekeya ninoka", "Street Photographer", "Istanbul", "", "avatar_ekeya"), "9+")
    )

    val filteredList = chatUsers.filter {
        it.first.name.contains(searchChatQuery, ignoreCase = true) ||
                it.first.handle.contains(searchChatQuery, ignoreCase = true)
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
                    title = "Conversations",
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
                    .testTag("chat_list_screen")
            ) {
                // Search bar
                OutlinedTextField(
                    value = searchChatQuery,
                    onValueChange = { searchChatQuery = it },
                    placeholder = { Text("Search messages & creators", color = FloTextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = FloOliveDark
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .testTag("chat_search_input"),
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = FloOliveMedium,
                        unfocusedBorderColor = FloCardBorder
                    ),
                    singleLine = true
                )

                // Creator Chats List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredList, key = { it.first.handle }) { (user, badge) ->
                        ChatRowItem(
                            user = user,
                            badge = badge,
                            onClick = { onSelectChat(user.handle) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatRowItem(
    user: CreativeUser,
    badge: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("chat_row_${user.handle}"),
        shape = RoundedCornerShape(20.dp),
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                CreatorAvatar(
                    avatarKey = user.avatarUrl,
                    name = user.name,
                    size = 46.dp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = user.name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = FloTextDark
                    )
                    Text(
                        text = user.handle,
                        fontSize = 12.sp,
                        color = FloTextMuted
                    )
                }
            }

            Surface(
                shape = CircleShape,
                color = FloBgPistachio,
                modifier = Modifier.size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = badge,
                        color = FloOliveDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBoxScreen(
    collaboratorHandle: String,
    creators: List<CreativeUser>,
    messages: List<ChatMessage>,
    onSendMessage: (String) -> Unit,
    onBack: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val collaborator = creators.firstOrNull { it.handle == collaboratorHandle }
        ?: CreativeUser(collaboratorHandle, "Ridhwan Nordin", "Photographer", "Tokyo", "", "avatar_ridhwan")

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
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
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = FloOliveDark
                                )
                            }

                            CreatorAvatar(
                                avatarKey = collaborator.avatarUrl,
                                name = collaborator.name,
                                size = 38.dp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = collaborator.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = FloTextDark
                                )
                                Text(
                                    text = collaborator.handle,
                                    fontSize = 11.sp,
                                    color = FloTextMuted
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = FloOliveDark, modifier = Modifier.size(20.dp))
                            }
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Videocam, contentDescription = "Video Collab", tint = FloOliveDark, modifier = Modifier.size(22.dp))
                            }
                        }
                    }
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .testTag("chat_box_screen")
            ) {
                // Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        ChatBubble(message = msg)
                    }
                }

                // Input bar
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Share notes, shot ideas, files...", color = FloTextMuted) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chat_message_input"),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FloOliveMedium,
                                unfocusedBorderColor = FloCardBorder
                            ),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        FloatingActionButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    onSendMessage(inputText)
                                    inputText = ""
                                }
                            },
                            shape = CircleShape,
                            containerColor = FloOliveDark,
                            contentColor = Color.White,
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("send_message_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowUpward,
                                contentDescription = "Send",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val isMe = message.isFromMe
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 18.dp,
                topEnd = 18.dp,
                bottomStart = if (isMe) 18.dp else 4.dp,
                bottomEnd = if (isMe) 4.dp else 18.dp
            ),
            color = if (isMe) FloOliveDark else Color.White,
            border = if (!isMe) androidx.compose.foundation.BorderStroke(1.dp, FloCardBorder) else null,
            shadowElevation = 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(
                    text = message.text,
                    color = if (isMe) Color.White else FloTextDark,
                    fontSize = 14.sp
                )
                val formattedTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date(message.timestamp))
                Text(
                    text = formattedTime,
                    color = if (isMe) Color.White.copy(alpha = 0.7f) else FloTextMuted,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationsScreen(
    requests: List<CollabRequest>,
    onAcceptRequest: (CollabRequest) -> Unit,
    onDeclineRequest: (CollabRequest) -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenProfile: () -> Unit,
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
                    onNotificationsClick = { /* Already here */ },
                    unreadNotifications = 0,
                    title = "Requests & Inquiries",
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
                    .testTag("notifications_screen"),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "Collaboration Inquiries (${requests.size})",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveDark,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(requests, key = { it.id }) { req ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("request_item_${req.id}"),
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CreatorAvatar(
                                        avatarKey = req.senderAvatar,
                                        name = req.senderName,
                                        size = 40.dp
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = req.senderName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = FloTextDark
                                        )
                                        Text(
                                            text = req.projectTitle,
                                            fontSize = 11.sp,
                                            color = FloTextMuted
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (req.status == "PENDING") FloBgPistachio else FloBgSage
                                ) {
                                    Text(
                                        text = req.status,
                                        color = FloOliveDark,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = req.message,
                                fontSize = 13.sp,
                                color = FloTextDark,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Type: ${req.type}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = FloOliveMedium
                                )

                                if (req.status == "PENDING") {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = FloBgSage,
                                            modifier = Modifier.clickable { onDeclineRequest(req) }
                                        ) {
                                            Text(
                                                text = "Decline",
                                                color = FloTextMuted,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }

                                        Surface(
                                            shape = RoundedCornerShape(14.dp),
                                            color = FloOliveDark,
                                            modifier = Modifier.clickable { onAcceptRequest(req) }
                                        ) {
                                            Text(
                                                text = "Accept",
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
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
    }
}
