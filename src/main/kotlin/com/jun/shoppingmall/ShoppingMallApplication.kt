package com.jun.shoppingmall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ShoppingMallApplication

fun main(args: Array<String>) {
    runApplication<ShoppingMallApplication>(*args)
}
