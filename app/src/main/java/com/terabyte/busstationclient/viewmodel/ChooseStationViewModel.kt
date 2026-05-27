package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.busstationclient.data.storage.remote.model.RequestError
import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.usecase.station.LoadAllStationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ChooseStationViewModel @Inject constructor(
    private val loadAllStationsUseCase: LoadAllStationsUseCase
) : ViewModel() {

    private val _stateFlowScreenState = MutableStateFlow<ChooseStationScreenState>(
        ChooseStationScreenState.Loading)
    val stateFlowScreenState = _stateFlowScreenState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadAllStationsUseCase()
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    _stateFlowScreenState.value = ChooseStationScreenState.Idle(
                        stations = result.getOrThrow()
                    )
                }
                result.onFailure {
                    when (result.exceptionOrNull()) {
                        is RequestError.TokenExpiredError -> {
                            _stateFlowScreenState.value = ChooseStationScreenState.TokenExpiredError
                        }
                        is RequestError.UnknownError -> {
                            _stateFlowScreenState.value = ChooseStationScreenState.NoInternetError
                        }
                        else -> {
                            _stateFlowScreenState.value = ChooseStationScreenState.NoInternetError
                        }
                    }
                }
            }
        }
    }
}

sealed class ChooseStationScreenState {
    data object Loading : ChooseStationScreenState()

    data object TokenExpiredError : ChooseStationScreenState()

    data object NoInternetError : ChooseStationScreenState()

    data class Idle(
        val stations: List<Station>
    ) : ChooseStationScreenState()
}