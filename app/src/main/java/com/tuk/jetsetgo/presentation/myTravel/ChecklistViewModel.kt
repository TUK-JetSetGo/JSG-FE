package com.tuk.jetsetgo.presentation.myTravel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuk.jetsetgo.domain.repository.myTravel.MyTravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor(
    //private val repository: MyTravelRepository
):ViewModel() {

    // 프리셋 리스트 (예: 바다, 산, 캠핑 등)
    private val _presetList = MutableLiveData<List<String>>()
    val presetList: LiveData<List<String>> get() = _presetList

    // 선택된 프리셋의 준비물 목록
    private val _checklistItems = MutableLiveData<List<String>>()
    val checklistItems: LiveData<List<String>> get() = _checklistItems

    init {
        _presetList.value = listOf("바다 여행", "산행", "캠핑")
        _checklistItems.value = listOf("썬크림", "수건", "슬리퍼") // 기본 더미
    }

    fun onPresetSelected(preset: String) {
        _checklistItems.value = when (preset) {
            "산행" -> listOf("등산화", "물", "지팡이")
            "캠핑" -> listOf("텐트", "버너", "침낭")
            else -> listOf("썬크림", "수건", "슬리퍼")
        }
    }

    fun addItem(item: String) {
        val updatedList = _checklistItems.value.orEmpty().toMutableList().apply { add(item) }
        _checklistItems.value = updatedList
    }
}
