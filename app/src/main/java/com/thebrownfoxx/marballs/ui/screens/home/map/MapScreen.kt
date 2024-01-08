package com.thebrownfoxx.marballs.ui.screens.home.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.extensions.toLatLng
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@Suppress("UNUSED_VARIABLE")
@Composable
fun MapScreen(
    currentLocation: Location?,
    selectedCache: CacheInfo?,
    onResetLocation: () -> Unit,
    onAddCache: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = remember(currentLocation.toString()) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                currentLocation?.toLatLng() ?: LatLng(0.0, 0.0),
                15f,
            ),
        )
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallFloatingActionButton(onClick = onResetLocation) {
                    Icon(
                        imageVector = Icons.TwoTone.MyLocation,
                        contentDescription = null,
                    )
                }
                VerticalSpacer(height = 8.dp)
                FloatingActionButton(
                    onClick = onAddCache,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.Add,
                        contentDescription = null,
                    )
                }
            }
        },
        bottomBar = {
            if (selectedCache != null) {
                CacheCard(
                    info = selectedCache,
                    modifier = Modifier
                        .padding(PaddingValues(16.dp) - PaddingValues(top = 16.dp))
                        .fillMaxWidth(),
                )
            }
        }
    ) { contentPadding ->
        val ignored = contentPadding
        GoogleMap(cameraPositionState = cameraPositionState)
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    AppTheme {
        MapScreen(
            currentLocation = null,
            selectedCache = null,
            onResetLocation = {},
            onAddCache = {},
        )
    }
}

@Preview
@Composable
fun MapScreenWithCachePreview() {
    AppTheme {
        MapScreen(
            currentLocation = null,
            selectedCache = CacheInfo(
                id = "1",
                name = "Sample Cache",
                description = "This is a sample cache.",
                location = "Test Avenue, Test Tease City",
                distance = 69.69.meters,
            ),
            onResetLocation = {},
            onAddCache = {},
        )
    }
}