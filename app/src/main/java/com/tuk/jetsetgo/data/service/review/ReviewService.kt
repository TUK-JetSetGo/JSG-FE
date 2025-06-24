package com.tuk.jetsetgo.data.service.review

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewService {
    // 리뷰 가져오기
    @GET("reviews/{travelPlanId}")
    suspend fun getReview(
        @Path("travelPlanId") travelPlanId: Int
    ): BaseResponse<GetReviewResponseDto>
}