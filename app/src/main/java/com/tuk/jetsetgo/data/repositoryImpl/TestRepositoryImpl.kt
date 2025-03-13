package com.tuk.jetsetgo.data.repositoryImpl

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.TestRequest
import com.tuk.jetsetgo.data.dto.response.TestResponse
import com.tuk.jetsetgo.data.service.TestService
import com.tuk.jetsetgo.domain.model.TestModel
import com.tuk.jetsetgo.domain.repository.TestRepository
import com.tuk.jetsetgo.util.network.NetworkResult
import com.tuk.jetsetgo.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}