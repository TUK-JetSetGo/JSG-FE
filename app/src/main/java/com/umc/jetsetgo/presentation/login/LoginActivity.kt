package com.umc.jetsetgo.presentation.login

import android.content.Intent
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.ActivityLoginBinding
import com.umc.jetsetgo.presentation.MainActivity
import com.umc.jetsetgo.presentation.base.BaseActivity

class LoginActivity: BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun initObserver() {

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
                navigateToMain()
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
                    navigateToMain()
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