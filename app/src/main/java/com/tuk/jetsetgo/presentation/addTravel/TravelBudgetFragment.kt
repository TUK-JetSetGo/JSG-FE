package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelBudgetBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class TravelBudgetFragment: BaseFragment<FragmentTravelBudgetBinding>(R.layout.fragment_travel_budget) {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
        setBudget()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivAddTravelBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setBudget() {
        val budgetEditText = binding.etTravelBudgetDropDownBudget

        val textWatcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: android.text.Editable?) {
                budgetEditText.removeTextChangedListener(this)

                val cleanString = s.toString().replace("\\D".toRegex(), "")
                val input = cleanString.toIntOrNull()

                if (input != null) {
                    val formatted = NumberFormat.getNumberInstance(Locale.US).format(input)
                    budgetEditText.setText(formatted)
                    budgetEditText.setSelection(formatted.length)
                }

                binding.viewTravelBudgetConfirmBtn.visibility = if (input != null && input >= 10000) View.VISIBLE else View.INVISIBLE

                budgetEditText.addTextChangedListener(this)
            }
        }

        budgetEditText.addTextChangedListener(textWatcher)
    }


    private fun setupConfirmButton() {
        binding.viewTravelBudgetConfirmBtn.setOnSingleClickListener {
            val budgetText = binding.etTravelBudgetDropDownBudget.text.toString().replace(",", "")
            val budgetValue = budgetText.toIntOrNull() ?: 0
            sharedViewModel.setBudget(budgetValue)
            Log.d("TravelBudgetFragment", "예산: ${sharedViewModel.budget.value}")
            findNavController().navigate(R.id.goToDate)
        }
    }

}