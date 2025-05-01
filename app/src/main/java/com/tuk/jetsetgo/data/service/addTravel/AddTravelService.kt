package com.tuk.jetsetgo.data.service.addTravel

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.CreatePlanRequestDto
import com.tuk.jetsetgo.data.dto.request.addTravel.EditPlanRequestDto
import com.tuk.jetsetgo.data.dto.response.addTravel.CreatePlanResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.PurposeResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCityResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SelectCountryResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.SpotInfoResponseDto
import com.tuk.jetsetgo.data.dto.response.addTravel.ThemesResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AddTravelService {
    // 국가 조회 API
    @GET("travel-spots/country")
    suspend fun fetchSelectCountry(): BaseResponse<SelectCountryResponseDto>

    // 시 & 도 조회 API
    @GET("travel-spots/city/{countryId}")
    suspend fun fetchSelectCity(
        @Path("countryId") countryId: Int
    ): BaseResponse<SelectCityResponseDto>

    // 여행 목적 조회 API
    @GET("purposes")
    suspend fun fetchPurpose(): BaseResponse<PurposeResponseDto>

    // 여행 테마 조회 API
    @GET("themes")
    suspend fun fetchThemes(): BaseResponse<ThemesResponseDto>

    // 여행 일정 생성 API
    @POST("travel-plans/create")
    suspend fun fetchCreatePlan(
        @Body request: CreatePlanRequestDto
    ): BaseResponse<CreatePlanResponseDto?>

    // 관광지 조회 API
    @GET("tourist-spots/search")
    suspend fun fetchSearchSpots(
        @Query("keyword") keyword: String?,
        @Query("category") category: String?,
        @Query("pageable") pageable: String?
    ): BaseResponse<SpotInfoResponseDto>

    // 여행 일정 수정 API
    @PUT("travel-plans/{itineraryId}")
    suspend fun fetchEditPlan(
        @Path("itineraryId") itineraryId: Int,
        @Body request: EditPlanRequestDto
    ): BaseResponse<String>
}