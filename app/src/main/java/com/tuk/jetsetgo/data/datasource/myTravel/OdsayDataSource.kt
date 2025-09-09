package com.tuk.jetsetgo.data.datasource.myTravel

import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto

interface OdsayDataSource {
    suspend fun searchTransport(sx: Double, sy: Double, ex: Double, ey: Double, opt: Int, lang: Int, apiKey: String): OdsayRouteDto
}