package com.tuk.jetsetgo.data.repositoryImpl.review

import android.util.Log
import com.google.gson.Gson
import com.tuk.jetsetgo.data.datasource.review.ReviewDataSource
import com.tuk.jetsetgo.domain.model.request.review.PostReviewRequestModel
import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel
import com.tuk.jetsetgo.domain.model.response.review.ReviewListResponseModel
import com.tuk.jetsetgo.domain.repository.review.ReviewRepository
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource
) : ReviewRepository {

    override suspend fun getReview(travelPlanId: Int): Result<GetReviewResponseModel> = runCatching {
        reviewDataSource.getReview(travelPlanId).data.toGetReviewResponseModel()
    }

    override suspend fun postReview(
        travelPlanId: Int,
        request: PostReviewRequestModel
    ): Result<String> = runCatching {
        // 1) Domain -> DTO (flat) -> JSON 문자열
        val dto = request.toPostReviewRequestDto()
        val json = Gson().toJson(dto)

        // 2) JSON -> RequestBody
        val jsonRequestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())

        // 3) RequestBody -> MultipartBody.Part  (서버 요구 파트명: "request")
        val requestPart = MultipartBody.Part.createFormData(
            /* name     = */ "request",
            /* filename = */ "request.json",
            /* body     = */ jsonRequestBody
        )

        // 4) API 호출
        reviewDataSource.postReview(travelPlanId, requestPart).data
    }.onFailure { e ->
        if (e is HttpException) {
            val code = e.code()
            val body = e.response()?.errorBody()?.string()
            Log.e("ReviewRepositoryImpl", "postReview HTTP $code, body=$body")
        } else {
            Log.e("ReviewRepositoryImpl", "postReview failed: ${e.message}", e)
        }
    }


    override suspend fun getReviewList(page: Int, size: Int): Result<ReviewListResponseModel> =
        runCatching {
            reviewDataSource.getReviewList(page, size).data.toModel()
        }
}
