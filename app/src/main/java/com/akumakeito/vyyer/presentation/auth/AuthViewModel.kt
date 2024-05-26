package com.akumakeito.vyyer.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akumakeito.vyyer.domain.repository.AuthRepository
import com.akumakeito.vyyer.domain.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    private val _password = MutableStateFlow<String?>(null)

    private val _state = repository.authStateFlow
    val state = _state.asStateFlow()

    private val _isButtonEnable = MutableStateFlow<Boolean>(false)
    val isButtonEnable = _isButtonEnable.asStateFlow()

    fun login(username : String, password : String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            val result = repository.login(username, password)
            _state.value = result

        }
    }

    fun onUsernameChanged(username : String) {
        _username.value = username
        checkButtonEnable()
    }

    fun onPasswordChanged(password : String) {
        _password.value = password
        checkButtonEnable()
    }

    fun clearCred() {
        _username.value = null
        _password.value = null
    }

   private fun checkButtonEnable() {
        _isButtonEnable.value = !_username.value.isNullOrBlank() && !_password.value.isNullOrBlank()
    }






}