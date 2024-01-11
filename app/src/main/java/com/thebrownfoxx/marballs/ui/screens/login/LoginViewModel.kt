package com.thebrownfoxx.marballs.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.authentication.Authentication
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authentication: Authentication) : ViewModel() {
    val loggedIn = authentication.loggedIn

    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        _loading.value = true
        val email = email.value
        val password = password.value

        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            _loading.value = false
            return
        }

        viewModelScope.launch {
            val outcome = authentication.login(email, password)
            if (outcome is Outcome.Failure) {
                _errors.emit(outcome.throwableMessage)
            }
            _loading.value = false
        }
    }
}