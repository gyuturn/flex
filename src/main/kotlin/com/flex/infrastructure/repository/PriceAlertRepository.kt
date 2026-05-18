package com.flex.infrastructure.repository

import com.flex.domain.AlertStatus
import com.flex.domain.PriceAlert
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PriceAlertRepository : CoroutineCrudRepository<PriceAlert, Long> {
    fun findByUserId(userId: Long): Flow<PriceAlert>
    fun findByStatus(status: AlertStatus): Flow<PriceAlert>
}
