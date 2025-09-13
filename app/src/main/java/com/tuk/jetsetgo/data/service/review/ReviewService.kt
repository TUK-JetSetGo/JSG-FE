package com.tuk.jetsetgo.data.service.review

import com.tuk.jetsetgo.data.dto.BaseResponse
import com.tuk.jetsetgo.data.dto.request.addTravel.EditPlanRequestDto
import com.tuk.jetsetgo.data.dto.request.review.PostReviewRequestDto
import com.tuk.jetsetgo.data.dto.response.review.GetReviewListResponseDto
import com.tuk.jetsetgo.data.dto.response.review.GetReviewResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    // 리뷰 가져오기
    @GET("reviews/{travelPlanId}/day")
    suspend fun getReview(
        @Path("travelPlanId") travelPlanId: Int,
        @Query("dayIndex") dayIndex: Int? = null
    ): BaseResponse<GetReviewResponseDto>

    // 리뷰 작성하기
    @Multipart
    @POST("reviews/{travelPlanId}")
    suspend fun postReview(
        @Path("travelPlanId") travelPlanId: Int,
        @Part request: MultipartBody.Part
    ): BaseResponse<String>

    // 리뷰 목록 조회
    @GET("reviews/list")
    suspend fun getReviewList(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<GetReviewListResponseDto>
}