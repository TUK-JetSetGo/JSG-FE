package com.umc.jetsetgo.data.repositoryImpl

import com.umc.jetsetgo.data.dto.BaseResponse
import com.umc.jetsetgo.data.dto.request.TestRequest
import com.umc.jetsetgo.data.dto.response.TestResponse
import com.umc.jetsetgo.data.service.TestService
import com.umc.jetsetgo.domain.model.TestModel
import com.umc.jetsetgo.domain.repository.TestRepository
import com.umc.jetsetgo.util.network.NetworkResult
import com.umc.jetsetgo.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}