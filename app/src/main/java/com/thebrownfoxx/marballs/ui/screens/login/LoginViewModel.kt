package com.thebrownfoxx.marballs.ui.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thebrownfoxx.marballs.services.auth.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authService: AuthService) : ViewModel() {
    val loggedIn = authService.loggedIn

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
        } else {
            authService.login(email, password) { loginResult ->
                if (!loginResult.isSuccess) {
                    viewModelScope.launch {
                        _errors.emit(
                            loginResult.exceptionOrNull()?.message ?: "Unknown error"
                        )
                    }
                }
                _loading.value = false
            }
        }
    }
}