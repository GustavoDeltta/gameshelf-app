package com.app.gameshelf.ui.components.linkSteam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.gameshelf.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun LinkSteamModal(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    var steamId by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(context) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Link Steam Account",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                Text(
                    text = "Enter your Steam ID 64 to link your account and import your games.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = steamId,
                    onValueChange = { 
                        steamId = it 
                        errorMessage = null
                    },
                    label = { Text("Steam ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = errorMessage != null
                )
                
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Button(
                    onClick = {
                        if (steamId.isNotBlank()) {
                            isLoading = true
                            scope.launch {
                                try {
                                    val response = authRepository.linkSteam(steamId)
                                    if (response.isSuccessful) {
                                        onSuccess()
                                    } else {
                                        errorMessage = "Failed to link account: ${response.code()}"
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        } else {
                            errorMessage = "Please enter your Steam ID"
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Link Account")
                }
            }
        },
        dismissButton = {
            if (!isLoading) {
                TextButton(onClick = onDismiss) {
                    Text(
                        "Cancel",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp)
    )
}