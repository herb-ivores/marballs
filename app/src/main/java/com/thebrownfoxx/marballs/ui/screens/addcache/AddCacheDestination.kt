package com.thebrownfoxx.marballs.ui.screens.addcache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application
import com.thebrownfoxx.marballs.domain.Location

@Destination
@Composable
fun AddCache(
    navigator: DestinationsNavigator,
    viewModel: AddCacheViewModel = viewModel {
        AddCacheViewModel(
            application.authentication,
            application.locationProvider,
            application.cacheInfoProvider,
            application.cacheRepository,
        )
    }
) {
    with(viewModel) {
        val name by name.collectAsStateWithLifecycle()
        val description by description.collectAsStateWithLifecycle()
        val location by location.collectAsStateWithLifecycle()
        val locationName by locationName.collectAsStateWithLifecycle()
        val loading by loading.collectAsStateWithLifecycle()

        val loggedIn by loggedIn.collectAsStateWithLifecycle()

        LaunchedEffect(loggedIn) {
            if (!loggedIn) {
                navigator.navigateUp()
            }
        }

        LaunchedEffect(successful) {
            successful.collect {
                navigator.navigateUp()
            }
        }

        AddCacheScreen(
            name = name,
            onNameChange = ::setName,
            description = description,
            onDescriptionChange = ::setDescription,
            location = location ?: Location(0.0, 0.0),
            locationName = locationName,
            onResetLocation = ::resetLocation,
            onAdd = ::add,
            errors = errors,
            navigateUp = navigator::navigateUp,
            loading = loading,
        )
    }
}