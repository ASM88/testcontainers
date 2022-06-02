package com.example.testcontainers.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("within(com.example.testcontainers..*)")
	public void applicationPointcut() {
		// Empty Pointcut Definition
	}

	@Around("applicationPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		UUID context = UUID.randomUUID();
		long startTime = System.currentTimeMillis();
		if (log.isDebugEnabled()) {
			log.debug("Enter [{}]: {}.{}() with argument[s] = {}", context, joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			long endTime = System.currentTimeMillis();
			if (log.isDebugEnabled()) {
				log.debug("Exit[{}]: {}.{}() with result = {}. Executed in {}ms.", context, joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result, endTime - startTime);
			}
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}
	}

}
