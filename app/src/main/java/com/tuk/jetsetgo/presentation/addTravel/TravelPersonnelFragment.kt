package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelPersonnelBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener

class TravelPersonnelFragment : BaseFragment<FragmentTravelPersonnelBinding>(R.layout.fragment_travel_personnel) {
    override fun initObserver() {

    }

    override fun initView() {
        bottomNavigationRemove()
        setupConfirmButton()
        setupClickListeners()
    }

    private fun bottomNavigationRemove() {
        // BottomNavigationView 숨기기
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bnv)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun setupClickListeners() {
        val btnAlone = binding.tvTravelPersonnelBtnAlone
        val btnGroup = binding.tvTravelPersonnelBtnGroup
        val confirmBtn = binding.viewTravelPersonnelConfirmBtn
        val text1 = binding.tvTravelPersonnelText1
        val editText = binding.etTravelPersonnelPersonnel
        val text2 = binding.tvTravelPersonnelText2

        btnAlone.setOnSingleClickListener {
            confirmBtn.visibility = View.VISIBLE
            text1.visibility = View.INVISIBLE
            editText.visibility = View.INVISIBLE
            text2.visibility = View.INVISIBLE

            btnAlone.setBackgroundResource(R.drawable.shape_rect_20_blue_main_fill)
            btnGroup.setBackgroundResource(R.drawable.shape_rect_20_gray400_fill)
        }

        btnGroup.setOnSingleClickListener {
            confirmBtn.visibility = View.INVISIBLE
            text1.visibility = View.VISIBLE
            editText.visibility = View.VISIBLE
            text2.visibility = View.VISIBLE

            btnGroup.setBackgroundResource(R.drawable.shape_rect_20_blue_main_fill)
            btnAlone.setBackgroundResource(R.drawable.shape_rect_20_gray400_fill)
        }

        editText.addTextChangedListener { text ->
            val input = text.toString().toIntOrNull()
            confirmBtn.visibility = if (input != null && input >= 1) View.VISIBLE else View.INVISIBLE
        }

        confirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToCountry)
        }
    }

    private fun setupConfirmButton() {
        binding.viewTravelPersonnelConfirmBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.goToCountry)
        }
    }
}