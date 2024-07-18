package com.hhplus.backend.support.interceptor;

import com.hhplus.backend.domain.queue.QueueService;
import com.hhplus.backend.domain.queue.UserToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class AuthTokenInterceptor implements HandlerInterceptor {

    private final QueueService queueService;

    public AuthTokenInterceptor(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================================");
        log.debug("==================== BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());

        // 포인트관련 api는 검증 제외
        String reqUrl = request.getRequestURI();
        if("/user/*".equalsIgnoreCase(reqUrl)){
            return true;
        }
        // 토큰 검증
        Long userId = Long.valueOf(request.getSession().getId());
        UserToken userToken = queueService.getToken(userId);

        if (!userToken.isActive()) {
            return false;
        };

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("==================== END ======================");
        log.debug("===============================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
