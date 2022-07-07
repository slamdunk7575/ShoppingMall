package com.jun.shoppingmall.review.application

import com.jun.shoppingmall.review.dao.ReviewRepository
import com.jun.shoppingmall.review.domain.Review
import com.jun.shoppingmall.review.dto.ReviewRequest
import com.jun.shoppingmall.review.dto.ReviewResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {
    @Transactional(readOnly = true)
    override fun createReview(reviewRequest: ReviewRequest): ReviewResponse {
        // return ReviewResponse.of(reviewRepository.save(reviewRequest.toEntity()))
        return reviewRequest.let(ReviewRequest::toEntity)
            .let {
                ReviewResponse.of(reviewRepository.save(it))
            }
    }

    @Transactional(readOnly = true)
    override fun getReviews(goodNo: Int, pageable: Pageable): List<ReviewResponse> {
        /*val reviews = reviewRepository.findReviewByGoodsNoOrderByNoDesc(goodNo, pageable)
        return reviews.stream()
            .map(ReviewResponse::of)
            .collect(Collectors.toList())*/

        return reviewRepository.findReviewByGoodsNoOrderByNoDesc(goodNo, pageable)
            .map {
                ReviewResponse.of(it)
            }.toMutableList()
    }

    @Transactional
    override fun updateReview(no: Long, reviewRequest: ReviewRequest) {
        val review = findReviewById(no)
        review.update(reviewRequest.toEntity())
    }

    @Transactional
    override fun deleteReview(no: Long) {
        val review = findReviewById(no)
        reviewRepository.delete(review)
    }

    private fun findReviewById(no: Long): Review {
        return reviewRepository.findByIdOrNull(no) ?:
        throw EntityNotFoundException("id = $no, 해당하는 리뷰가 없습니다.")
    }

}
