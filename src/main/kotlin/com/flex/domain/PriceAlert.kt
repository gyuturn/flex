package com.flex.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

enum class AlertStatus { PENDING, SENT, FAILED }
enum class AlertChannel { KAKAO, SLACK, EMAIL }

@Table("price_alerts")
data class PriceAlert(
    @Id val id: Long? = null,
    val productId: Long,
    val userId: Long,
    val triggeredPrice: BigDecimal,
    val targetPrice: BigDecimal,
    val channel: AlertChannel,
    val status: AlertStatus = AlertStatus.PENDING,
    val createdAt: Instant = Instant.now(),
)
