package com.tuk.jetsetgo.data.datasource.myTravel

import com.tuk.jetsetgo.data.dto.response.myTravel.OsrmResponseDto

interface OsrmDataSource {
    suspend fun getRoute(coords: String, overview: String, geometries: String) : OsrmResponseDto
}