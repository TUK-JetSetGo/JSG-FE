package com.tuk.jetsetgo.data.datasourceImpl.review

import com.tuk.jetsetgo.data.datasource.review.ReviewDataSource
import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.EditPlanRequestDto
import com.tuk.jetsetgo.data.dto.request.review.PostReviewRequestDto
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto
import com.tuk.jetsetgo.data.service.review.ReviewService
import okhttp3.MultipartBody
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewService: ReviewService
): ReviewDataSource {

    override suspend fun getReview(travelPlanId: Int): BaseResponse<GetReviewResponseDto> =
        reviewService.getReview(travelPlanId)

    override suspend fun postReview(
        travelPlanId: Int,
        requestPart: MultipartBody.Part
    ): BaseResponse<String> =
        reviewService.postReview(travelPlanId, requestPart)

    override suspend fun getReviewList(page: Int, size: Int) =
        reviewService.getReviewList(page, size)
}