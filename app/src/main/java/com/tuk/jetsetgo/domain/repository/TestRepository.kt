package com.tuk.jetsetgo.domain.repository

import com.tuk.jetsetgo.data.dto.request.TestRequest
import com.tuk.jetsetgo.domain.model.TestModel
import com.tuk.jetsetgo.util.network.NetworkResult

interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}