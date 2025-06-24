package com.tuk.jetsetgo.data.repositoryImpl.review

import com.tuk.jetsetgo.data.datasource.review.ReviewDataSource
import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel
import com.tuk.jetsetgo.domain.repository.review.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource
): ReviewRepository {

    override suspend fun getReview(travelPlanId: Int): Result<GetReviewResponseModel> = runCatching {
        reviewDataSource.getReview(travelPlanId).data.toGetReviewResponseModel()
    }

}