package com.tuk.jetsetgo.presentation.mypage

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentMypageBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class MypageFragment: BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    override fun initObserver() {

    }

    override fun initView() {
        setClickListener()
        binding.tvMypageName.text = "${binding.tvMypageName.text} 님"
    }

    private fun setClickListener(){
        binding.clMypageProfile.setOnSingleClickListener {
            findNavController().navigate(R.id.goToProfile)
        }
    }
}