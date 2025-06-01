package com.tuk.jetsetgo.data.service.myTravel

import com.tuk.jetsetgo.data.dto.response.myTravel.OsrmResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OsrmService {
    @GET("route/v1/driving/{coordinates}")
    suspend fun getRoute(
        @Path("coordinates") coords: String,
        @Query("overview") overview: String = "full",   // full : 축약 없이 전부
        @Query("geometries") geometries: String = "polyline" // or polyline/polyline6
    ): OsrmResponseDto
}