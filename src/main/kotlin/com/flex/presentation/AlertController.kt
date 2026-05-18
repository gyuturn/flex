package com.flex.presentation

import com.flex.application.notification.AlertEvent
import com.flex.application.notification.NotificationService
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/v1/alerts")
class AlertController(private val notificationService: NotificationService) {

    // SSE 스트림: Flux<ServerSentEvent<T>> 반환 (ADR-002 핵심 패턴)
    @GetMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamAlerts(): Flux<ServerSentEvent<AlertEvent>> =
        notificationService.getAlertStream()
}
