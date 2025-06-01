package com.tuk.jetsetgo.domain.repository.addTravel

import com.tuk.jetsetgo.domain.model.request.addTravel.CreatePlanRequestModel
import com.tuk.jetsetgo.domain.model.request.addTravel.EditPlanRequestModel
import com.tuk.jetsetgo.domain.model.response.addTravel.CreatePlanResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.PurposeResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SelectCityResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SelectCountryResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.SpotInfoResponseModel
import com.tuk.jetsetgo.domain.model.response.addTravel.ThemesResponseModel

interface AddTravelRepository {
    suspend fun fetchSelectCountry(): Result<SelectCountryResponseModel>
    suspend fun fetchSelectCity(countryId: Int): Result<SelectCityResponseModel>
    suspend fun fetchPurpose(): Result<PurposeResponseModel>
    suspend fun fetchThemes(): Result<ThemesResponseModel>
    suspend fun fetchCreatePlan(request: CreatePlanRequestModel): Result<CreatePlanResponseModel?>
    suspend fun fetchSearchSpots(
        keyword: String?,
        category: String?,
        page: Int?,
        size: Int?,
        sort: String?
    ): Result<SpotInfoResponseModel>
    suspend fun fetchEditPlan(itineraryId: Int, request: EditPlanRequestModel): Result<String>
}