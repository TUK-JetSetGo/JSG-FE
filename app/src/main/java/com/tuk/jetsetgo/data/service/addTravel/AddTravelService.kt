package com.tuk.jetsetgo.data.service.addTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.CreatePlanRequestDto
import com.tuk.jetsetgo.data.dto.response.addTravel.CreatePlanResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.PurposeResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCityResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCountryResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.ThemesResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AddTravelService {
    @GET("travel-spots/country")
    suspend fun fetchSelectCountry(): BaseResponse<SelectCountryResponseDto>

    @GET("travel-spots/city/{countryId}")
    suspend fun fetchSelectCity(
        @Path("countryId") countryId: Int
    ): BaseResponse<SelectCityResponseDto>

    @GET("purposes")
    suspend fun fetchPurpose(): BaseResponse<PurposeResponseDto>

    @GET("themes")
    suspend fun fetchThemes(): BaseResponse<ThemesResponseDto>

    @POST("travel-plans/create")
    suspend fun fetchCreatePlan(
        @Body request: CreatePlanRequestDto
    ): BaseResponse<CreatePlanResponseDto>
}