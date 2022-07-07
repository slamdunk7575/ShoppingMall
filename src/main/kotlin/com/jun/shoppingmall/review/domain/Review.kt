package com.jun.shoppingmall.review.domain

import com.jun.shoppingmall.base.BaseEntity
import com.jun.shoppingmall.review.dto.ReviewRequest
import org.hibernate.annotations.Comment
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
class Review : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var no: Long = 0

    @Comment("상품번호")
    @Column(nullable = false)
    var goodsNo: Int?=0

    @Comment("내용")
    @Column(nullable = true, columnDefinition = "TEXT")
    var contents: String?=null

    @Comment("이미지 주소")
    @Column(length = 100, nullable = false)
    var image: String=""

    @Comment("이미지 넓이")
    @Column(nullable = true)
    var width: Int?=0

    @Comment("이미지 높이")
    @Column(nullable = true)
    var height: Int?=0

    companion object {
        @JvmStatic
        fun fromRequest(request: ReviewRequest): Review {
            return Review().apply {
                goodsNo = request.goodsNo
                contents = request.contents
                image = request.image
                width = request.width
                height = request.height
            }
        }
    }

    fun update(updatedReview: Review) {
        this.contents = updatedReview.contents
        this.image = updatedReview.image
        this.height = updatedReview.height
        this.width = updatedReview.width
    }
}
