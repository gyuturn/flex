package com.flex.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("price_histories")
data class PriceHistory(
    @Id val id: Long? = null,
    val productId: Long,
    val price: BigDecimal,
    val crawledAt: Instant = Instant.now(),
)
