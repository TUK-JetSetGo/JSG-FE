package com.tuk.jetsetgo.presentation.mypage

import android.app.Dialog
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMypageBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.presentation.mypage.adapter.MypageViewModel
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MypageFragment: BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val mypageViewModel: MypageViewModel by activityViewModels()


    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        getUser()
    }

    private fun setClickListener(){
        binding.clMypageProfile.setOnSingleClickListener {
            findNavController().navigate(R.id.goToProfile)
        }
        binding.tvMypageLogout.setOnClickListener { showLogoutDialog() }

    }

    private fun getUser() {
        // 1. API 요청
        mypageViewModel.getUser()

        // 2. StateFlow 수집 및 UI 반영
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mypageViewModel.getUserState.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Log.d("MypageFragment", "Loading user data")
                            // 로딩 인디케이터 띄우기 등
                        }

                        is UiState.Success -> {
                            // userInfo에서 실제 유저 정보 꺼내오기
                            val user = mypageViewModel.userInfo.value
                            if (user != null) {
                                Log.d("MypageFragment", "유저 정보 UI 업데이트: $user")
                                binding.tvMypageName.text = "${user.name} 님"
                                binding.tvMypageEmail.text = user.email
                            }
                        }

                        is UiState.Error -> {
                            Toast.makeText(requireContext(), "사용자 정보 로드 실패", Toast.LENGTH_SHORT).show()
                            Log.e("MypageFragment", "사용자 정보 로드 실패: ${state.error?.message}")
                        }

                        UiState.Empty -> {
                            Log.d("MypageFragment", "userInfo 상태: Empty")
                        }
                    }
                }
            }
        }
    }


    private fun showLogoutDialog() {
        binding.viewDialogBg.visibility = View.VISIBLE // 배경 뷰 활성화

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_logout_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val logoutBtn = dialog.findViewById<TextView>(R.id.tv_dialog_yes)
        logoutBtn.setOnClickListener {
            dialog.dismiss()
            //logoutUser()
        }

        val cancelBtn = dialog.findViewById<TextView>(R.id.tv_dialog_no)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            binding.viewDialogBg.visibility = View.GONE
        }

        dialog.show()
    }

}