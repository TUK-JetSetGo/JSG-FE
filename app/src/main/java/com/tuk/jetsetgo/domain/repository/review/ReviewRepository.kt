package com.tuk.jetsetgo.domain.repository.review

import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel

interface ReviewRepository {
    suspend fun getReview(travelPlanId: Int): Result<GetReviewResponseModel>

}