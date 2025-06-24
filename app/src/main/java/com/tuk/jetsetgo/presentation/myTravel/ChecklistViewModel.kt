package com.tuk.jetsetgo.presentation.myTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.myTravel.PostCheckListRequestModel
import com.tuk.jetsetgo.domain.model.response.myTravel.GetCheckListResponseModel
import com.tuk.jetsetgo.domain.repository.myTravel.MyTravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor(
    private val repository: MyTravelRepository
):ViewModel() {

    private val _checklistItems = MutableLiveData<List<GetCheckListResponseModel>>()
    val checklistItems: LiveData<List<GetCheckListResponseModel>> get() = _checklistItems

    fun loadChecklist(travelPlanId: Int) {
        viewModelScope.launch {
            repository.getCheckList(travelPlanId)
                .onSuccess {
                    Log.d("ChecklistViewModel", "체크리스트 로딩 성공, 항목 수: ${it.size}")
                    _checklistItems.value = it
                }
                .onFailure {
                    Log.e("ChecklistViewModel", "체크리스트 로딩 실패: ${it.message}")
                }
        }
    }

    fun postChecklistItem(travelPlanId: Int, itemName: String) {
        Log.d("ChecklistViewModel", "📡 POST checklist 호출: [$itemName]")
        val request = PostCheckListRequestModel(itemName)
        viewModelScope.launch {
            repository.postCheckList(travelPlanId, request)
                .onSuccess {
                    Log.d("ChecklistViewModel", "체크리스트 항목 추가 성공: $itemName → $it")
                    // 항목 새로고침
                    loadChecklist(travelPlanId)
                }
                .onFailure {
                    Log.e("ChecklistViewModel", "체크리스트 항목 추가 실패: $itemName", it)
                }
        }
    }

    fun patchCheckItem(checklistId: Int, isChecked: Boolean) {
        Log.d("ChecklistViewModel", "📡 PATCH 체크 상태 변경 요청: id=$checklistId, isChecked=$isChecked")
        viewModelScope.launch {
            repository.patchCheckList(checklistId, isChecked)
                .onSuccess {
                    Log.d("ChecklistViewModel", "체크리스트 상태 변경 성공: id=$checklistId → $isChecked")
                }
                .onFailure {
                    Log.e("ChecklistViewModel", "체크리스트 상태 변경 실패: id=$checklistId", it)
                }
        }
    }

    fun deleteCheckItem(checklistId: Int, travelPlanId: Int) {
        Log.d("ChecklistViewModel", "🗑️ 삭제 요청: checklistId=$checklistId")
        viewModelScope.launch {
            repository.deleteCheckList(checklistId)
                .onSuccess {
                    Log.d("ChecklistViewModel", "체크리스트 아이템 삭제 성공: id=$checklistId → $it")
                    loadChecklist(travelPlanId) // 삭제 후 리스트 재로딩
                }
                .onFailure {
                    Log.e("ChecklistViewModel", "체크리스트 아이템 삭제 실패: id=$checklistId", it)
                }
        }
    }

}
