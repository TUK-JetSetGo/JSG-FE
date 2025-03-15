package com.tuk.jetsetgo.presentation.login

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSignupCompleteBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment

class SignupCompleteFragment : BaseFragment<FragmentSignupCompleteBinding>(R.layout.fragment_signup_complete) {
    override fun initView() {
    }

    override fun initObserver() {
        binding.root.postDelayed({
            findNavController().navigate(R.id.goToHome)
        }, 3000)
    }

}