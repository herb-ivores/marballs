package com.thebrownfoxx.marballs

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thebrownfoxx.marballs.services.auth.FirebaseAuthService

class MarballsApplication: Application() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var _authService: FirebaseAuthService
    val authService get() = _authService

    override fun onCreate() {
        super.onCreate()
        firebaseAuth = Firebase.auth
        _authService = FirebaseAuthService(firebaseAuth)
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarballsApplication)