package com.thebrownfoxx.marballs

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thebrownfoxx.marballs.services.authentication.FirebaseAuthentication
import com.thebrownfoxx.marballs.services.map.GoogleMap

class MarballsApplication: Application() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var _authService: FirebaseAuthentication
    val authService get() = _authService

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var _mapService: GoogleMap
    val mapService get() = _mapService

    override fun onCreate() {
        super.onCreate()
        firebaseAuth = Firebase.auth
        _authService = FirebaseAuthentication(firebaseAuth)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        _mapService = GoogleMap(
            fusedLocationClient = fusedLocationProviderClient,
            application = this,
        )
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarballsApplication)