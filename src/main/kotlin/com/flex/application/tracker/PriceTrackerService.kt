package com.flex.application.tracker

import com.flex.application.crawler.CrawlerService
import com.flex.domain.PriceHistory
import com.flex.infrastructure.repository.PriceHistoryRepository
import com.flex.infrastructure.repository.ProductRepository
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class PriceTrackerService(
    private val productRepository: ProductRepository,
    private val priceHistoryRepository: PriceHistoryRepository,
    private val crawlerService: CrawlerService,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelayString = "\${flex.crawler.interval-ms:600000}")
    fun scheduledCrawl() {
        trackAllActiveProducts()
            .subscribe(
                { log.info("가격 추적 완료: productId={}", it.productId) },
                { e -> log.error("가격 추적 실패", e) },
            )
    }

    fun trackAllActiveProducts(): Flux<PriceHistory> =
        productRepository.findByActiveTrue()
            .toFlux()
            // ADR-004: Service 계층은 Reactor 연산자 중심 (학습 목적)
            // flatMap(50): 최대 50개 URL 동시 크롤링
            .flatMap({ product ->
                crawlerService.fetchPrice(product.url)
                    .flatMap { result ->
                        if (result.price != null && result.success) {
                            val history = PriceHistory(
                                productId = product.id!!,
                                price = result.price,
                            )
                            Mono.fromSuspend { priceHistoryRepository.save(history) }
                        } else {
                            Mono.empty()
                        }
                    }
            }, 50)
}
