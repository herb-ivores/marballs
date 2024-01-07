package com.thebrownfoxx.marballs.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.marballs.services.auth.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupViewModel(private val authService: AuthService) : ViewModel() {
    val loggedIn = authService.loggedIn

    private val _errors = MutableSharedFlow<String>()
    val errors = _errors.asSharedFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private fun Throwable?.emitError() {
        viewModelScope.launch {
            _errors.emit(this@emitError?.message ?: "Unknown error")
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }

    fun signUp() {
        _loading.value = true

        val email = email.value
        val password = password.value
        val repeatPassword = repeatPassword.value

        if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            viewModelScope.launch {
                _errors.emit("Please fill out all fields")
            }
            _loading.value = false
            return
        }

        authService.signup(email, password) { signupResult ->
            if (signupResult.isSuccess) {
                authService.login(email, password) { loginResult ->
                    if (loginResult.isFailure) {
                        loginResult.exceptionOrNull().emitError()
                    }
                    _loading.value = false
                }
            } else {
                viewModelScope.launch {
                    signupResult.exceptionOrNull().emitError()
                    _loading.value = false
                }
            }
        }
    }
}