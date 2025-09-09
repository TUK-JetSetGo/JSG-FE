package com.tuk.jetsetgo.data.repositoryImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.OdsayDataSource
import com.tuk.jetsetgo.data.dto.response.myTravel.OdsayRouteDto
import com.tuk.jetsetgo.domain.repository.myTravel.OdsayRepository
import javax.inject.Inject

class OdsayRepositoryImpl @Inject constructor(
    private val odsayDataSource: OdsayDataSource
): OdsayRepository {
    override suspend fun searchTransport(
        sx: Double,
        sy: Double,
        ex: Double,
        ey: Double,
        opt: Int,
        lang: Int,
        apiKey: String
    ): Result<OdsayRouteDto> = runCatching {
        odsayDataSource.searchTransport(sx, sy, ex, ey, opt, lang, apiKey)
    }

}