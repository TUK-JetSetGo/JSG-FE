package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor(
    //private val repository: MyTravelRepository
):ViewModel() {

    private val _presetList = MutableLiveData<List<String>>()
    val presetList: LiveData<List<String>> get() = _presetList

    private val _checklistItems = MutableLiveData<List<String>>()
    val checklistItems: LiveData<List<String>> get() = _checklistItems

    private val dummyChecklistMap = mapOf(
        "바다" to listOf("수영복", "썬크림", "선글라스", "비치타월", "슬리퍼"),
        "산행" to listOf("등산화", "모자", "물병", "간식", "우비", "지도"),
        "캠핑" to listOf("텐트", "침낭", "버너", "랜턴", "모기약", "접이식 의자"),
        "해외" to listOf("여권", "환전", "멀티 어댑터", "세면도구", "복대", "비상약")
    )

    init {
        _presetList.value = dummyChecklistMap.keys.toList()
        onPresetSelected("바다 여행") // ✅ 기본 프리셋
        Log.d("ChecklistViewModel", "초기 프리셋 설정 완료")
    }


    fun onPresetSelected(preset: String) {
        Log.d("ChecklistViewModel", "onPresetSelected 호출됨: $preset") // ✅ 프리셋에 따른 로딩 확인
        val data = dummyChecklistMap[preset].orEmpty()
        Log.d("ChecklistViewModel", "불러온 준비물 목록: $data") // ✅ 데이터 비어있는지 확인
        _checklistItems.value = data
    }


    fun addItem(item: String) {
        val updatedList = _checklistItems.value.orEmpty().toMutableList().apply { add(item) }
        _checklistItems.value = updatedList
    }
}
