package ru.skypro.courswork.springboot.auction.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);


    @Around("execution(* ru.skypro.courswork.springboot.auction.service.*.*.*(..))")
    public Object logEmployeeServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        if (args.length != 0){
            LOGGER.info("Was invoked method: {} that take: {}.",methodName,Arrays.toString(args));
        }
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod != null){
            LOGGER.info("Was invoked method: {} that returns: {}.",methodName,returnedByMethod );
        }
        return returnedByMethod;
    }

    @Around("execution(* ru.skypro.courswork.springboot.auction.repository.*.*(..))")
    public Object logEmployeeRepositories(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object returnedByMethod = proceedingJoinPoint.proceed();
        if (returnedByMethod == null){
            LOGGER.debug("Successfully invoked method: {}.",methodName);
        }
        else {
            LOGGER.debug("Successfully invoked method: {} and returns: {}.",methodName,returnedByMethod);
        }

        return returnedByMethod;
    }
}