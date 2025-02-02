package com.tuk.jetsetgo.domain.repository.addTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.domain.model.request.CreatePlanRequestModel
import com.tuk.jetsetgo.domain.model.response.CreatePlanResponseModel
import com.tuk.jetsetgo.domain.model.response.PurposeResponseModel
import com.tuk.jetsetgo.domain.model.response.SelectCityResponseModel
import com.tuk.jetsetgo.domain.model.response.SelectCountryResponseModel
import com.tuk.jetsetgo.domain.model.response.ThemesResponseModel

interface AddTravelRepository {
    suspend fun fetchSelectCountry(): Result<SelectCountryResponseModel>
    suspend fun fetchSelectCity(countryId: Int): Result<SelectCityResponseModel>
    suspend fun fetchPurpose(): Result<PurposeResponseModel>
    suspend fun fetchThemes(): Result<ThemesResponseModel>
    suspend fun fetchCreatePlan(request: CreatePlanRequestModel): Result<CreatePlanResponseModel>
}