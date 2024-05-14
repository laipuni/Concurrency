package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
@Component
public class OptimisticAop {

    @Value("#{environment['Optimistic.Retry.Max']}")
    private int maxCount;

    @Around("@annotation(com.example.shopproject.domain.order.service.OptimisticRetry)")
    public Object executeAgain(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Exception exception = null;
        Random random = new Random();
        for (int i = 0; i < maxCount; i++) {
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException | StaleObjectStateException e){
                log.info("try = {}, exception = {}, message = {}",i,e.getCause(),e.getMessage());
                exception = e;
                Thread.sleep(random.nextInt(10,1000));
            }
        }
        return exception;
    }

}
