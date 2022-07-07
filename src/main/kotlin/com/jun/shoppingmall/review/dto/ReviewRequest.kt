package com.jun.shoppingmall.review.dto

import com.jun.shoppingmall.review.domain.Review
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ReviewRequest(
    @field:NotNull
    var goodsNo: Int?=0,

    var contents: String?=null,

    @field:NotNull
    @field:Size(min = 1, max = 100)
    var image: String="",

    var width: Int?=0,

    var height: Int?=0
) {
    fun toEntity(): Review = Review.fromRequest(this)
}
