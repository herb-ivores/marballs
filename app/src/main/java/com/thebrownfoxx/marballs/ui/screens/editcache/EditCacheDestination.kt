package com.thebrownfoxx.marballs.ui.screens.editcache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application

@Destination(navArgsDelegate = CacheNavArgs::class)
@Composable
fun EditCache(
    navigator: DestinationsNavigator,
    viewModel: EditCacheViewModel = viewModel{
        EditCacheViewModel(
            application.authentication,
            application.locationProvider,
            application.cacheInfoProvider,
            application.cacheRepository,
            savedStateHandle = createSavedStateHandle(),
        )
    }
){
    with(viewModel){
        val cache by cache.collectAsStateWithLifecycle()
        val locationName by locationName.collectAsStateWithLifecycle()

        LaunchedEffect(successful) {
            successful.collect {
                navigator.navigateUp()
            }
        }

        cache?.let {
            EditCacheScreen(
                cache = it,
                onNameChange = ::setName,
                onDescriptionChange = ::setDescription,
                locationName = locationName,
                onResetLocation = ::resetLocation,
                onUpdate = ::update,
                errors = errors,
                navigateUp = navigator::navigateUp
            )
        }
    }
}