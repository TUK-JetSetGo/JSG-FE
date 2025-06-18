package com.tuk.jetsetgo.data.datasource.review

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto

interface ReviewDataSource {
    suspend fun getReview(travelPlanId: Int): BaseResponse<GetReviewResponseDto>

}