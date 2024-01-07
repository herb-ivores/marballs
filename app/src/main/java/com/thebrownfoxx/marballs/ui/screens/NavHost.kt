package com.thebrownfoxx.marballs.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.thebrownfoxx.marballs.ui.extensions.getDefaultTransitions

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun NavHost() {
    val engine = rememberAnimatedNavHostEngine(
        rootDefaultAnimations = getDefaultTransitions(LocalDensity.current),
    )

        DestinationsNavHost(
            engine = engine,
            navGraph = NavGraphs.root,
        )
}