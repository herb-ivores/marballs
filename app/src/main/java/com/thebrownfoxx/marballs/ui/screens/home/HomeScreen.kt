package com.thebrownfoxx.marballs.ui.screens.home

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledButton(text = "Logout", onClick = onLogout)
}