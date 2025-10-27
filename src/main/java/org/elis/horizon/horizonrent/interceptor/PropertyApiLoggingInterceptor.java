package org.elis.horizon.horizonrent.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PropertyApiLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PropertyApiLoggingInterceptor.class);
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                            Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        logger.debug("Incoming request: {} {} from {}",
            request.getMethod(), request.getRequestURI(), request.getRemoteAddr());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                               Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute(START_TIME);
        long duration = System.currentTimeMillis() - startTime;

        logger.info("Completed: {} {} - Status: {} - Duration: {}ms",
            request.getMethod(), request.getRequestURI(), response.getStatus(), duration);

        if (ex != null) {
            logger.error("Request failed with exception", ex);
        }
    }
}
