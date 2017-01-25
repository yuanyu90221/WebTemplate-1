package com.exfantasy.template.monitor;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Spring Boot AOP
 * 
 * 參考: <a href="https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-aop/src/main/java/sample/aop/monitor/ServiceMonitor.java">Sample Code on GitHub</a>
 * 參考: <a href="http://blog.didispace.com/springbootaoplog/">Spring Boot中使用AOP統一處理 Web 請求日誌</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Aspect
@Component
public class ServiceMonitor {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);
	
	private final String ALL_SERVICE_METHOD = "execution(* com.exfantasy.template.services..*Service.*(..))";
	
	@Before(ALL_SERVICE_METHOD)
	public void logAccessServiceBefore(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		// class
		String className = signature.getDeclaringTypeName();
		// method name
		String methodName = signature.getName();
		// args
		String args = Arrays.toString(joinPoint.getArgs());
		
		String logStr = className + "." + methodName + "(" + args + ")";
				
		logger.info("----> Prepare to access: {}", logStr);
	}
	
	
	@AfterReturning(pointcut = ALL_SERVICE_METHOD, returning = "ret")
	public void logAccessServiceAfterReturning(JoinPoint joinPoint, Object ret) {
		Signature signature = joinPoint.getSignature();
		// class
		String className = signature.getDeclaringTypeName();
		// method name
		String methodName = signature.getName();
		// args
		String args = Arrays.toString(joinPoint.getArgs());
		
		String logStr = className + "." + methodName + "(" + args + ")";

		logger.info("<---- Access completed: {}, return: <{}>", logStr, ret);
	}
}
