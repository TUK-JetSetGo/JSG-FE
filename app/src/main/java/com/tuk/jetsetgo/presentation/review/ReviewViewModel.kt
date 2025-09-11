package com.tuk.jetsetgo.presentation.review

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.jetsetgo.domain.model.request.review.PostReviewRequestModel
import com.tuk.jetsetgo.domain.model.response.review.GetReviewResponseModel
import com.tuk.jetsetgo.domain.repository.review.ReviewRepository
import com.tuk.jetsetgo.util.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _postReviewState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val postReviewState: StateFlow<UiState<Boolean>> = _postReviewState.asStateFlow()

    private val _getReviewState = MutableStateFlow<UiState<GetReviewResponseModel>>(UiState.Empty)
    val getReviewState: StateFlow<UiState<GetReviewResponseModel>> = _getReviewState

    fun getReview(travelPlanId: Int) {
        viewModelScope.launch {
            _getReviewState.value = UiState.Loading
            reviewRepository.getReview(travelPlanId)
                .onSuccess { _getReviewState.value = UiState.Success(it) }
                .onFailure { _getReviewState.value = UiState.Error(it) }
        }
    }

    fun postReview(travelPlanId: Int, body: PostReviewRequestModel) {
        viewModelScope.launch {
            _postReviewState.value = UiState.Loading
            reviewRepository.postReview(travelPlanId, body)
                .onSuccess {
                    Log.d("ReviewViewModel", "postReview() 성공")
                    _postReviewState.value = UiState.Success(true)
                }
                .onFailure { exception ->
                    Log.e("ReviewViewModel", "postReview() 실패: ${exception.message}")
                    _postReviewState.value = UiState.Error(exception)
                }
        }
    }
}
