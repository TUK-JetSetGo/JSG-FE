package com.tuk.jetsetgo.util.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val spf: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = spf.getString("jwt", "") ?: "" // null 체크 및 기본값 설정

        val requestBuilder = chain.request().newBuilder()

        // 토큰이 비어있지 않을 때만 Authorization 헤더 추가
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}