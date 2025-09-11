package com.tuk.jetsetgo.domain.repository.review

import com.tuk.jetsetgo.domain.model.request.review.PostReviewRequestModel
import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel
import com.tuk.jetsetgo.domain.model.response.review.ReviewListResponseModel

interface ReviewRepository {
    suspend fun getReview(travelPlanId: Int): Result<GetReviewResponseModel>
    suspend fun postReview(travelPlanId: Int, request: PostReviewRequestModel): Result<String>
    suspend fun getReviewList(page: Int, size: Int): Result<ReviewListResponseModel>

}