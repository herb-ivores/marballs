package com.thebrownfoxx.marballs.services.cacheinfo

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.thebrownfoxx.marballs.BuildConfig
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.CacheInfo
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.domain.User
import com.thebrownfoxx.marballs.extensions.distanceTo
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.awaitOutcome
import com.thebrownfoxx.marballs.services.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

infix fun Boolean.AND(other: Boolean) = this && other // LOL

class PlacesFirebaseCacheInfoService(
    private val placesClient: PlacesClient,
    private val auth: Authentication,
    private val application: Application,
    private val userRepository: UserRepository
) : CacheInfoProvider {
    /*    override fun Cache.toCacheInfo(currentLocation: Location): CacheInfo {
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

        val latitude = location.latitude
        val longitude = location.longitude

        val latLng = LatLng(latitude, longitude)

        Log.d("PlacesFirebaseCacheInfoService", "Latitude: $latitude, Longitude: $longitude")

        val placeId = runBlocking {
            getPlaceId(latLng)
        }


        val placeFields = listOf(Place.Field.ID, Place.Field.NAME)

        Log.d(this::class.simpleName, "placeID: $placeId")
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                Log.i("PlacesFirebaseCacheInfoService", "Place found: ${place.name}")
                val locationName = place.name.orEmpty()
                Log.d("PlacesFirebaseCacheInfoService", "Current Location: $currentLocation, Location: $location")
                val distance = currentLocation.distanceTo(location)
                val author = User("defaultUid", "defaultDisplayName")
                Log.i("PlacesFirebaseCacheInfoService", "CacheInfo: $locationName, $distance, $author")
                infoHolder = CacheInfo(
                    id = id ?: "DefaultID",
                    name = name,
                    description = description,
                    coordinates = location,
                    location = locationName,
                    distance = distance,
                    author = auth.currentUser.value ?: author
                )
            }
            .addOnFailureListener { exception: Exception ->
                Log.e("PlacesFirebaseCacheInfoService", "Error finding current place", exception)
            }
        Log.d("PlacesFirebaseCacheInfoService", "Returning successful infoHolder: $infoHolder")
        return infoHolder
    }

    suspend fun getPlaceId(locationLatLng: LatLng): String {
        return withContext(Dispatchers.IO) {
            val context = GeoApiContext.Builder()
                .apiKey(BuildConfig.MAPS_API_KEY)
                .build()

            try {
                val results = GeocodingApi.geocode(context, locationLatLng.latitude.toString() + "," + locationLatLng.longitude.toString()).await()
                if (results.isNotEmpty()) {
                    val placeId = results[0].placeId
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    Log.d("PlacesFirebaseCacheInfoService", "Geocoding results: ${gson.toJson(results)}")
                    return@withContext placeId
                } else {
                    Log.d("PlacesFirebaseCacheInfoService", "No results found for the given coordinates.")
                }
            } catch (e: Exception) {
                Log.e("PlacesFirebaseCacheInfoService", "Error fetching place details: ${e.message}")
            } finally {
                // Invoke .shutdown() after your application is done making requests
                context.shutdown()
            }

            return@withContext ""
        }
    }


    override fun Location.getLocationName(): String {
       return ""
    }*/
    private suspend fun getPlaceId(locationLatLng: LatLng): String {
        return withContext(Dispatchers.IO) {
            val context = GeoApiContext.Builder()
                .apiKey(BuildConfig.MAPS_API_KEY)
                .build()

            try {
                val results = GeocodingApi.geocode(context, locationLatLng.latitude.toString() + "," + locationLatLng.longitude.toString()).await()
                if (results.isNotEmpty()) {
                    val placeId = results[0].placeId
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    Log.d("PlacesFirebaseCacheInfoService", "Geocoding results: ${gson.toJson(results)}")
                    return@withContext placeId
                } else {
                    Log.d("PlacesFirebaseCacheInfoService", "No results found for the given coordinates.")
                }
            } catch (e: Exception) {
                Log.e("PlacesFirebaseCacheInfoService", "Error fetching place details: ${e.message}")
            } finally {
                // Invoke .shutdown() after your application is done making requests
                context.shutdown()
            }

            return@withContext ""
        }
    }



    override suspend fun Cache.toCacheInfo(currentLocation: Location): Outcome<CacheInfo> = withContext(Dispatchers.IO) {
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
            return@withContext Outcome.Failure(IllegalStateException("No location permissions"))
        }

        val latitude = location.latitude
        val longitude = location.longitude

        val latLng = LatLng(latitude, longitude)

        Log.d("PlacesFirebaseCacheInfoService", "Latitude: $latitude, Longitude: $longitude")

        val placeId = runBlocking {
            getPlaceId(latLng)
        }



        val placeFields = listOf(Place.Field.ID, Place.Field.NAME)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        return@withContext placesClient.fetchPlace(request).awaitOutcome().map {
            val place = it.place
            Log.i("PlacesFirebaseCacheInfoService", "Place found: ${place.name}")
            val locationName = place.name.orEmpty()
            Log.d("PlacesFirebaseCacheInfoService", "Current Location: $currentLocation, Location: $location")
            val distance = currentLocation.distanceTo(location)
            val user = userRepository.users.value?.first { user -> user.uid == authorUid } ?: User("DefaultUID", "DefaultEmail")
            //val author = User("defaultUid", "defaultDisplayName")
            Log.i("PlacesFirebaseCacheInfoService", "CacheInfo: $locationName, Distance = $distance, Author: $user")
            CacheInfo(
                id = id ?: "DefaultID",
                name = name,
                description = description,
                coordinates = location,
                location = locationName,
                distance = distance,
                author = user
            )
        }
    }

    override suspend fun Location.getLocationName(): Outcome<String> = withContext(Dispatchers.IO) {
        // TODO: Fix this
        return@withContext Outcome.Success("")
    }
}