package com.tuk.jetsetgo.data.datasourceImpl.review

import com.tuk.jetsetgo.data.datasource.review.ReviewDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto
import com.tuk.jetsetgo.data.service.review.ReviewService
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService
): ReviewDataSource {

    override suspend fun getReview(travelPlanId: Int): BaseResponse<GetReviewResponseDto> =
        reviewService.getReview(travelPlanId)

}