package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.busstationclient.domain.model.auth.LoginCredentialsModel
import com.terabyte.busstationclient.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    init {

    }

    private val _stateFlowLoginScreenState = MutableStateFlow(LoginScreenState())
    val stateFlowLoginScreenState = _stateFlowLoginScreenState.asStateFlow()

    fun updateEmail(email: String) {
        _stateFlowLoginScreenState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _stateFlowLoginScreenState.update {
            it.copy(
                password = password
            )
        }
    }

    fun login() {
        _stateFlowLoginScreenState.update {
            it.copy(
                isLoading = true,
                isLoginError = false
            )
        }

        val loginCredentialsModel = LoginCredentialsModel(
            email = stateFlowLoginScreenState.value.email.trim(),
            password = stateFlowLoginScreenState.value.password.trim(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isLoginSuccess = loginUseCase(loginCredentialsModel)
            withContext(Dispatchers.Main) {
                if (isLoginSuccess) {
                    _stateFlowLoginScreenState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true
                        )
                    }
                }
                else {
                    _stateFlowLoginScreenState.update {
                        it.copy(
                            isLoading = false,
                            isLoginError = true
                        )
                    }
                }
            }
        }
    }
}

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val loginSuccess: Boolean = false,
    val isLoginError: Boolean = false,
    val isLoading: Boolean = false
)
