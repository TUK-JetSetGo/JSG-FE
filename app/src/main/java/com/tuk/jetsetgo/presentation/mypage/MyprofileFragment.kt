package com.tuk.jetsetgo.presentation.mypage

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMyprofileBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class MyprofileFragment: BaseFragment<FragmentMyprofileBinding>(R.layout.fragment_myprofile) {
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        editText()
        binding.tvMyprofileName.text = "${binding.tvMyprofileName.text} 님"
    }

    private fun setClickListener(){
        binding.ivMyprofileBack.setOnClickListener { findNavController().popBackStack() }
        binding.ivMyprofileSave.setOnClickListener { findNavController().popBackStack() }
        binding.ivMyprofileDelete.setOnClickListener { binding.etMyprofileName.setText("") }
    }

    private fun editText() {
        binding.etMyprofileName.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                return@setOnKeyListener true // 화면 키보드 엔터 방지
            }
            false
        }

        binding.etMyprofileName.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                return@setOnEditorActionListener true // 자판 키보드 엔터 방지
            }
            false
        }
    }

}
