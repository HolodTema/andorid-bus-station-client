package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.busstationclient.data.storage.remote.model.RequestError
import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.model.shop.Voyage
import com.terabyte.busstationclient.domain.model.shop.VoyageFilterCriteria
import com.terabyte.busstationclient.domain.usecase.station.LoadAllStationsUseCase
import com.terabyte.busstationclient.domain.usecase.voyage.LoadVoyagesByStationsAndDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val loadAllStationsUseCase: LoadAllStationsUseCase,
    private val loadVoyagesByStationsAndDateUseCase: LoadVoyagesByStationsAndDateUseCase
) : ViewModel() {

    private var listAllStations = emptyList<Station>()

    private val _stateFlowShopScreenState =
        MutableStateFlow<ShopScreenState>(ShopScreenState.Loading)
    val stateFlowShopScreenState = _stateFlowShopScreenState.asStateFlow()

    init {
        loadAllStations()
    }


    fun updateFilterCriteria(filterCriteria: VoyageFilterCriteria) {
        val state = stateFlowShopScreenState.value
        if (state !is ShopScreenState.Idle) {
            return
        }

        _stateFlowShopScreenState.value = state.copy(
            filterCriteria = filterCriteria
        )
    }

    private fun loadAllStations() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadAllStationsUseCase()
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    listAllStations = result.getOrThrow()
                    if (listAllStations.size < 2) {
                        _stateFlowShopScreenState.value = ShopScreenState.NoInternetError
                        return@onSuccess
                    }
                    loadVoyagesByStationsAndDate(
                        listAllStations[0],
                        listAllStations[1],
                        LocalDateTime.now()
                    )
                }
                result.onFailure {
                    when (result.exceptionOrNull()) {
                        is RequestError.UnknownError -> {
                            _stateFlowShopScreenState.value = ShopScreenState.NoInternetError
                        }

                        is RequestError.TokenExpiredError -> {
                            _stateFlowShopScreenState.value = ShopScreenState.TokenExpiredError
                        }

                        else -> {
                            _stateFlowShopScreenState.value = ShopScreenState.NoInternetError
                        }
                    }
                }
            }
        }
    }

    private fun loadVoyagesByStationsAndDate(
        startStation: Station,
        endStation: Station,
        date: LocalDateTime
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadVoyagesByStationsAndDateUseCase(startStation.id, endStation.id, date)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    _stateFlowShopScreenState.value = ShopScreenState.Idle(
                        startStation = startStation,
                        endStation = endStation,
                        date = date,
                        filterCriteria = VoyageFilterCriteria.BY_TIME_OF_DEPARTURE,
                        listVoyages = result.getOrThrow()
                    )
                }
                result.onFailure {
                    when (result.exceptionOrNull()) {
                        is RequestError.UnknownError -> {
                            _stateFlowShopScreenState.value = ShopScreenState.NoInternetError
                        }

                        is RequestError.TokenExpiredError -> {
                            _stateFlowShopScreenState.value = ShopScreenState.TokenExpiredError
                        }

                        else -> {
                            _stateFlowShopScreenState.value = ShopScreenState.NoInternetError
                        }
                    }
                }
            }
        }
    }
}

sealed class ShopScreenState {
    data object Loading : ShopScreenState()

    data object TokenExpiredError : ShopScreenState()

    data object NoInternetError : ShopScreenState()

    data class Idle(
        val startStation: Station,
        val endStation: Station,
        val date: LocalDateTime,
        val filterCriteria: VoyageFilterCriteria = VoyageFilterCriteria.BY_TIME_OF_DEPARTURE,
        val listVoyages: List<Voyage> = emptyList()
    ) : ShopScreenState()
}

