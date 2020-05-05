package com.infobeans.coronavirustracker.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingConfiguration {

	Logger logger = LoggerFactory.getLogger(LoggingConfiguration.class);

	@Pointcut(value = "execution(* com.infobeans.coronavirustracker.*.*.*(..) )")
	public void pointCut() {

	}

	@Around("pointCut()")
	public Object centralizeLogging(ProceedingJoinPoint joinPoint) {
		Object object = null;
		ObjectMapper mapper = new ObjectMapper();
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getSimpleName();
		Object[] args = joinPoint.getArgs();
		try {
			logger.info(className + " " + "Class invoked" + " With " + methodName + "() Method " + " With Arguments : "
					+ mapper.writeValueAsString(args));
			object = joinPoint.proceed();
			logger.info("Method " + methodName + "()" + " Returned With " + "Response : "
					+ mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return object;
	}

}