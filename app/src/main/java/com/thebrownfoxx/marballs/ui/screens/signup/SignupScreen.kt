package com.thebrownfoxx.marballs.ui.screens.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.marballs.ui.extensions.SnackbarHost
import com.thebrownfoxx.marballs.ui.extensions.rememberSnackbarHostState
import com.thebrownfoxx.marballs.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignupScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    repeatPassword: String,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUp: () -> Unit,
    loading: Boolean,
    errors: Flow<String>,
    onNavigateUp: () -> Unit,
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
            Row {
                IconButton(
                    imageVector = Icons.TwoTone.ArrowBack,
                    contentDescription = "Back button",
                    onClick = onNavigateUp,
                )
                Text(
                    text = "Signup",
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.size(48.dp))
            }
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
            VerticalSpacer(height = 16.dp)
            TextField(
                label = { Text(text = "Repeat Password") },
                value = repeatPassword,
                onValueChange = onRepeatPasswordChange,
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
                text = "Signup",
                onClick = onSignUp,
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    AppTheme {
        SignupScreen(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            repeatPassword = "",
            onRepeatPasswordChange = {},
            onSignUp = {},
            loading = false,
            errors = emptyFlow(),
            onNavigateUp = {},
        )
    }
}

@Preview
@Composable
fun SignupScreenLoadingPreview() {
    AppTheme {
        SignupScreen(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            repeatPassword = "",
            onRepeatPasswordChange = {},
            onSignUp = {},
            loading = true,
            errors = emptyFlow(),
            onNavigateUp = {},
        )
    }
}