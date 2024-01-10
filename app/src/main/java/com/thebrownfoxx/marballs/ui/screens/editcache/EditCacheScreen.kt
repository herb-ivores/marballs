package com.thebrownfoxx.marballs.ui.screens.editcache

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.FiberManualRecord
import androidx.compose.material.icons.twotone.MyLocation
import androidx.compose.material.icons.twotone.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.extensions.toLatLng
import com.thebrownfoxx.marballs.ui.components.EditableCacheCard
import com.thebrownfoxx.marballs.ui.extensions.SnackbarHost
import com.thebrownfoxx.marballs.ui.extensions.rememberSnackbarHostState
import com.thebrownfoxx.marballs.ui.extensions.toLocation
import com.thebrownfoxx.marballs.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCacheScreen(
    cache: Cache,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    locationName: String,
    onResetLocation: () -> Unit,
    onUpdate: (Location) -> Unit,
    errors: Flow<String>,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = remember(cache.location.key.toString()) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                cache.location.toLatLng(),
                15f,
            ),
        )
    }

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Edit Cache")
                },
                navigationIcon = {
                    IconButton(
                        imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                        contentDescription = null,
                        onClick = navigateUp,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                )
            )
        },
        snackbarHost = {
            SnackbarHost(state = rememberSnackbarHostState(errors))
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
                name = cache.name,
                onNameChange = onNameChange,
                description = cache.description,
                onDescriptionChange = onDescriptionChange,
                location = locationName,
                saveButton = {
                    FilledButton(
                        icon = Icons.TwoTone.Save,
                        text = "Save",
                        onClick = {
                            onUpdate(cameraPositionState.position.target.toLocation())
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                modifier = Modifier
                    .padding(PaddingValues(16.dp) - PaddingValues(top = 16.dp)),
            )
        }
    ) { contentPadding ->
        val ignored = contentPadding
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(cameraPositionState = cameraPositionState)
            Icon(
                imageVector = Icons.TwoTone.FiberManualRecord,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
fun EditCacheScreenPreview() {
    AppTheme {
        EditCacheScreen(
            cache = Cache(
                id = "ergsfvg",
                name ="Name",
                description = "asfwfqcasc",
                location = Location(15.12321,120.21321),
                authorUid = "AUTH325RGS",
            ),
            onNameChange = {},
            onDescriptionChange = {},
            locationName = "Area 69",
            onResetLocation = {},
            onUpdate = {},
            errors = emptyFlow(),
            navigateUp = {},
        )
    }
}