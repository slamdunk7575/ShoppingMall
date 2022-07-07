package com.jun.shoppingmall.review.dao

import com.jun.shoppingmall.review.domain.Review
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findReviewByGoodsNoOrderByNoDesc(goodsNo: Int, pageable: Pageable): List<Review>
}
