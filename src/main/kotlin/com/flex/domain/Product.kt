package com.flex.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("products")
data class Product(
    @Id val id: Long? = null,
    val userId: Long,
    val name: String,
    val url: String,
    val targetPrice: BigDecimal,
    val currentPrice: BigDecimal? = null,
    val active: Boolean = true,
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
)
