package com.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalTime
import java.util.*
import javax.servlet.http.HttpSession

@RestController
class SSEController(val sessions: SSEConnectionStorage) {

    @GetMapping("/sse")
    fun sse(session: HttpSession): SseEmitter {
        val sse = SseEmitter(3600000)
        sse.send(
            SseEmitter.event()
                .data("SSE MVC - " + LocalTime.now().toString())
                .id(UUID.randomUUID().toString())
                .name("sse event - mvc")
        )
        sessions.addConnection(session, sse)
        return sse
    }
}