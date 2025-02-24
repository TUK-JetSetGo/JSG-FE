package com.tuk.jetsetgo.presentation.addTravel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.response.SelectCityResponseModel
import com.tuk.jetsetgo.domain.model.response.SelectCountryResponseModel
import com.tuk.jetsetgo.domain.repository.addTravel.AddTravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelCountryViewModel @Inject constructor(
    private val addTravelRepository: AddTravelRepository
): ViewModel() {
    private val _countryList = MutableLiveData<List<SelectCountryResponseModel.TravelCountryInfoListModel>>()
    val countryList: LiveData<List<SelectCountryResponseModel.TravelCountryInfoListModel>> get() = _countryList

    private val _cityList = MutableLiveData<List<SelectCityResponseModel.TravelCityInfoListModel>>()
    val cityList: LiveData<List<SelectCityResponseModel.TravelCityInfoListModel>> get() = _cityList

    fun fetchCountries() {
        viewModelScope.launch {
            addTravelRepository.fetchSelectCountry()
                .onSuccess { response ->
                    _countryList.value = response.travelCountryInfoList
                    Log.d("fetchCountries", "국가 목록 로드 성공: ${response.travelCountryInfoList}")
                }
                .onFailure { exception ->
                    Log.e("fetchCountries", "국가 목록 로드 실패: ${exception.message}")
                    _countryList.value = emptyList()
                }
        }
    }

    fun fetchCities(countryId: Int) {
        viewModelScope.launch {
            addTravelRepository.fetchSelectCity(countryId)
                .onSuccess { response ->
                    _cityList.value = response.travelCityInfoList
                    Log.d("fetchCities", "도시 목록 로드 성공: ${response.travelCityInfoList}")
                }
                .onFailure { exception ->
                    Log.e("fetchCities", "도시 목록 로드 실패: ${exception.message}")
                    _cityList.value = emptyList()
                }
        }
    }
}