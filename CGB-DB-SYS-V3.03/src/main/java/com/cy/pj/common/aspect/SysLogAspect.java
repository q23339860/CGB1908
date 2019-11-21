package com.cy.pj.common.aspect;



import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SysLogAspect {
	private Logger log=Logger.getLogger(SysLogAspect.class);
	@Autowired
	private SysLogService sysLogService;
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public void dologpointcut() {}
	@Around("dologpointcut()")
	public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
		long startTime=System.currentTimeMillis();
		Object result=joinPoint.proceed();
		long endTime=System.currentTimeMillis();
		long totalTime=endTime-startTime;
		log.info("方法执行总时长为："+totalTime);
		saveSysLog(joinPoint,totalTime);
		return result;
	}
	private void saveSysLog(ProceedingJoinPoint point,long totleTime)throws Exception{
		MethodSignature ms=(MethodSignature)point.getSignature();
		Class<?> targetClass=point.getTarget().getClass();
		String className=targetClass.getName();
		String methodName=ms.getMethod().getName();
		Class<?>[] parameterTypes=ms.getMethod().getParameterTypes();
		Method targetMethod=targetClass.getDeclaredMethod(methodName, parameterTypes);
		String username="S";
		Object[] paramsObj=point.getArgs();
		System.out.println("paramsObj="+paramsObj);
		String params=new ObjectMapper().writeValueAsString(paramsObj);
		SysLog log=new SysLog();
		log.setUsername(username);
		RequiredLog requiredLog=targetMethod.getAnnotation(RequiredLog.class);
		if(requiredLog!=null) {
			log.setOperation(requiredLog.value());
		}
		log.setMethod(className+"."+methodName);
		log.setParams(params);
		log.setIp(IPUtils.getIpAddr());
		log.setTime(totleTime);
		log.setCreatedTime(new Date());
		sysLogService.saveObject(log);
		System.out.println(log);
		}
	}

