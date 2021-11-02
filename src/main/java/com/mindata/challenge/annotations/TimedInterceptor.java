package com.mindata.challenge.annotations;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TimedInterceptor implements HandlerInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(TimedInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		// Obtener la anotación especificada en el método actual
		PersonalTimed loggerAnnotation = method.getAnnotation(PersonalTimed.class);
		// Determinar si existe la anotación actual
		if(loggerAnnotation != null){
			long startTime = System.currentTimeMillis();
			request.setAttribute("startTime",startTime);
			
			StringBuilder strInit = new StringBuilder();
			strInit.append("Method: ");
			strInit.append(method.getName());
			strInit.append("logInit: ");
			strInit.append(startTime);
			
			logger.info (strInit.toString());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		// Obtener la anotación especificada en el método actual
		PersonalTimed loggerAnnotation = method.getAnnotation(PersonalTimed.class);
		// Determinar si existe la anotación actual
		if(loggerAnnotation != null){
			long endTime = System.currentTimeMillis();
			long startTime = (Long) request.getAttribute("startTime");
			long periodTime = endTime - startTime;
			StringBuilder strEnd = new StringBuilder();
			strEnd.append("Method: ");
			strEnd.append(method.getName());
			strEnd.append("logEnd: ");
			strEnd.append(endTime);
			
			logger.info (strEnd.toString());
			
			StringBuilder strDif = new StringBuilder();
			strDif.append("Method: ");
			strDif.append(method.getName());
			strDif.append("difTime: ");
			strDif.append(periodTime);
			
			logger.info (strDif.toString());
		}
	}
}
