package com.tuk.jetsetgo.domain.repository.myTravel

import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto

interface OdsayRepository {
    suspend fun searchTransport(sx: Double, sy: Double, ex: Double, ey: Double, opt: Int, lang: Int, apiKey: String): Result<OdsayRouteDto>
}