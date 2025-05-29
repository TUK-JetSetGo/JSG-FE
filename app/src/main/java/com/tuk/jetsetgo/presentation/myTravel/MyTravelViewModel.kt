package com.tuk.jetsetgo.presentation.myTravel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.addTravel.EditPlanRequestModel
import com.tuk.jetsetgo.domain.model.request.mypage.RecommendAltRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.RecommendAltResponseModel
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import com.tuk.jetsetgo.domain.repository.myTravel.MyTravelRepository
import com.tuk.jetsetgo.presentation.myTravel.adapter.ScheduleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MyTravelViewModel @Inject constructor(
    private val repository: MyTravelRepository,
    private val addTravelRepository: AddTravelRepository
): ViewModel() {
    private val _myTravelList = MutableStateFlow<List<MyPlanResponseModel.MyTravelPlanInfoListModel>>(emptyList())
    val myTravelList: StateFlow<List<MyPlanResponseModel.MyTravelPlanInfoListModel>> = _myTravelList

    private val _travelPlanId = MutableLiveData<Int>()
    val travelPlanId: LiveData<Int> = _travelPlanId

    private val _travelPlan = MutableLiveData<PlanInfoResponseModel>()
    val travelPlan: LiveData<PlanInfoResponseModel> = _travelPlan

    private val _currentDayIndex = MutableLiveData<Int>(1)
    val currentDayIndex: LiveData<Int> = _currentDayIndex

    private val _editResult = MutableLiveData<Result<String>>()
    val editResult: LiveData<Result<String>> get() = _editResult

    private val _recommendResult = MutableLiveData<RecommendAltResponseModel>()
    val recommendResult: LiveData<RecommendAltResponseModel> get() = _recommendResult

    fun fetchMyTravelList() {
        viewModelScope.launch {
            repository.fetchMyTravelList().onSuccess { result ->
                Log.d("MyTravelViewModel", "여행 목록 불러오기 성공: ${result.myTravelPlanInfoList}")
                _myTravelList.value = result.myTravelPlanInfoList
            }.onFailure { exception ->
                Log.e("MyTravelViewModel", "여행 목록 불러오기 실패", exception)
            }
        }
    }

    fun setTravelPlanId(id: Int) {
        Log.d("MyTravelViewModel", "받아온 TravelPlanId = $id")
        _travelPlanId.value = id
    }

    fun fetchTravelPlan(travelPlanId: Int, dayIndex: Int) {
        viewModelScope.launch {
            repository.fetchTravelPlan(travelPlanId, dayIndex)
                .onSuccess {
                    _travelPlan.value = it
                }
                .onFailure {
                    Log.e("MyTravelViewModel", "여행 계획 실패: ${it.message}")
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToScheduleData(response: PlanInfoResponseModel): List<ScheduleData> {
        return response.itineraryInfo?.routeInfoList?.map { route ->
            val spot = route.touristSpotInfo
            ScheduleData(
                routeId = route.routeId,
                touristSpotId = spot.touristSpotId,
                title = spot.name,
                totalTime = getDurationText(route.visitStartTime, route.visitEndTime),
                startTime = route.visitStartTime,
                endTime = route.visitEndTime,
                orderIndex = route.orderIndex,
                latitude = spot.latitude,
                longitude = spot.longitude
            )
        } ?: emptyList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDurationText(start: String, end: String): String {
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val startTime = LocalDateTime.parse(start, formatter)
            val endTime = LocalDateTime.parse(end, formatter)
            val duration = Duration.between(startTime, endTime).toMinutes()
            "${duration}분"
        } catch (e: Exception) {
            "시간 없음"
        }
    }

    fun setCurrentDayIndex(index: Int) {
        _currentDayIndex.value = index
    }

    fun fetchEditPlan(itineraryId: Int, request: EditPlanRequestModel, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            addTravelRepository.fetchEditPlan(itineraryId, request)
                .onSuccess {
                    Log.d("MyTravelViewModel", "일정 수정 성공: $it")
                    _editResult.postValue(Result.success(it))
                    onSuccess()
                }
                .onFailure { e ->
                    Log.e("MyTravelViewModel", "일정 수정 실패: ${e.message}")
                    _editResult.postValue(Result.failure(e))
                }
        }
    }

    fun postAlternativeRecommend(request: RecommendAltRequestModel) {
        viewModelScope.launch {
            repository.postAlternativesRecommend(request)
                .onSuccess {
                    Log.d("MyTravelViewModel", "대체 여행지 추천 성공: $it")
                    _recommendResult.value = it
                }
                .onFailure { e ->
                    Log.e("MyTravelViewModel", "대체 여행지 추천 실패: ${e.message}")
                }
        }
    }
}
