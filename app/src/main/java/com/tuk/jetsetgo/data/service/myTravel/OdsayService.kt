package com.tuk.jetsetgo.data.service.myTravel

import com.tuk.jetsetgo.BuildConfig
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OdsayService {
    @GET("v1/api/searchPubTransPathT")
    suspend fun searchTransport(
        @Query("SX") sx: Double,
        @Query("SY") sy: Double,
        @Query("EX") ex: Double,
        @Query("EY") ey: Double,
        @Query("OPT") opt: Int = 0,
        @Query("lang") lang: Int = 0,
        @Query("apiKey") apiKey: String = BuildConfig.ODSAY_API_KEY
    ): OdsayRouteDto
}