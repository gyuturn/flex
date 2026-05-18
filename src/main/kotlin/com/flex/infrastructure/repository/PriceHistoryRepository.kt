package com.flex.infrastructure.repository

import com.flex.domain.PriceHistory
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

interface PriceHistoryRepository : CoroutineCrudRepository<PriceHistory, Long> {
    fun findByProductIdOrderByCrawledAtDesc(productId: Long): Flow<PriceHistory>
    fun findByProductIdAndCrawledAtAfter(productId: Long, since: Instant): Flow<PriceHistory>
}
