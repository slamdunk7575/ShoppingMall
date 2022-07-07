package com.jun.shoppingmall.review.ui

import com.jun.shoppingmall.review.application.ReviewServiceImpl
import com.jun.shoppingmall.review.dto.ReviewRequest
import com.jun.shoppingmall.review.dto.ReviewResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageRequest
import org.springframework.http.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewRestControllerTest(
    @Autowired private var restTemplate: TestRestTemplate,
    @Autowired private var reviewServiceImpl: ReviewServiceImpl,
    @LocalServerPort private val port: Int
) {

    @BeforeEach
    internal fun setUp() {
        var reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정말 편해요"
            this.image = "https://image/review.jpg"
            this.width = 100
            this.height = 100
        }

        for (i in 1..20) {
            reviewServiceImpl.createReview(reviewRequest)
        }
    }

    @Test
    fun `리뷰 생성 API 테스트`() {
        // given
        val reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정말 편해요"
            this.image = "https://image/review.jpg"
            this.width = 100
            this.height = 100
        }

        val url = "http://localhost:${port}/api/v1/review"

        // when
        val responseEntity: ResponseEntity<ReviewResponse> =
            restTemplate.postForEntity(url, reviewRequest, ReviewResponse::class.java)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
        assertThat(responseEntity.body?.goodsNo ?: 0).isEqualTo(1234)
        assertThat(responseEntity.body?.contents ?: null).isEqualTo("정말 편해요")
        assertThat(responseEntity.body?.image ?: "").isEqualTo("https://image/review.jpg")
    }

    @Test
    fun `리뷰 목록 조회 API 테스트`() {
        // given
        val goodsNo = 1234
        val pageable = PageRequest.of(0, 5);
        val targetUrl: URI = UriComponentsBuilder.fromUriString("http://localhost:${port}")
            .path("/api/v1/review")
            .queryParam("goodsNo", goodsNo)
             .queryParam("page", pageable.pageNumber)
             .queryParam("size", pageable.pageSize)
            .build()
            .toUri()

        // when
        val responseEntity : ResponseEntity<List<ReviewResponse>> =
            restTemplate.exchange(targetUrl, HttpMethod.GET, null, object: ParameterizedTypeReference<List<ReviewResponse>>() {})

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.headers.contentType).isEqualTo(MediaType.APPLICATION_JSON)
        assertThat(responseEntity.body?.size ?: 0).isEqualTo(5)
    }

    @Test
    fun `리뷰 수정 API 테스트`() {
        // given
        val reviewRequest = ReviewRequest().apply {
            this.goodsNo = 1234
            this.contents = "정사이즈에요"
            this.image = "https://image/size.jpg"
            this.width = 50
            this.height = 50
        }

        val no = 1L
        val url = "http://localhost:${port}/api/v1/review/${no}"

        // when
        val httpEntity= HttpEntity<ReviewRequest>(reviewRequest)
        val responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ReviewResponse::class.java)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `리뷰 삭제 API 테스트`() {
        // given
        val no = 2L
        val url = "http://localhost:${port}/api/v1/review/${no}"

        // when
        val responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, Unit.javaClass)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }

}
