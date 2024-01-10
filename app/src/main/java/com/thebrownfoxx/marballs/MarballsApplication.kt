package com.thebrownfoxx.marballs

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.authentication.DummyAuthentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.cacheinfo.DummyCacheInfoProvider
import com.thebrownfoxx.marballs.services.cacheinfo.PlacesFirebaseCacheInfoService
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.caches.DummyCacheRepository
import com.thebrownfoxx.marballs.services.map.DummyLocationProvider
import com.thebrownfoxx.marballs.services.map.LocationProvider

class MarballsApplication: Application() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var _authentication: Authentication
    val authentication get() = _authentication

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var _locationProvider: LocationProvider
    val locationProvider get() = _locationProvider

    private lateinit var firestore: FirebaseFirestore
    private lateinit var _cacheRepository: CacheRepository
    val cacheRepository get() = _cacheRepository

    private lateinit var placesClient: PlacesClient
    private lateinit var _cacheInfoProvider: CacheInfoProvider
    val cacheInfoProvider get() = _cacheInfoProvider

    override fun onCreate() {
        super.onCreate()
        firebaseAuth = Firebase.auth
//        _authService = FirebaseAuthentication(firebaseAuth)
        _authentication = DummyAuthentication()

        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(applicationContext)
//        _locationProvider = GoogleLocationProvider(
//            fusedLocationClient = fusedLocationProviderClient,
//            application = this,
//        )
        _locationProvider = DummyLocationProvider()

        firestore = FirebaseFirestore.getInstance()
//        _cacheRepository = FirestoreCacheRepository(firestore)
        _cacheRepository = DummyCacheRepository()

        Places.initializeWithNewPlacesApiEnabled(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(applicationContext)
        _cacheInfoProvider = DummyCacheInfoProvider()
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarballsApplication)