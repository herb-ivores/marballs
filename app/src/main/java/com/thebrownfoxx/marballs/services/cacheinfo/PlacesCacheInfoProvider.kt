package com.thebrownfoxx.marballs.services.cacheinfo

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Distance
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.extensions.distanceTo
import com.thebrownfoxx.marballs.services.authentication.Authentication

class PlacesFirebaseCacheInfoService(
    private val placesClient: PlacesClient,
    private val auth: Authentication,
    private val application: Application,
) : CacheInfoProvider {

    override fun Cache.toCacheInfo(currentLocation: Location): CacheInfo {
        var infoHolder = CacheInfo(
            id = "defaultId",
            name = "defaultName",
            description = "defaultDescription",
            location = "defaultLocation",
            distance = Distance(0.0),
            author = User("defaultId", "defaultEmail"),
        )

        if (
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            val findCurrentPlaceRequest =
                FindCurrentPlaceRequest.newInstance(listOf(Place.Field.ADDRESS, Place.Field.NAME))
            placesClient.findCurrentPlace(findCurrentPlaceRequest)
                .addOnSuccessListener { response: FindCurrentPlaceResponse ->
                    val placeLikelihoods = response.placeLikelihoods
                    if (placeLikelihoods.isNotEmpty()) {
                        val firstPlace = placeLikelihoods[0].place
                        val locationName = firstPlace.name.orEmpty()
                        val address = firstPlace.address.orEmpty()
                        val distance = currentLocation.distanceTo(location)
                        val author = User("defaultUid", "defaultDisplayName")
                        infoHolder = CacheInfo(
                            id = id ?: "DefaultID",
                            name = name,
                            description = description,
                            location = "$locationName, $address",
                            distance = distance,
                            author = auth.currentUser.value ?: author
                        )
                    }
                }
                .addOnFailureListener { exception: Exception ->
                }
        }

        return infoHolder
    }
}