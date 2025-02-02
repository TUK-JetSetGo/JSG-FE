package com.tuk.jetsetgo.presentation.addTravel

import android.view.View
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelBudgetBinding
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import java.text.NumberFormat
import java.util.Locale

class TravelBudgetFragment: BaseFragment<FragmentTravelBudgetBinding>(R.layout.fragment_travel_budget) {
    override fun initObserver() {

    }

    override fun initView() {
        setupConfirmButton()
        setBudget()
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
            findNavController().navigate(R.id.goToDate)
        }
    }
}