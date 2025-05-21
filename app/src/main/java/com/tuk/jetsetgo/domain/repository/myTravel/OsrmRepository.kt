package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.data.dto.response.myTravel.OsrmResponseDto

interface OsrmRepository {
    suspend fun getRoute(coords: String, overview: String, geometries: String): Result<OsrmResponseDto>
}