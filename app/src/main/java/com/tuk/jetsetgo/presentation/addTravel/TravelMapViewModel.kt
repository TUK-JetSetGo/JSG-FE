package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.response.addTravel.SpotInfoResponseModel
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelMapViewModel @Inject constructor(
    private val repository: AddTravelRepository
): ViewModel() {
    private val _searchResults = MutableLiveData<Result<SpotInfoResponseModel>>()
    val searchResults: LiveData<Result<SpotInfoResponseModel>> get() = _searchResults

    fun fetchSearchSpots(keyword: String) {
        viewModelScope.launch {
            repository.fetchSearchSpots(
                keyword = keyword,
                category = null, // 필요 없으므로 null 설정
                page = null,       // 기본값 설정 (첫 번째 페이지)
                size = null,      // 기본 사이즈 설정 (필요하면 변경 가능)
                sort = null   // 기본 정렬 방식 (API 요구사항에 맞춰 설정)
            )
            .onSuccess { response ->
                _searchResults.postValue(Result.success(response))
            }.onFailure { exception ->
                Log.e("TravelMapViewModel", "검색 API 호출 실패: ${exception.message}")
                _searchResults.postValue(Result.failure(exception))
            }
        }
    }
}