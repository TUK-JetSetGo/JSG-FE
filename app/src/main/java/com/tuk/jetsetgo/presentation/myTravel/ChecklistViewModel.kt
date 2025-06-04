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

    private val _checklistItems = MutableLiveData<List<String>>()
    val checklistItems: LiveData<List<String>> get() = _checklistItems

    init {
        _checklistItems.value = emptyList() // 빈 리스트로 시작
    }


    fun addItem(item: String) {
        val updatedList = _checklistItems.value.orEmpty().toMutableList().apply { add(item) }
        _checklistItems.value = updatedList
    }
}
