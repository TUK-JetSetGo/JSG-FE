package com.tuk.jetsetgo.presentation.login

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSignupProfileBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupProfileFragment : BaseFragment<FragmentSignupProfileBinding>(R.layout.fragment_signup_profile) {

    override fun initView() {
    }

    override fun initObserver() {
        setClickListener()
    }

    private fun setClickListener(){
        binding.viewSignupProfileNextBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToSignupActivity)
        }
    }
}