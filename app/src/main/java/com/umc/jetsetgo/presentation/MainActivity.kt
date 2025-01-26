package com.umc.jetsetgo.presentation

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.umc.jetsetgo.presentation.base.BaseActivity
import com.umc.jetsetgo.R
import com.umc.jetsetgo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    override fun initView() {
        initNavigator()
    }

    override fun initObserver() {

    }

    private fun initNavigator() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.mainBnv.setupWithNavController(navController)

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addTravelTab -> {
                    // 스택 초기화 후 첫 화면으로 이동
                    navController.popBackStack(navController.graph.startDestinationId, false) // 이전 스택 제거
                    navController.navigate(R.id.addTravelTab) // 첫 화면으로 이동
                    true
                }
                else -> {
                    // 기본 네비게이션 동작
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }
    }

}