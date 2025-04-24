package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.myTravel.ExpenseRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.MyPlanResponseModel
import com.tuk.jetsetgo.domain.model.response.myTravel.PlanInfoResponseModel
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
    private val repository: MyTravelRepository
): ViewModel() {
    private val _myTravelList = MutableStateFlow<List<MyPlanResponseModel.MyTravelPlanInfoListModel>>(emptyList())
    val myTravelList: StateFlow<List<MyPlanResponseModel.MyTravelPlanInfoListModel>> = _myTravelList

    private val _travelPlanId = MutableLiveData<Int>()
    val travelPlanId: LiveData<Int> = _travelPlanId

    private val _travelPlan = MutableLiveData<PlanInfoResponseModel>()
    val travelPlan: LiveData<PlanInfoResponseModel> = _travelPlan

    private val _currentDayIndex = MutableLiveData<Int>(1)
    val currentDayIndex: LiveData<Int> = _currentDayIndex

    private val _saveExpenseResult = MutableLiveData<Result<String>>()
    val saveExpenseResult: LiveData<Result<String>> = _saveExpenseResult

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

    fun convertToScheduleData(response: PlanInfoResponseModel): List<ScheduleData> {
        return response.itineraryInfo?.routeInfoList?.map { route ->
            val spot = route.touristSpotInfo
            ScheduleData(
                title = spot.name,
                totalTime = getDurationText(route.visitStartTime, route.visitEndTime),
                startTime = route.visitStartTime,
                endTime = route.visitEndTime,
                latitude = spot.latitude,
                longitude = spot.longitude
            )
        } ?: emptyList()
    }

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

    fun saveExpense(request: ExpenseRequestModel) {
        viewModelScope.launch {
            repository.fetchSaveExpense(request)
                .onSuccess { result ->
                    Log.d("MyTravelViewModel", "지출 저장 성공: $result")
                    _saveExpenseResult.value = Result.success(result)
                }
                .onFailure { exception ->
                    Log.e("MyTravelViewModel", "지출 저장 실패", exception)
                    _saveExpenseResult.value = Result.failure(exception)
                }
        }
    }
}