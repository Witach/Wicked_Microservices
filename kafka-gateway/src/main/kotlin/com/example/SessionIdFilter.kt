package com.example

import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SessionIdFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val session = request.getSession()
        if(session.isNew || session.getAttribute("session-id") == null) {
            session.setAttribute("session-id", session.id)
        }
        filterChain.doFilter(request, response);
    }
}