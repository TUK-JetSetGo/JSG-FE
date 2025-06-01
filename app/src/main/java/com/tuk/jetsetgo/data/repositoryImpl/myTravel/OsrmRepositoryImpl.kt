package com.tuk.jetsetgo.data.repositoryImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.OsrmDataSource
import com.tuk.jetsetgo.data.dto.response.myTravel.OsrmResponseDto
import com.tuk.jetsetgo.domain.repository.myTravel.OsrmRepository
import javax.inject.Inject

class OsrmRepositoryImpl @Inject constructor(
    private val osrmDataSource: OsrmDataSource
): OsrmRepository {
    override suspend fun getRoute(
        coords: String,
        overview: String,
        geometries: String
    ): Result<OsrmResponseDto> = runCatching {
        osrmDataSource.getRoute(coords, overview, geometries)
    }
}