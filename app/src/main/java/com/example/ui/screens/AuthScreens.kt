package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.FloWordmark
import com.example.ui.theme.FloBgCream
import com.example.ui.theme.FloBgPistachio
import com.example.ui.theme.FloBgSage
import com.example.ui.theme.FloCardBorder
import com.example.ui.theme.FloOliveDark
import com.example.ui.theme.FloOliveMedium
import com.example.ui.theme.FloOliveSoft
import com.example.ui.theme.FloTextDark
import com.example.ui.theme.FloTextMuted

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("ekeya.ninoka@flo.creative") }
    var password by remember { mutableStateOf("••••••••") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FloBgCream, FloBgSage, FloBgPistachio)
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .testTag("login_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top curved photography banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(32.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_auth_landscape),
                contentDescription = "Login Banner Landscape",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Flo wordmark text only ("remove the logo, just write flo only")
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                FloWordmark(color = Color.White, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = FloOliveDark
            )
            Text(
                text = "Sign in to access your portfolios and collaborations",
                fontSize = 13.sp,
                color = FloTextMuted,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = FloTextMuted) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_email_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = FloTextMuted) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = FloTextMuted
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_password_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = onLoginSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("login_submit_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FloOliveDark)
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Guest Quick Access
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onLoginSuccess() }
                    .border(1.dp, FloCardBorder, RoundedCornerShape(24.dp))
                    .padding(vertical = 12.dp)
                    .testTag("login_guest_button"),
                color = Color.White
            ) {
                Text(
                    text = "Continue as Guest Creator",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FloOliveDark,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Switch to Register
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 13.sp,
                    color = FloTextMuted
                )
                TextButton(onClick = onNavigateToRegister) {
                    Text(
                        text = "Sign Up",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveMedium
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("Ekeya Ninoka") }
    var email by remember { mutableStateOf("ekeya@flo.creative") }
    var specialty by remember { mutableStateOf("Portrait & Street Photographer") }
    var password by remember { mutableStateOf("••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(FloBgCream, FloBgSage, FloBgPistachio)
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .testTag("register_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Flo header text only
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            FloWordmark(color = FloOliveDark, fontSize = 28.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Join flo",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = FloOliveDark
            )
            Text(
                text = "Create your digital stories and showcase your portfolio",
                fontSize = 13.sp,
                color = FloTextMuted,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            // Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name", color = FloTextMuted) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_name_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = FloTextMuted) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_email_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Creative Role
            OutlinedTextField(
                value = specialty,
                onValueChange = { specialty = it },
                label = { Text("Specialty / Creative Discipline", color = FloTextMuted) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_specialty_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = FloTextMuted) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register_password_input"),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FloOliveMedium,
                    unfocusedBorderColor = FloCardBorder,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Register Button
            Button(
                onClick = onRegisterSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("register_submit_button"),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FloOliveDark)
            ) {
                Text(
                    text = "Create Creator Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Switch to Login
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 13.sp,
                    color = FloTextMuted
                )
                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = "Sign In",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = FloOliveMedium
                    )
                }
            }
        }
    }
}
