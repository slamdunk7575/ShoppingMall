package com.jun.shoppingmall.review.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.Validation

class ReviewRequestTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `리뷰 DTO 검증 테스트` () {
        // given
        var reviewRequest = ReviewRequest().apply {
            this.goodsNo = 4567
            this.contents = "사이즈가 조금 커요"
            this.image = "https://image/sample.jpg"
            this.width = 200
            this.height = 200
        }

        // when
        val result = validator.validate(reviewRequest)

        result.forEach {
            println(it.propertyPath.last().name)
            println(it.message)
            println(it.invalidValue)
        }

        // then
        assertThat(result.isEmpty()).isTrue
    }

}
