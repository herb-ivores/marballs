@file:Suppress("unused")

package com.thebrownfoxx.components.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

val PaddingValues.left @Composable get() = calculateLeftPadding(LocalLayoutDirection.current)
val PaddingValues.right @Composable get() = calculateRightPadding(LocalLayoutDirection.current)

val PaddingValues.start @Composable get() = calculateStartPadding(LocalLayoutDirection.current)
val PaddingValues.top get() = calculateTopPadding()
val PaddingValues.end @Composable get() = calculateEndPadding(LocalLayoutDirection.current)
val PaddingValues.bottom get() = calculateBottomPadding()
val StatusBarHeight @Composable get() = WindowInsets.statusBars.asPaddingValues().top
val NavigationBarHeight @Composable get() = WindowInsets.navigationBars.asPaddingValues().bottom

@Composable
operator fun PaddingValues.plus(other: PaddingValues) = PaddingValues(
    start = start + other.start,
    top = top + other.top,
    end = end + other.end,
    bottom = bottom + other.bottom,
)

@Composable
operator fun PaddingValues.minus(other: PaddingValues) = PaddingValues(
    start = start - other.start,
    top = top - other.top,
    end = end - other.end,
    bottom = bottom - other.bottom,
)