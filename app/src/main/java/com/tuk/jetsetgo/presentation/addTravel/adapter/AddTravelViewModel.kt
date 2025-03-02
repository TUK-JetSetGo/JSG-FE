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
                .onSuccess {
                    Log.d("AddTravelViewModel", "createTravel() 성공")
                    _createTravelState.value = UiState.Success(true)
                }
                .onFailure { exception ->
                    Log.e("AddTravelViewModel", "createTravel() 실패: ${exception.message}\n${exception.stackTraceToString()}")
                    _createTravelState.value = UiState.Error(exception)
                }
        }
    }
}