package com.tuk.jetsetgo.data.repositoryImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.OsrmDataSource
import com.tuk.jetsetgo.domain.repository.myTravel.OsrmRepository
import javax.inject.Inject

class OsrmRepositoryImpl @Inject constructor(
    private val osrmDataSource: OsrmDataSource
): OsrmRepository {
}