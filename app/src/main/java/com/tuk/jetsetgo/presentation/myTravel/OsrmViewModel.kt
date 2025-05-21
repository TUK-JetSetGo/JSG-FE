package com.tuk.jetsetgo.presentation.myTravel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.data.dto.response.myTravel.OsrmResponseDto
import com.tuk.jetsetgo.domain.repository.myTravel.OsrmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OsrmViewModel @Inject constructor(
    private val osrmRepository: OsrmRepository
): ViewModel() {
    private val _routeResult = MutableLiveData<Result<OsrmResponseDto>>()
    val routeResult: LiveData<Result<OsrmResponseDto>> = _routeResult

    fun loadRoute(
        coords: String,
        overview: String = "full",
        geometries: String = "polyline"
    ) {
        viewModelScope.launch {
            // Repository가 Result<OsrmResponseDto>를 반환한다고 가정
            val result = osrmRepository.getRoute(coords, overview, geometries)
            _routeResult.value = result
        }
    }
}