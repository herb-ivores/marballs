package com.thebrownfoxx.marballs.ui.screens.addcache

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.marballs.domain.Cache
import com.thebrownfoxx.marballs.domain.Location
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.authentication.Authentication
import com.thebrownfoxx.marballs.services.cacheinfo.CacheInfoProvider
import com.thebrownfoxx.marballs.services.caches.CacheRepository
import com.thebrownfoxx.marballs.services.location.LocationProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCacheViewModel(
    private val authentication: Authentication,
    private val locationProvider: LocationProvider,
    private val cacheInfoProvider: CacheInfoProvider,
    private val cacheRepository: CacheRepository,
) : ViewModel() {
    val loggedIn = authentication.loggedIn

    private val _successful = MutableSharedFlow<Unit>()
    val successful = _successful.asSharedFlow()

    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    val location = locationProvider.currentLocation

    private val _locationName = MutableStateFlow("")
    val locationName = _locationName.asStateFlow()

    init {
        viewModelScope.launch {
            locationProvider.currentLocation.collect {
                val outcome = with(cacheInfoProvider) {
                    it?.getLocationName()
                }
                if (outcome is Outcome.Success && outcome.data != "") {
                    _locationName.value = outcome.data
                }
            }
        }
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setName(name: String) {
        _name.value = name
    }

    fun setDescription(description: String) {
        _description.value = description
    }

    fun resetLocation() {
        viewModelScope.launch {
            locationProvider.updateLocation()
        }
    }

    fun add(location: Location) {
        _loading.value = true

        val name = name.value
        val description = description.value

        if (name.isBlank() || description.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            _loading.value = false
            return
        }

        val cache = Cache(
            name = name,
            description = description,
            location = location,
            authorUid = authentication.currentUser.value?.uid ?: "",
        )

        viewModelScope.launch {
            when (val outcome = cacheRepository.addCache(cache)) {
                is Outcome.Success -> {
                    viewModelScope.launch {
                        _successful.emit(Unit)
                    }
                }

                is Outcome.Failure -> {
                    viewModelScope.launch {
                        _errors.emit(outcome.throwableMessage)
                    }
                }
            }
            _loading.value = false
        }
    }
}