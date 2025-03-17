package com.tuk.jetsetgo.data.datasourceImpl.addTravel

import com.tuk.jetsetgo.data.datasource.addTravel.AddTravelDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.CreatePlanRequestDto
import com.tuk.jetsetgo.data.dto.response.addTravel.CreatePlanResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.PurposeResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCityResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCountryResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SpotInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.ThemesResponseDto
import com.tuk.jetsetgo.data.service.addTravel.AddTravelService
import javax.inject.Inject

class AddTravelDataSourceImpl @Inject constructor(
    private val addTravelService: AddTravelService
): AddTravelDataSource {
    override suspend fun fetchSelectCountry(): BaseResponse<SelectCountryResponseDto> =
        addTravelService.fetchSelectCountry()

    override suspend fun fetchSelectCity(countryId: Int): BaseResponse<SelectCityResponseDto> =
        addTravelService.fetchSelectCity(countryId)

    override suspend fun fetchPurpose(): BaseResponse<PurposeResponseDto> =
        addTravelService.fetchPurpose()

    override suspend fun fetchThemes(): BaseResponse<ThemesResponseDto> =
        addTravelService.fetchThemes()

    override suspend fun fetchCreatePlan(request: CreatePlanRequestDto): BaseResponse<CreatePlanResponseDto?> =
        addTravelService.fetchCreatePlan(request)

    override suspend fun fetchSearchSpots(
        keyword: String?,
        category: String?,
        pageable: String?
    ): BaseResponse<SpotInfoResponseDto> =
        addTravelService.fetchSearchSpots(keyword, category, pageable)
}