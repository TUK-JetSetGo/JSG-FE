package com.tuk.jetsetgo.presentation.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.login.SignUpRequestModel
import com.tuk.jetsetgo.domain.model.response.login.SignUpResponseModel
import com.tuk.jetsetgo.domain.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _signUpResponse = MutableLiveData<Result<SignUpResponseModel>>()
    val signUpResponse: LiveData<Result<SignUpResponseModel>> get() = _signUpResponse

    fun fetchSignUp(request: SignUpRequestModel) {
        viewModelScope.launch {
            loginRepository.fetchSignUp(request)
                .onSuccess { response ->
                    _signUpResponse.value = Result.success(response) // 성공한 값 저장

                    sharedPreferences.edit().putString("jwt", response.token).apply()
                    Log.d("LoginViewModel", "토큰 저장 완료: ${response.token}")
                }
                .onFailure { exception ->
                    _signUpResponse.value = Result.failure(exception) // 실패한 값 저장
                    Log.e("LoginViewModel", "회원가입 API 호출 실패", exception)
                }
        }
    }
}