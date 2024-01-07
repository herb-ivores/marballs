@file:Suppress("unused")

package com.thebrownfoxx.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.thebrownfoxx.components.extension.NavigationBarHeight
import com.thebrownfoxx.components.extension.StatusBarHeight

@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun RowScope.WeightedSpacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.WeightedSpacer(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun StatusBarSpacer() {
    VerticalSpacer(height = StatusBarHeight)
}

@Composable
fun NavigationBarSpacer() {
    VerticalSpacer(height = NavigationBarHeight)
}