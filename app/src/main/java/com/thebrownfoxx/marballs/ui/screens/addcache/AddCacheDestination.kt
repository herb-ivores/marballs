package com.thebrownfoxx.marballs.ui.screens.addcache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.marballs.application

@Destination
@Composable
fun AddCacheDestination(
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

        AddCacheScreen(
            name = name,
            onNameChange = ::setName,
            description = description,
            onDescriptionChange = ::setDescription,
            location = location,
            locationName = locationName,
            onLocationChange = ::setLocation,
            onResetLocation = ::resetLocation,
            onSave = {
                navigator.navigateUp()
            },
        )
    }
}