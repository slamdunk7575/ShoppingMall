package com.jun.shoppingmall.review.application

import com.jun.shoppingmall.review.dao.ReviewRepository
import com.jun.shoppingmall.review.dto.ReviewRequest
import com.jun.shoppingmall.review.dto.ReviewResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    lateinit var reviewServiceImpl: ReviewServiceImpl

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @BeforeEach
    internal fun setUp() {
        var reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정말 편해요"
            this.image = "https://image/review.jpg"
            this.width = 100
            this.height = 100
        }

        for (i in 1..10) {
            reviewServiceImpl.createReview(reviewRequest)
        }
    }

    @Test
    fun `리뷰 생성 테스트`() {
        // given
        val reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정말 편해요"
            this.image = "https://image/review.jpg"
            this.width = 100
            this.height = 100
        }

        // when
        val createdReview = reviewServiceImpl.createReview(reviewRequest)

        // then
        assertThat(createdReview.goodsNo).isEqualTo(1234)
        assertThat(createdReview.contents).isEqualTo("정말 편해요")
        assertThat(createdReview.image).isEqualTo("https://image/review.jpg")
        assertThat(createdReview.width).isEqualTo(100)
        assertThat(createdReview.height).isEqualTo(100)
    }

    @Test
    fun `리뷰 목록 Paging 조회 테스트`() {
        // given
        val goodsNo = 1234
        val pageable = PageRequest.of(0, 5);

        // when
        val reviews: List<ReviewResponse> = reviewServiceImpl.getReviews(goodsNo, pageable)

        // then
        assertThat(reviews.size).isEqualTo(5)
    }

    @Test
    fun `리뷰 수정 테스트`() {
        // given
        val reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정사이즈에요"
            this.image = "https://image/size.jpg"
            this.width = 50
            this.height = 50
        }

        // when
        reviewServiceImpl.updateReview(1L, reviewRequest)
        val result = reviewRepository.findById(1L).get()

        // then
        assertThat(result.contents).isEqualTo("정사이즈에요")
        assertThat(result.image).isEqualTo("https://image/size.jpg")
        assertThat(result.width).isEqualTo(50)
        assertThat(result.height).isEqualTo(50)
    }

    @Test
    fun `리뷰 삭제 테스트`() {
        // given
        val deleteNo = 1L

        // when
        reviewServiceImpl.run { deleteReview(1) }

        // then
        assertThrows(NoSuchElementException::class.java) {
            reviewRepository.findById(deleteNo).get()
        }
    }
}
