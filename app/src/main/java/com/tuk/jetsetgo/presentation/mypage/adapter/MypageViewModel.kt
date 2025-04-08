package com.tuk.jetsetgo.presentation.mypage.adapter

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.mypage.PatchUserRequestModel
import com.tuk.jetsetgo.domain.model.response.mypage.GetUserResponseModel
import com.tuk.jetsetgo.domain.repository.mypage.MypageRepository
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val spf: SharedPreferences,
    private val mypageRepository: MypageRepository
) : ViewModel() {

    private val _getUserState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val getUserState: StateFlow<UiState<Boolean>> = _getUserState.asStateFlow()

    private val _patchUserState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val patchUserState: StateFlow<UiState<Boolean>> = _patchUserState.asStateFlow()

    private val _userInfo = MutableStateFlow<GetUserResponseModel?>(null)
    val userInfo: StateFlow<GetUserResponseModel?> = _userInfo.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            _getUserState.value = UiState.Loading
            mypageRepository.getUser()
                .onSuccess { response ->
                    Log.d("MypageViewModel", "getUser() 성공")
                    _userInfo.value = response // 유저 정보 저장
                    _getUserState.value = UiState.Success(true)
                }
                .onFailure { exception ->
                    Log.e("MypageViewModel", "getUser() 실패: ${exception.message}")
                    _getUserState.value = UiState.Error(exception)
                }
        }
    }


    fun patchUser(request:PatchUserRequestModel) {
        viewModelScope.launch {
            _patchUserState.value = UiState.Loading
            mypageRepository.patchUser(request)
                .onSuccess { response ->
                    Log.d("MypageViewModel", "patchUser() 성공")
                }
                .onFailure { exception ->
                    Log.e("MypageViewModel", "patchUser() 실패: ${exception.message}")
                    _patchUserState.value = UiState.Error(exception)
                }
        }
    }



}