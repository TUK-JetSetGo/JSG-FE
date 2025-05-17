package com.tuk.jetsetgo.data.datasourceImpl.myTravel

import com.tuk.jetsetgo.data.datasource.myTravel.OsrmDataSource
import com.tuk.jetsetgo.data.service.myTravel.OsrmService
import javax.inject.Inject

class OsrmDataSourceImpl @Inject constructor(
    private val osrmService: OsrmService
): OsrmDataSource {

}