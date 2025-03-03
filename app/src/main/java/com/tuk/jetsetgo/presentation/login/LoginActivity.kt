package com.tuk.jetsetgo.presentation.login

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.ActivityLoginBinding
import com.tuk.jetsetgo.domain.model.request.login.SignUpRequestModel
import com.tuk.jetsetgo.presentation.MainActivity
import com.tuk.jetsetgo.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModels()
    override fun initObserver() {
        viewModel.signUpResponse.observe(this) { result ->
            result.onSuccess { response ->
                Log.i(TAG, "회원가입 성공: ${response.userId}, ${response.name}")
                navigateToMain()
            }
            result.onFailure { exception ->
                Log.e(TAG, "회원가입 실패: ${exception.message}")
            }
        }
    }

    override fun initView() {
        setupKakaoLogin()
    }

    companion object {
        private const val TAG = "KakaoLogin"
    }

    private fun setupKakaoLogin() {
        binding.ivKakaoLogin.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오 계정 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오 계정 로그인 성공: ${token.accessToken}")
//                navigateToMain()
                viewModel.fetchSignUp(SignUpRequestModel(token.accessToken, "KAKAO"))
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카카오톡이 설치되어 있는 경우
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡 로그인 실패", error)

                    // 사용자가 로그인 화면에서 취소한 경우
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 계정이 없으면 카카오 계정 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡 로그인 성공: ${token.accessToken}")
//                    navigateToMain()
                    viewModel.fetchSignUp(SignUpRequestModel(token.accessToken, "KAKAO"))
                }
            }
        } else {
            // 카카오톡이 설치되지 않은 경우, 카카오 계정 로그인 시도
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // LoginActivity 종료
    }
}