package com.jun.shoppingmall.review.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.jun.shoppingmall.review.domain.Review

class ReviewResponse {
    var no: Long? = null
    @JsonProperty("goods_no")
    var goodsNo: Int? = 0
    var contents: String? = null
    var image: String? = null
    var width: Int? = 0
    var height: Int? = 0

    companion object {
        @JvmStatic
        fun of(review: Review): ReviewResponse {
            return ReviewResponse().apply {
                no = review.no
                goodsNo = review.goodsNo
                contents = review.contents
                image = review.image
                width = review.width
                height = review.height
            }
        }
    }
}
