package com.thebrownfoxx.marballs.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.thebrownfoxx.components.FilledButton

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        GoogleMap()
        FilledButton(text = "Logout", onClick = onLogout)
    }
}