package com.example.kotlincrud

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
class KotlinCrudApplication

fun main(args: Array<String>) {
    runApplication<KotlinCrudApplication>(*args)
}
