package com.flex.application.notification

import com.flex.domain.AlertChannel
import com.flex.domain.PriceAlert
import com.flex.domain.PriceHistory
import com.flex.domain.Product
import org.slf4j.LoggerFactory
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks

data class AlertEvent(
    val productId: Long,
    val productName: String,
    val triggeredPrice: java.math.BigDecimal,
    val targetPrice: java.math.BigDecimal,
)

@Service
class NotificationService {

    private val log = LoggerFactory.getLogger(javaClass)

    // Hot Publisher: SSE 구독자에게 브로드캐스트 (ADR-002 SSE 설계)
    private val alertSink: Sinks.Many<AlertEvent> = Sinks.many().multicast().onBackpressureBuffer()

    fun getAlertStream(): Flux<ServerSentEvent<AlertEvent>> =
        alertSink.asFlux()
            .map { event -> ServerSentEvent.builder(event).build() }

    fun notify(product: Product, history: PriceHistory): Mono<Void> {
        val event = AlertEvent(
            productId = product.id!!,
            productName = product.name,
            triggeredPrice = history.price,
            targetPrice = product.targetPrice,
        )
        alertSink.tryEmitNext(event)
        log.info("알림 발송: product={}, price={}", product.name, history.price)
        // TODO: 카카오톡/슬랙/이메일 채널별 발송 로직 (이슈 분리 예정)
        return Mono.empty()
    }
}
