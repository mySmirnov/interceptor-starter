package io.codero.interceptor.interceptor;

import io.codero.interceptor.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class MetricInterceptor implements HandlerInterceptor {
    private final ProducerService service;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        service.sendMetric(request.getRequestURL().toString(), request.getMethod());
    }
}
