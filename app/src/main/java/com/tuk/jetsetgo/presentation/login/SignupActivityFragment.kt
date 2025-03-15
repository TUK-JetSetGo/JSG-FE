package com.tuk.jetsetgo.presentation.login

import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentSignupActivityBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivityFragment : BaseFragment<FragmentSignupActivityBinding>(R.layout.fragment_signup_activity) {

    override fun initView() {
    }

    override fun initObserver() {
        setClickListener()
    }

    private fun setClickListener(){
        binding.viewSignupActivityNextBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToSignupTheme)
        }
    }
}