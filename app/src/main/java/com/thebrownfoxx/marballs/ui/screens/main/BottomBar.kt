package com.thebrownfoxx.marballs.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.twotone.Map
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@Composable
fun BottomBar(
    currentScreen: MainScreen,
    onNavigateTo: (MainScreen) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        for (screen in MainScreen.entries) {
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onNavigateTo(screen) },
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            MainScreen.Map -> Icons.TwoTone.Map
                            MainScreen.Caches -> Icons.AutoMirrored.TwoTone.List
                            MainScreen.Profile -> Icons.TwoTone.Person
                        },
                        contentDescription = null,
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    AppTheme {
        BottomBar(
            currentScreen = MainScreen.Map,
            onNavigateTo = {},
        )
    }
}