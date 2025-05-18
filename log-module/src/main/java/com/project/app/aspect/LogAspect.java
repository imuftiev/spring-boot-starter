package com.project.app.aspect;

import com.project.app.config.LogHttpProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogAspect {

    private final LogHttpProperties logHttpProperties;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    private void log(String message) {
        switch (logHttpProperties.getLevel()) {
            case DEBUG -> log.debug(message);
            case WARN-> log.warn(message);
            case ERROR-> log.error(message);
            default-> log.info(message);
        }
    }

    @Around("restControllerMethods()")
    public Object logHttpRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attrs != null;
        HttpServletRequest request = attrs.getRequest();
        HttpServletResponse response = attrs.getResponse();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log("HTTP:Request " + method + " URL: " + uri);

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            log("HTTP:Response " + method + " exception: " + ex.getMessage() + "time:" + duration);
            throw ex;
        }
        long duration = System.currentTimeMillis() - startTime;

        String status = (response != null) ? String.valueOf(response.getStatus()) : "null";

        log("HTTP:Response " + method + " status: " + status + " time: " + duration);

        return result;
    }
}
