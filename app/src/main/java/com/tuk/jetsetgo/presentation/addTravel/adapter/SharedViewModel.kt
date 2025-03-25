package com.tuk.jetsetgo.presentation.addTravel.adapter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _isGroup = MutableStateFlow(false)
    val isGroup: StateFlow<Boolean> = _isGroup

    private val _groupSize = MutableStateFlow(0)
    val groupSize: StateFlow<Int> = _groupSize

    private val _travelCityId = MutableStateFlow(0)
    val travelCityId: StateFlow<Int> = _travelCityId

    private val _dailyVisitCount = MutableStateFlow(0)
    val dailyVisitCount: StateFlow<Int> = _dailyVisitCount

    private val _activityStartTime = MutableStateFlow("")
    val activityStartTime: StateFlow<String> = _activityStartTime

    private val _activityEndTime = MutableStateFlow("")
    val activityEndTime: StateFlow<String> = _activityEndTime

    private val _travelStartDate = MutableStateFlow("")
    val travelStartDate: StateFlow<String> = _travelStartDate

    private val _travelEndDate = MutableStateFlow("")
    val travelEndDate: StateFlow<String> = _travelEndDate

    private val _travelPurposeId = MutableStateFlow(0)
    val travelPurposeId: StateFlow<Int> = _travelPurposeId

    private val _travelThemeId = MutableStateFlow(0)
    val travelThemeId: StateFlow<Int> = _travelThemeId

    private val _budget = MutableStateFlow(0)
    val budget: StateFlow<Int> = _budget

    private val _travelSpotName = MutableStateFlow<List<String>>(emptyList())
    val travelSpotName: StateFlow<List<String>> = _travelSpotName

    private val _travelSpotIdList = MutableStateFlow<List<Int>>(emptyList())
    val travelSpotIdList: StateFlow<List<Int>> = _travelSpotIdList

    private val _dailyStartPointList = MutableStateFlow<List<Int>>(emptyList())
    val dailyStartPointList: StateFlow<List<Int>> = _dailyStartPointList

    private val _preferredTransport = MutableStateFlow("")
    val preferredTransport: StateFlow<String> = _preferredTransport

    // 각 입력값을 업데이트하는 setter 함수들
    fun setIsGroup(value: Boolean) { _isGroup.value = value }
    fun setGroupSize(value: Int) { _groupSize.value = value }
    fun setTravelCityId(value: Int) { _travelCityId.value = value }
    fun setDailyVisitCount(value: Int) { _dailyVisitCount.value = value }
    fun setActivityStartTime(value: String) { _activityStartTime.value = value }
    fun setActivityEndTime(value: String) { _activityEndTime.value = value }
    fun setTravelStartDate(value: String) { _travelStartDate.value = value }
    fun setTravelEndDate(value: String) { _travelEndDate.value = value }
    fun setTravelPurposeId(value: Int) { _travelPurposeId.value = value }
    fun setTravelThemeId(value: Int) { _travelThemeId.value = value }
    fun setBudget(value: Int) { _budget.value = value }
    fun setTravelSpotName(value: List<String>) { _travelSpotName.value = value }
    fun setTravelSpotIdList(value: List<Int>) { _travelSpotIdList.value = value }
    fun setDailyStartPointList(value: List<Int>) { _dailyStartPointList.value = value }
    fun setPreferredTransport(value: String) { _preferredTransport.value = value }
}