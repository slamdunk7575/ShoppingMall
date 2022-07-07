package com.jun.shoppingmall.review.application

import com.jun.shoppingmall.review.dto.ReviewRequest
import com.jun.shoppingmall.review.dto.ReviewResponse
import org.springframework.data.domain.Pageable

interface ReviewService {
    fun createReview(reviewRequest: ReviewRequest): ReviewResponse
    fun getReviews(goodNo: Int, pageable: Pageable): List<ReviewResponse>
    fun updateReview(no: Long, reviewRequest: ReviewRequest)
    fun deleteReview(no: Long)
}
