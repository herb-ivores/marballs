package com.thebrownfoxx.marballs.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.marballs.ui.extensions.SnackbarHost
import com.thebrownfoxx.marballs.ui.extensions.rememberSnackbarHostState
import com.thebrownfoxx.marballs.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoginScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    loading: Boolean,
    errors: Flow<String>,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = rememberSnackbarHostState(messages = errors)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier,
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(16.dp)
                .padding(contentPadding)
                .fillMaxSize(),
        ) {
            Text(
                text = "Login",
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 32.dp)
            TextField(
                label = { Text(text = "Email") },
                value = email,
                onValueChange = onEmailChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(height = 16.dp)
            TextField(
                label = { Text(text = "Password") },
                value = password,
                onValueChange = onPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            AnimatedVisibility(visible = loading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                )
            }
            VerticalSpacer(height = 16.dp)
            FilledButton(
                text = "Login",
                onClick = onLogin,
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
            )
            TextButton(
                text = "Sign up",
                onClick = onSignup,
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            onLogin = {},
            onSignup = {},
            loading = false,
            errors = emptyFlow(),
        )
    }
}

@Preview
@Composable
fun LoginScreenLoadingPreview() {
    AppTheme {
        LoginScreen(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            onLogin = {},
            onSignup = {},
            loading = true,
            errors = emptyFlow(),
        )
    }
}