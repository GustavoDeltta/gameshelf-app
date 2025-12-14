package com.app.gameshelf.ui.screens.signup

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.gameshelf.R
import com.app.gameshelf.data.model.LoginRequest
import com.app.gameshelf.data.model.SignupRequest
import com.app.gameshelf.data.repository.AuthRepository
import com.app.gameshelf.ui.components.linkSteam.LinkSteamModal
import com.app.gameshelf.ui.navigation.routes.Route
import kotlinx.coroutines.launch

private enum class AuthMode {
    IDLE, SIGNUP, LOGIN
}

@Composable
fun SignupScreen(navController: NavController){

    var authMode by remember { mutableStateOf(AuthMode.IDLE) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var steamid by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showLinkSteamModal by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(context) }

    Scaffold { pad ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ){}
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .animateContentSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Crossfade(targetState = authMode, label = "AuthFormCrossfade") { mode ->
                    when (mode) {
                        AuthMode.SIGNUP -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    "Create Account",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text("Username") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                                )
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                                OutlinedTextField(
                                    value = steamid,
                                    onValueChange = { steamid = it },
                                    label = { Text("Steam ID (Optional)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                if (isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    Button(
                                        onClick = {
                                            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                                                isLoading = true
                                                scope.launch {
                                                    try {
                                                        // Note: steamid is currently not sent to the backend as the API doesn't support it yet
                                                        val signupResponse = authRepository.signup(
                                                            SignupRequest(username, email, password)
                                                        )
                                                        if (signupResponse.isSuccessful) {
                                                            // Auto-login after successful signup
                                                            val loginResponse = authRepository.login(
                                                                LoginRequest(email, password)
                                                            )
                                                            if (loginResponse.isSuccessful) {
                                                                val storedToken = authRepository.getToken()
                                                                if (storedToken != null) {
                                                                    Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show()
                                                                    // For new account, Steam ID is definitely null initially unless linked
                                                                    if (steamid.isNotBlank()) {
                                                                         val linkResponse = authRepository.linkSteam(steamid)
                                                                         if (linkResponse.isSuccessful) {
                                                                             navController.navigate(Route.Home.route)
                                                                         } else {
                                                                             Toast.makeText(context, "Failed to link Steam ID, proceeding without it.", Toast.LENGTH_SHORT).show()
                                                                             navController.navigate(Route.Home.route)
                                                                         }
                                                                    } else {
                                                                        showLinkSteamModal = true
                                                                    }
                                                                } else {
                                                                    Toast.makeText(context, "Signup successful but login failed", Toast.LENGTH_SHORT).show()
                                                                    authMode = AuthMode.LOGIN
                                                                }
                                                            } else {
                                                                Toast.makeText(context, "Signup successful, please login", Toast.LENGTH_SHORT).show()
                                                                authMode = AuthMode.LOGIN
                                                            }
                                                        } else {
                                                            Toast.makeText(context, "Signup failed: ${signupResponse.code()}", Toast.LENGTH_SHORT).show()
                                                        }
                                                    } catch (e: Exception) {
                                                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                                    } finally {
                                                        isLoading = false
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(context, "Fill in all fields", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonColors(
                                            contentColor = MaterialTheme.colorScheme.primary,
                                            containerColor = MaterialTheme.colorScheme.secondary,
                                            disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                            disabledContentColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(
                                            "Sign Up",
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                                Text(
                                    "Cancel",
                                    modifier = Modifier.clickable { authMode = AuthMode.IDLE },
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                        AuthMode.LOGIN -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    "Login",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text("Email") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                                )
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Password") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    visualTransformation = PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                                if (isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    Button(
                                        onClick = {
                                            if (email.isNotBlank() && password.isNotBlank()) {
                                                isLoading = true
                                                scope.launch {
                                                    try {
                                                        val response = authRepository.login(
                                                            LoginRequest(email, password)
                                                        )
                                                        if (response.isSuccessful) {
                                                            val body = response.body()
                                                            if (body != null) {
                                                                if (body.steamId == null) {
                                                                    showLinkSteamModal = true
                                                                } else {
                                                                    navController.navigate(Route.Home.route)
                                                                }
                                                            } else {
                                                                Toast.makeText(context, "Login failed: Empty response", Toast.LENGTH_SHORT).show()
                                                            }
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Login failed: ${response.code()}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    } catch (e: Exception) {
                                                        // Check if timeout
                                                        if (e is java.net.SocketTimeoutException) {
                                                            Toast.makeText(
                                                                context,
                                                                "Connection timeout. Please check your internet or try again.",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Error: ${e.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    } finally {
                                                        isLoading = false
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(context, "Fill in all fields", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp),
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonColors(
                                            contentColor = MaterialTheme.colorScheme.primary,
                                            containerColor = MaterialTheme.colorScheme.secondary,
                                            disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                            disabledContentColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(
                                            "Login",
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                                Text(
                                    "Cancel",
                                    modifier = Modifier.clickable { authMode = AuthMode.IDLE },
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                        AuthMode.IDLE -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Button(
                                    onClick = { authMode = AuthMode.SIGNUP },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = ButtonColors(
                                        contentColor = MaterialTheme.colorScheme.primary,
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                                        disabledContentColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        "Create New Account",
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                                Text(
                                    "I already have an account",
                                    modifier = Modifier.clickable { authMode = AuthMode.LOGIN },
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(1.dp)
                                            .background(MaterialTheme.colorScheme.outline),
                                    ) { }
                                    Text(
                                        "Or",
                                        color = MaterialTheme.colorScheme.outline,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(1.dp)
                                            .background(MaterialTheme.colorScheme.outline),
                                    ) { }
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
                                ) {
                                    OutlinedButton(
                                        onClick = {  },
                                        modifier = Modifier
                                            .size(50.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.google),
                                            contentDescription = "Google",
                                            modifier = Modifier
                                                .size(30.dp),
                                            tint = Color.Unspecified
                                        )
                                    }
                                    OutlinedButton(
                                        onClick = {  },
                                        modifier = Modifier
                                            .size(50.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.social),
                                            contentDescription = "Social",
                                            modifier = Modifier
                                                .size(30.dp),
                                            tint = Color.Unspecified
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
    
    if (showLinkSteamModal) {
        LinkSteamModal(
            onDismiss = { 
                showLinkSteamModal = false
                authRepository.clearToken()
                navController.navigate(Route.Signup.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onSuccess = {
                showLinkSteamModal = false
                navController.navigate(Route.Home.route)
            }
        )
    }
}