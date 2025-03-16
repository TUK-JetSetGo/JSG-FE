package com.tuk.jetsetgo.presentation.login

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSignupThemeBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupThemeFragment : BaseFragment<FragmentSignupThemeBinding>(R.layout.fragment_signup_theme) {

    override fun initView() {
    }

    override fun initObserver() {
        setClickListener()
    }

    private fun setClickListener(){
        binding.viewTravelThemeConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToSignupComplete)
        }
    }

}