package com.flowShop.spring.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logRequestResponse(requestWrapper, responseWrapper, duration);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequestResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String requestBody = getBody(request.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getBody(response.getContentAsByteArray(), response.getCharacterEncoding());
        int status = response.getStatus();

        log.info("Request [{} {}?{}] | Body: {} | Time: {}ms", method, uri, queryString != null ? queryString : "", requestBody, duration);
        log.info("Response [{} {}] | Status: {} | Body: {}", method, uri, status, responseBody);
    }

    private String getBody(byte[] content, String characterEncoding) {
        if (content == null || content.length == 0) {
            return "[empty]";
        }
        try {
            return new String(content, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            return "[error reading body]";
        }
    }
}
