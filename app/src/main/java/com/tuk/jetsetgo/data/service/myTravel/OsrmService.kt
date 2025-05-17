package com.tuk.jetsetgo.data.service.myTravel

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OsrmService {
    @GET("route/v1/driving/{coordinates}")
    suspend fun getRoute(
        @Path("coordinates") coords: String,
        @Query("overview") overview: String = "full",   // full : 축약 없이 전부
        @Query("geometries") geometries: String = "geojson" // or polyline/polyline6
    ): OsrmResponse
}