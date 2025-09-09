package com.tuk.jetsetgo.data.datasourceImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.OdsayDataSource
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import com.tuk.jetsetgo.data.service.myTravel.OdsayService
import javax.inject.Inject

class OdsayDataSourceImpl @Inject constructor(
    private val odsayService: OdsayService
): OdsayDataSource {
    override suspend fun searchTransport(
        sx: Double,
        sy: Double,
        ex: Double,
        ey: Double,
        opt: Int,
        lang: Int,
        apiKey: String
    ): OdsayRouteDto =
        odsayService.searchTransport(sx, sy, ex, ey, opt, lang, apiKey)

}