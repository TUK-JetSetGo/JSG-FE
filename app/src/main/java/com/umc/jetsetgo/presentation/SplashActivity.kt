package com.umc.jetsetgo.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.ActivitySplashBinding
import com.umc.jetsetgo.presentation.base.BaseActivity
import com.umc.jetsetgo.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun initObserver() {

    }

    override fun initView() {
        lifecycleScope.launch {
            delay(2000) // 2초 대기
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // SplashActivity 종료
        }
    }

}