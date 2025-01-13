package com.umc.jetsetgo.domain.repository

import com.umc.jetsetgo.data.dto.request.TestRequest
import com.umc.jetsetgo.domain.model.TestModel
import com.umc.jetsetgo.util.network.NetworkResult

interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}