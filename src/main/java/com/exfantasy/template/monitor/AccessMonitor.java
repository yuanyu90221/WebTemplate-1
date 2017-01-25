package com.exfantasy.template.monitor;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
public class AccessMonitor {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessMonitor.class);
	
	@Pointcut("execution(* com.exfantasy.template.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void controllerMethodPointcut() {}
	
	@Pointcut("execution(* com.exfantasy.template.services..*Service.*(..))")
	public void serviceMethodPointcut() {}
	
	/**
	 * 參考: <a href="http://blog.csdn.net/clementad/article/details/52035199">spring boot 使用spring AOP實現攔截器</a>
	 */
	@Around("controllerMethodPointcut()")
	public void logAccessControllerBefore(ProceedingJoinPoint pjp) {
		long beginTime = System.currentTimeMillis();
		
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		// class
		String className = signature.getDeclaringTypeName();
		// method name
		String methodName = signature.getMethod().getName();
		
		Set<Object> allParams = new LinkedHashSet<>();
		
		// TODO
		String logStr = "";
		
		logger.info(">>>>> Prepare to access: {}", logStr);
	}
	
	@Before("serviceMethodPointcut()")
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
	
	@AfterReturning(pointcut = "serviceMethodPointcut()", returning = "ret")
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
