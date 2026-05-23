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
import java.nio.charset.StandardCharsets;

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
        
        String requestContentType = request.getContentType();
        String requestBody = isTextContent(requestContentType) 
                ? getBody(request.getContentAsByteArray(), request.getCharacterEncoding())
                : "[binary content: " + requestContentType + "]";

        String responseContentType = response.getContentType();
        String responseBody = isTextContent(responseContentType)
                ? getBody(response.getContentAsByteArray(), response.getCharacterEncoding())
                : "[binary content: " + responseContentType + "]";
                
        int status = response.getStatus();

        log.info("Request [{} {}?{}] | Body: {} | Time: {}ms", method, uri, queryString != null ? queryString : "", requestBody, duration);
        log.info("Response [{} {}] | Status: {} | Body: {}", method, uri, status, responseBody);
    }

    private boolean isTextContent(String contentType) {
        if (contentType == null) return true; // Assume text/json if not specified
        return contentType.contains("application/json") || 
               contentType.contains("text/") || 
               contentType.contains("application/xml") ||
               contentType.contains("application/x-www-form-urlencoded");
    }

    private String getBody(byte[] content, String characterEncoding) {
        if (content == null || content.length == 0) {
            return "[empty]";
        }
        
        // Simple heuristic to avoid logging large binary data (like images)
        if (content.length > 10000) {
            return "[body too large or binary]";
        }

        try {
            return new String(content, characterEncoding != null ? characterEncoding : StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            return new String(content, StandardCharsets.UTF_8);
        }
    }
}
