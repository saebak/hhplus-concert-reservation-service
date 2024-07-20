package com.hhplus.backend.support.filter;

import com.hhplus.backend.support.filter.wrapper.CustomHttpServletRequestWrapper;
import com.hhplus.backend.support.filter.wrapper.CustomHttpServletResponseWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
        CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper);
            logResponse(responseWrapper);
        }
    }

    private void logRequest(CustomHttpServletRequestWrapper request) {
        StringBuilder sb = new StringBuilder();
        sb.append("REQUEST ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");
        sb.append("Headers: ").append(request.getHeaderNames()).append("\n");
        sb.append("Body: ").append(request.getBody()).append("\n");
        System.out.println(sb.toString());
    }

    private void logResponse(CustomHttpServletResponseWrapper response) {
        StringBuilder sb = new StringBuilder();
        sb.append("RESPONSE ").append(response.getStatus()).append("\n");
        sb.append("Headers: ").append(response.getHeaderNames()).append("\n");
        sb.append("Body: ").append(response.getBody()).append("\n");
        System.out.println(sb.toString());
    }
}
