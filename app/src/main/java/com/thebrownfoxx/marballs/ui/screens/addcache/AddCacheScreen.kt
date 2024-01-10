package com.thebrownfoxx.marballs.ui.screens.addcache

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.MyLocation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.extensions.toLatLng
import com.thebrownfoxx.marballs.ui.components.EditableCacheCard
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCacheScreen(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    location: Location,
    locationName: String,
    onLocationChange: (Location) -> Unit,
    onResetLocation: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = remember(location.key.toString()) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                location.toLatLng(),
                15f,
            ),
        )
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving) {
            onLocationChange(
                Location(
                    latitude = cameraPositionState.position.target.latitude,
                    longitude = cameraPositionState.position.target.longitude,
                )
            )
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add Cache")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                )
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(onClick = onResetLocation) {
                Icon(
                    imageVector = Icons.TwoTone.MyLocation,
                    contentDescription = null,
                )
            }
        },
        bottomBar = {
            EditableCacheCard(
                name = name,
                onNameChange = onNameChange,
                description = description,
                onDescriptionChange = onDescriptionChange,
                location = locationName,
                saveButton = {
                    FilledButton(
                        icon = Icons.TwoTone.Add,
                        text = "Add",
                        onClick = onSave,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                modifier = Modifier
                    .padding(PaddingValues(16.dp) - PaddingValues(top = 16.dp)),
            )
        }
    ) { contentPadding ->
        val ignored = contentPadding
        GoogleMap(cameraPositionState = cameraPositionState)
    }
}

@Preview
@Composable
fun AddCacheScreenPreview() {
    AppTheme {
        AddCacheScreen(
            name = "Huge Booty",
            onNameChange = {},
            description = "Sussy baka hiding in the bushes.",
            onDescriptionChange = {},
            location = Location(
                latitude = 0.0,
                longitude = 0.0,
            ),
            locationName = "Area 69",
            onLocationChange = {},
            onResetLocation = {},
            onSave = {},
        )
    }
}