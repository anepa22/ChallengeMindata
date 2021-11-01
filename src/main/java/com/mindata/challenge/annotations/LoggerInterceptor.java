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
public class LoggerInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		// Obtener la anotación especificada en el método actual
		LoggerAnnotation loggerAnnotation = method.getAnnotation(LoggerAnnotation.class);
		// Determinar si existe la anotación actual
		if(loggerAnnotation != null){
			long startTime = System.currentTimeMillis();
			request.setAttribute("startTime",startTime);
			logger.info ("Enter" + method.getName () + "logInit" + startTime);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		Method method = handlerMethod.getMethod();
		// Obtener la anotación especificada en el método actual
		LoggerAnnotation loggerAnnotation = method.getAnnotation(LoggerAnnotation.class);
		// Determinar si existe la anotación actual
		if(loggerAnnotation != null){
			long endTime = System.currentTimeMillis();
			long startTime = (Long) request.getAttribute("startTime");
			long periodTime = endTime - startTime;
			logger.info ("Leave" + method.getName () + "logEnd:" + endTime);
			logger.info ("in" + method.getName () + "totalTime:" + periodTime);
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterCompletion");

	}

}
