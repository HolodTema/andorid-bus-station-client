package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.busstationclient.domain.model.auth.RegisterCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.RegistrationError
import com.terabyte.busstationclient.domain.usecase.auth.RegisterUseCase
import com.terabyte.busstationclient.domain.usecase.auth.ValidateEmailUseCase
import com.terabyte.busstationclient.domain.usecase.auth.ValidatePasswordUseCase
import com.terabyte.busstationclient.domain.usecase.auth.ValidateUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateUsernameUseCase: ValidateUsernameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    private val _stateFlowRegisterScreenState = MutableStateFlow(RegistrationScreenState())
    val stateFlowRegisterScreenState = _stateFlowRegisterScreenState.asStateFlow()

    fun updateName(name: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                name = name
            )
        }
    }

    fun updateSurname(surname: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                surname = surname
            )
        }
    }

    fun updateEmail(email: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                email = email
            )
        }
    }

    fun updatePassword(password: String) {
        _stateFlowRegisterScreenState.update {
            it.copy(
                password = password
            )
        }
    }

    fun register() {
        val name = stateFlowRegisterScreenState.value.name.trim()
        val isNameValid = validateUsernameUseCase(name)

        val surname = stateFlowRegisterScreenState.value.surname.trim()
        val isSurnameValid = validateUsernameUseCase(surname)

        val email = stateFlowRegisterScreenState.value.email.trim()
        val isEmailValid = validateEmailUseCase(email)

        val password = stateFlowRegisterScreenState.value.password.trim()
        val isPasswordValid = validatePasswordUseCase(password)

        if (!(isPasswordValid && isNameValid && isSurnameValid && isEmailValid)) {
            _stateFlowRegisterScreenState.update {
                it.copy(
                    isErrorNameValidation = !isNameValid,
                    isErrorSurnameValidation = !isSurnameValid,
                    isErrorEmailValidation = !isEmailValid,
                    isErrorPasswordValidation = !isPasswordValid
                )
            }
            return
        }

        _stateFlowRegisterScreenState.update {
            it.copy(
                isLoading = true,
                isErrorUnableToRegister = false,
                isErrorNameValidation = false,
                isErrorSurnameValidation = false,
                isErrorEmailBusy = false,
                isErrorEmailValidation = false,
                isErrorPasswordValidation = false,
            )
        }

        val registerCredentialsModel = RegisterCredentialsModel(
            name = name,
            surname = surname,
            email = email,
            password = password
        )
        viewModelScope.launch(Dispatchers.IO) {
            val registrationError = registerUseCase(registerCredentialsModel)
            withContext(Dispatchers.Main) {
                if (registrationError == null) {
                    //successful registration
                    _stateFlowRegisterScreenState.update {
                        it.copy(
                            isLoading = false,
                            registrationSuccess = true
                        )
                    }
                } else {
                    //some error happened
                    when (registrationError) {
                        is RegistrationError.EmailBusy -> {
                            _stateFlowRegisterScreenState.update {
                                it.copy(
                                    isLoading = false,
                                    isErrorUnableToRegister = false,
                                    isErrorEmailBusy = true,
                                )
                            }
                        }

                        is RegistrationError.UnknownError -> {
                            _stateFlowRegisterScreenState.update {
                                it.copy(
                                    isLoading = false,
                                    isErrorUnableToRegister = true,
                                    isErrorEmailBusy = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

data class RegistrationScreenState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val registrationSuccess: Boolean = false,
    val isErrorEmailBusy: Boolean = false,
    val isErrorUnableToRegister: Boolean = false,
    val isErrorNameValidation: Boolean = false,
    val isErrorSurnameValidation: Boolean = false,
    val isErrorEmailValidation: Boolean = false,
    val isErrorPasswordValidation: Boolean = false,
    val isLoading: Boolean = false
)