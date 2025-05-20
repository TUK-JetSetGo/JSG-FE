package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tuk.jetsetgo.R
import com.tuk.jetsetgo.databinding.FragmentTravelCountryBinding
import com.tuk.jetsetgo.presentation.addTravel.adapter.SharedViewModel
import com.tuk.jetsetgo.presentation.addTravel.adapter.SpinnerAdapter
import com.tuk.jetsetgo.presentation.base.BaseFragment
import com.tuk.jetsetgo.util.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelCountryFragment: BaseFragment<FragmentTravelCountryBinding>(R.layout.fragment_travel_country) {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: TravelCountryViewModel by activityViewModels()

    private var selectedCountryId: Int? = null

    override fun initObserver() {
        val defaultCityList = mutableListOf("도시를 선택해주세요")
        val defaultCityAdapter = SpinnerAdapter(requireContext(), defaultCityList)
        binding.spinnerTravelCountryCity.adapter = defaultCityAdapter
        binding.spinnerTravelCountryCity.setSelection(0) // 기본값 선택

        // 국가 리스트 관찰
        viewModel.countryList.observe(viewLifecycleOwner) { countries ->
            val countryNames = mutableListOf("국가를 선택해주세요") + countries.map { it.name }
            val countryAdapter = SpinnerAdapter(requireContext(), countryNames)

            binding.spinnerTravelCountryCountry.adapter = countryAdapter
            binding.spinnerTravelCountryCountry.setSelection(0) // 기본값 선택 (선택되지 않음)

            binding.spinnerTravelCountryCountry.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) return // 첫 번째 항목은 무시

                        val selectedCountry = countries[position - 1]
                        selectedCountryId = selectedCountry.id
                        viewModel.fetchCities(selectedCountry.id) // 도시 API 호출
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }

        // 도시 리스트 관찰
        viewModel.cityList.observe(viewLifecycleOwner) { cities ->
            val cityNames = mutableListOf("도시를 선택해주세요") + cities.map { it.name }
            val cityAdapter = SpinnerAdapter(requireContext(), cityNames)

            binding.spinnerTravelCountryCity.adapter = cityAdapter
            binding.spinnerTravelCountryCity.setSelection(0) // 기본값 선택 (선택되지 않음)

            binding.spinnerTravelCountryCity.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) return // 첫 번째 항목은 무시

                        val selectedCity = cities[position - 1]
                        sharedViewModel.setTravelCityId(selectedCity.id) // 선택된 도시 저장
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    override fun initView() {
        viewModel.fetchCountries()
        setupConfirmButton()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivAddTravelBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupConfirmButton() {
        binding.viewTravelCountryConfirmBtn.setOnSingleClickListener {
            Log.d("SharedViewModel", "isGroup: ${sharedViewModel.isGroup.value}")
            Log.d("SharedViewModel", "groupSize: ${sharedViewModel.groupSize.value}")
            Log.d("SharedViewModel", "travelCityId: ${sharedViewModel.travelCityId.value}")
            findNavController().navigate(R.id.goToActivityLevel)
        }
    }

}