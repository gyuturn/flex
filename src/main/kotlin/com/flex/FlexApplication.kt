package com.flex

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FlexApplication

fun main(args: Array<String>) {
    runApplication<FlexApplication>(*args)
}
