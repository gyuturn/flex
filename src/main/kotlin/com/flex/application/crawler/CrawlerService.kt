package com.flex.application.crawler

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.math.BigDecimal

data class CrawlResult(
    val url: String,
    val price: BigDecimal?,
    val success: Boolean,
)

@Service
class CrawlerService(private val crawlerWebClient: WebClient) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun fetchPrice(url: String): Mono<CrawlResult> =
        crawlerWebClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class)
            .map { html -> parsePrice(url, html) }
            .onErrorResume { e ->
                log.warn("크롤링 실패: url={}, error={}", url, e.message)
                Mono.just(CrawlResult(url = url, price = null, success = false))
            }

    private fun parsePrice(url: String, html: String): CrawlResult {
        // TODO: 쇼핑몰별 파서 구현 (이슈 분리 예정)
        return CrawlResult(url = url, price = null, success = true)
    }
}
