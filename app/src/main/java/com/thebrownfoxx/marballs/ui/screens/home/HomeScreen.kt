package com.thebrownfoxx.marballs.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.extensions.toLatLng

@Composable
fun HomeScreen(
    currentLocation: Location?,
    onLogout: () -> Unit,
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

    Column {
        GoogleMap(cameraPositionState = cameraPositionState)
        FilledButton(text = "Logout", onClick = onLogout)
    }
}