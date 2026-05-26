package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.busstationclient.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _stateFlowSettingsScreenState = MutableStateFlow(SettingsScreenState())
    val stateFlowSettingsScreenState = _stateFlowSettingsScreenState.asStateFlow()

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
            withContext(Dispatchers.Main) {
                _stateFlowSettingsScreenState.update {
                    it.copy(
                        logoutSuccess = true
                    )
                }
            }
        }
    }
}

data class SettingsScreenState(
    val logoutSuccess: Boolean = false
)
