package com.thebrownfoxx.marballs.services.cacheinfo

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Distance
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.extensions.distanceTo
import com.thebrownfoxx.marballs.services.authentication.Authentication
import kotlin.random.Random

infix fun Boolean.AND(other: Boolean) = this&& other

class PlacesFirebaseCacheInfoService(
    private val placesClient: PlacesClient,
    private val auth: Authentication,
    private val application: Application,
) : CacheInfoProvider {


    override fun Cache.toCacheInfo(currentLocation: Location): CacheInfo {
        Log.d("PlacesFirebaseCacheInfoService", "Hello1")
        var infoHolder = CacheInfo(
            id = Random.nextLong().toString(),
            name = "defaultName",
            description = "defaultDescription",
            coordinates = location,
            location = "defaultLocation",
            distance = Distance(0.0),
            author = User("defaultId", "defaultEmail"),
        )
        if (
            (ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) AND
            (ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            return infoHolder
        }
        val findCurrentPlaceRequest =
            FindCurrentPlaceRequest.newInstance(listOf(Place.Field.ADDRESS, Place.Field.NAME))
        Log.d("PlacesFirebaseCacheInfoService", "hello: $findCurrentPlaceRequest")
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
                        coordinates = location,
                        distance = distance,
                        author = auth.currentUser.value ?: author
                    )
                    Log.d("PlacesFirebaseCacheInfoService", "Number of place likelihoods: ${placeLikelihoods.size}")
                }
                else{
                    Log.d("PlacesFirebaseCacheInfoService", "Else invoked")
                }
            }
            .addOnFailureListener { exception: Exception ->
                Log.e("PlacesFirebaseCacheInfoService", "Error finding current place", exception)
            }
        Log.d("PlacesFirebaseCacheInfoService", "Returning infoHolder: $infoHolder")
        return infoHolder
    }

    override fun Location.getLocationName(): String {
        TODO("Not yet implemented")
    }
}