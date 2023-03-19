package com.example

import org.springframework.http.MediaType
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpSession

class SSEConnectionStorage {
     val connections: ConcurrentHashMap<String, SseEmitter> = ConcurrentHashMap()

    fun addConnection(session: HttpSession, emitter: SseEmitter) {
        session.getAttribute("session-id")?.let {
            connections["X"] = emitter
            emitter.onCompletion{ connections.remove("X") }
            emitter.onTimeout{ connections.remove("X") }
            emitter.onError{ connections.remove("X") }
        }

    }

    fun removeConnection(session: HttpSession) {
        session.getAttribute("session-id")?.let {
            connections.remove("X")
        }
    }

     fun sendData(session: String, data: String) {
         connections["X"]?.send(data, MediaType.APPLICATION_JSON)
     }
}

