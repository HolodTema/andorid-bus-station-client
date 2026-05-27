package com.terabyte.busstationclient.viewmodel

import androidx.lifecycle.ViewModel
import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.model.shop.Voyage
import com.terabyte.busstationclient.domain.model.shop.VoyageFilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(

) : ViewModel() {
    private val _stateFlowShopScreenState = MutableStateFlow<ShopScreenState>(ShopScreenState.Loading)
    val stateFlowShopScreenState = _stateFlowShopScreenState.asStateFlow()

    fun updateFilterCriteria(filterCriteria: VoyageFilterCriteria) {
        val state = stateFlowShopScreenState.value
        if (state !is ShopScreenState.Idle) {
            return
        }

        _stateFlowShopScreenState.value = state.copy(
            filterCriteria = filterCriteria
        )
    }
}

sealed class ShopScreenState {
    data object Loading : ShopScreenState()

    data class Idle(
        val isLoading: Boolean = false,
        val stationFrom: Station,
        val stationTo: Station,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
        val filterCriteria: VoyageFilterCriteria = VoyageFilterCriteria.BY_TIME_OF_DEPARTURE,
        val listVoyages: List<Voyage> = emptyList()
    ) : ShopScreenState()
}

