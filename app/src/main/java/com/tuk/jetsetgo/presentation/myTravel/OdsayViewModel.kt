package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.BuildConfig
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import com.tuk.jetsetgo.data.service.myTravel.OdsayService
import com.tuk.jetsetgo.domain.repository.myTravel.OdsayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class OdsayViewModel @Inject constructor(
    private val odsayRepository: OdsayRepository
) : ViewModel() {
    private val _routeResult = MutableLiveData<Result<OdsayRouteDto>>()
    val routeResult: LiveData<Result<OdsayRouteDto>> = _routeResult

    fun loadRoute(
        sx: Double,
        sy: Double,
        ex: Double,
        ey: Double,
        opt: Int = 0,
        lang: Int = 0
    ) {
        viewModelScope.launch {
            val result = odsayRepository.searchTransport(
                sx = sx,
                sy = sy,
                ex = ex,
                ey = ey,
                opt = opt,
                lang = lang,
                apiKey = BuildConfig.ODSAY_API_KEY
            )

            Log.d("OdsayViewModel", "ODsay 경로 요청 결과: $result")
            _routeResult.value = result
        }
    }
}