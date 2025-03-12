package com.tuk.jetsetgo.presentation.addTravel.adapter

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.addTravel.CreatePlanRequestModel
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTravelViewModel @Inject constructor(
    private val spf: SharedPreferences,
    private val addTravelRepository: AddTravelRepository
) : ViewModel() {

    private val _createTravelState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val createTravelState: StateFlow<UiState<Boolean>> = _createTravelState.asStateFlow()

    fun createTravel(request: CreatePlanRequestModel) {
        viewModelScope.launch {
            _createTravelState.value = UiState.Loading
            addTravelRepository.fetchCreatePlan(request)
                .onSuccess { response ->
                    Log.d("AddTravelViewModel", "createTravel() 성공")

                    // response가 null이거나 데이터가 없어도 성공으로 처리
                    if (response != null) {
                        _createTravelState.value = UiState.Success(true)
                    } else {
                        Log.w("AddTravelViewModel", "서버 응답의 data가 null이지만 성공 처리함.")
                        _createTravelState.value = UiState.Success(true)
                    }
                }
                .onFailure { exception ->
                    Log.e("AddTravelViewModel", "createTravel() 실패: ${exception.message}")
                }
        }
    }



}