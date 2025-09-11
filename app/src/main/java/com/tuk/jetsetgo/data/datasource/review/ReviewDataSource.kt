package com.tuk.jetsetgo.data.datasource.review

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.review.PostReviewRequestDto
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto
import okhttp3.MultipartBody

interface ReviewDataSource {
    suspend fun getReview(travelPlanId: Int): BaseResponse<GetReviewResponseDto>

    suspend fun postReview(
        travelPlanId: Int,
        requestPart: MultipartBody.Part
    ): BaseResponse<String>
}