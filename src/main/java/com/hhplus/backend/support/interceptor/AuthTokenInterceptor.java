package com.hhplus.backend.support.interceptor;

import com.hhplus.backend.domain.exception.NotActivateTokenException;
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
//        log.debug("==================== BEGIN ====================");
//        log.debug("Request URI ===> " + request.getRequestURI());
        // 토큰 검증
        //Long userId = Long.valueOf(request.getSession().getId());
        long userId = 1; // 임의로 값 설정
//        UserToken userToken = queueService.getToken(userId);
//
//        if (!userToken.isActive()) {
//            NotActivateTokenException e = new NotActivateTokenException("Not activate token");
//            log.warn(e.getMessage());
//            return false;
//        };
        
        // redis로 변경
        long active = queueService.getActiveToken(userId);
        if (active < 0) {
            long waitingNo = queueService.getWaitingToken(userId);
            log.info("현재 대기번호는 " + waitingNo + "번 입니다.");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.debug("==================== END ======================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
