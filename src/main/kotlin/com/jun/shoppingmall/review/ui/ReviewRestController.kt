package com.jun.shoppingmall.review.ui

import com.jun.shoppingmall.review.application.ReviewServiceImpl
import com.jun.shoppingmall.review.dto.ReviewRequest
import com.jun.shoppingmall.review.dto.ReviewResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class ReviewRestController(
    private val reviewServiceImpl: ReviewServiceImpl) {

    @PostMapping("/review")
    fun createReview(@RequestBody reviewRequest: ReviewRequest): ResponseEntity<ReviewResponse> {
        val reviewResponse = reviewServiceImpl.createReview(reviewRequest)
        val uri = URI.create("/api/v1/review/" + reviewResponse.no)
        return ResponseEntity.created(uri).body(reviewResponse)
    }

    @GetMapping("/review")
    fun getReviews(
        @RequestParam(value = "goodsNo") goodsNo: Int,
        @PageableDefault(size = 10, sort = ["no"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<List<ReviewResponse>> {
        val reviews = reviewServiceImpl.getReviews(goodsNo, pageable)
        return ResponseEntity.ok().body(reviews)
    }

    @PutMapping("/review/{no}")
    fun updateReview(
        @PathVariable(name = "no") no: Long,
        @Valid @RequestBody reviewRequest: ReviewRequest, bindingResult: BindingResult): ResponseEntity<ReviewResponse> {
        val updatedReview = reviewServiceImpl.updateReview(no, reviewRequest)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/review/{no}")
    fun deleteReview(@PathVariable no: Long): ResponseEntity<Unit> {
        reviewServiceImpl.deleteReview(no)
        return ResponseEntity.noContent().build()
    }
}
