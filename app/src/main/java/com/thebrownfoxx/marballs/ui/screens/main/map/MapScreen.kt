package com.thebrownfoxx.marballs.ui.screens.main.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.MyLocation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.marballs.R
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.domain.meters
import com.thebrownfoxx.marballs.extensions.toLatLng
import com.thebrownfoxx.marballs.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNUSED_VARIABLE")
@Composable
fun MapScreen(
    location: Location?,
    caches: List<CacheInfo>,
    selectedCache: CacheInfo?,
    allowCacheEdit: Boolean,
    selectedCacheFound: Boolean,
    onSelectCache: (CacheInfo) -> Unit,
    onMarkSelectedCacheAsFound: () -> Unit,
    onUnmarkSelectedCacheAsFound: () -> Unit,
    onResetLocation: () -> Unit,
    onAddCache: () -> Unit,
    onEditCache: () -> Unit,
    deleteDialogVisible: Boolean,
    onInitiateDeleteCache: () -> Unit,
    onCancelDeleteCache: () -> Unit,
    onDeleteCache: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val cameraPositionState = remember(location?.key.toString()) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                location?.toLatLng() ?: LatLng(0.0, 0.0),
                15f,
            ),
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                        )
                        HorizontalSpacer(width = 8.dp)
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                    append("Mar")
                                }
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                    append("balls")
                                }
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                )
            )
        },
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
                    cache = selectedCache,
                    allowEdit = allowCacheEdit,
                    found = selectedCacheFound,
                    onMarkAsFound = onMarkSelectedCacheAsFound,
                    onUnmarkAsFound = onUnmarkSelectedCacheAsFound,
                    onEdit = onEditCache,
                    onDelete = onInitiateDeleteCache,
                    modifier = Modifier
                        .padding(PaddingValues(16.dp) - PaddingValues(top = 16.dp)),
                )
            }
        }
    ) { contentPadding ->
        val ignored = contentPadding
        GoogleMap(
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
        ) {
            caches.forEach { cache ->
                Marker(
                    state = rememberMarkerState(position = cache.coordinates.toLatLng()),
                    onClick = {
                        onSelectCache(cache)
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (selectedCache?.id == cache.id) BitmapDescriptorFactory.HUE_RED
                        else BitmapDescriptorFactory.HUE_ORANGE
                    ),
                )
            }
        }
    }
    DeleteCacheDialog(
        visible = deleteDialogVisible,
        cacheName = selectedCache?.name.orEmpty(),
        onConfirm = onDeleteCache,
        onDismiss = onCancelDeleteCache,
    )
}

@Preview
@Composable
fun MapScreenPreview() {
    AppTheme {
        MapScreen(
            location = null,
            caches = emptyList(),
            selectedCache = null,
            onSelectCache = {},
            allowCacheEdit = true,
            selectedCacheFound = false,
            onMarkSelectedCacheAsFound = {},
            onUnmarkSelectedCacheAsFound = {},
            onResetLocation = {},
            onEditCache = {},
            onAddCache = {},
            deleteDialogVisible = false,
            onInitiateDeleteCache = {},
            onCancelDeleteCache = {},
            onDeleteCache = {},
        )
    }
}

@Preview
@Composable
fun MapScreenWithCachePreview() {
    AppTheme {
        MapScreen(
            location = null,
            caches = emptyList(),
            selectedCache = CacheInfo(
                id = "1",
                name = "Sample Cache",
                description = "This is a sample cache.",
                location = "Test Avenue, Test Tease City",
                distance = 69.69.meters,
                author = User(uid = "1", email = "jonelespiritu@fuckers-online.io"),
                coordinates = Location(19.2132, 121.3242)
            ),
            onSelectCache = {},
            allowCacheEdit = true,
            selectedCacheFound = false,
            onMarkSelectedCacheAsFound = {},
            onUnmarkSelectedCacheAsFound = {},
            onEditCache = {},
            onResetLocation = {},
            onAddCache = {},
            deleteDialogVisible = false,
            onInitiateDeleteCache = {},
            onCancelDeleteCache = {},
            onDeleteCache = {},
        )
    }
}