package com.umc.jetsetgo.presentation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.umc.jetsetgo.presentation.base.BaseActivity
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    override fun initView() {

    }

    override fun initObserver() {

    }

}