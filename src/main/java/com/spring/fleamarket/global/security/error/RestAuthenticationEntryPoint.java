package com.spring.fleamarket.global.security.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.fleamarket.global.error.model.ErrorResponse;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, authException.getMessage());
		
		response.setStatus(errorResponse.getStatusCode());
		response.getWriter().print(objectMapper.writeValueAsString(errorResponse));;
	}

}
