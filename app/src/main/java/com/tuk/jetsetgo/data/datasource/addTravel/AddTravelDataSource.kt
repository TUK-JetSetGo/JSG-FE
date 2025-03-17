package com.tuk.jetsetgo.data.datasource.addTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.CreatePlanRequestDto
import com.tuk.jetsetgo.data.dto.response.addTravel.CreatePlanResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.PurposeResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCityResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCountryResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SpotInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.ThemesResponseDto

interface AddTravelDataSource {
    suspend fun fetchSelectCountry(): BaseResponse<SelectCountryResponseDto>
    suspend fun fetchSelectCity(countryId: Int): BaseResponse<SelectCityResponseDto>
    suspend fun fetchPurpose(): BaseResponse<PurposeResponseDto>
    suspend fun fetchThemes(): BaseResponse<ThemesResponseDto>
    suspend fun fetchCreatePlan(request: CreatePlanRequestDto): BaseResponse<CreatePlanResponseDto?>
    suspend fun fetchSearchSpots(keyword: String?, category: String?, pageable: String?): BaseResponse<SpotInfoResponseDto>
}