package com.flex.presentation

import com.flex.domain.Product
import com.flex.infrastructure.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getProducts(@RequestParam userId: Long): Flow<Product> =
        productRepository.findByUserId(userId)

    @GetMapping("/{id}")
    suspend fun getProduct(@PathVariable id: Long): Product? =
        productRepository.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createProduct(@RequestBody product: Product): Product =
        productRepository.save(product)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun deleteProduct(@PathVariable id: Long) =
        productRepository.deleteById(id)
}
