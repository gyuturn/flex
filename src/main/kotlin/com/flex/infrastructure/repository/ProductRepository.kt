package com.flex.infrastructure.repository

import com.flex.domain.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository : CoroutineCrudRepository<Product, Long> {
    fun findByUserId(userId: Long): Flow<Product>
    fun findByActiveTrue(): Flow<Product>
    suspend fun findByUrl(url: String): Product?
}
