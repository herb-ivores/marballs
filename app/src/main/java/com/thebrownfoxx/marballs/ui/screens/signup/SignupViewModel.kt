package com.thebrownfoxx.marballs.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thebrownfoxx.marballs.domain.Outcome
import com.thebrownfoxx.marballs.services.authentication.Authentication
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val authentication: Authentication) : ViewModel() {
    val loggedIn = authentication.loggedIn

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

        viewModelScope.launch {
            when (val signupOutcome = authentication.signup(email, password)) {
                is Outcome.Success -> {
                    val loginOutcome = authentication.login(email, password)
                    if (loginOutcome is Outcome.Failure) {
                        _errors.emit(loginOutcome.throwableMessage)
                    }
                }
                is Outcome.Failure -> {
                    _errors.emit(signupOutcome.throwableMessage)
                }
            }
            _loading.value = false
        }
    }
}