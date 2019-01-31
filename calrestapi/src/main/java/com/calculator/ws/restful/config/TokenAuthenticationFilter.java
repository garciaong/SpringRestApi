package com.calculator.ws.restful.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.NestedServletException;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private static final Logger log = Logger.getLogger(TokenAuthenticationFilter.class);
	private static final String DSAWS_PROPERTIES = "dsa.properties";
	private static final String AUTH_TOKEN = "security.auth.token";
	private static final String GET_REQUEST = "GET";
	
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		try {
			Properties dsaProp = new Properties();
			InputStream dsaInputStream = SecurityConfig.class.getClassLoader().getResourceAsStream(DSAWS_PROPERTIES);
			dsaProp.load(dsaInputStream);

			String validToken = dsaProp.getProperty(AUTH_TOKEN);
			
			String [] validTokens = validToken.split("\\|");
			Map<String,String> validTokenMap = new HashMap<String,String>();
			
			for(String token:validTokens) {
				token = Base64.encodeBase64String(token.getBytes());
				validTokenMap.put(token, token);
			}
			
			// Extract token from header
			String accessToken = httpRequest.getHeader("REMOTE_TOKEN");
			log.info("[REMOTE_TOKEN received : " + accessToken + "]");
			
			if(GET_REQUEST.equalsIgnoreCase(httpRequest.getMethod())) {
				log.info("[GET request detected, by passing token validation...]");
				chain.doFilter(request, response);
			} else if (validToken == null || "".equals(validToken.trim())) {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,
						"Not token configured");
			} else if (accessToken != null && !"".equals(accessToken.trim()) && validTokenMap.containsKey(accessToken)) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Invalid token sent from client");
			}

		} catch (NestedServletException e) {
			log.error("Servlet exception detected", e);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"This could be an error loading VP/MS JNI library, please check server log for detail");
		} catch (Exception e) {
			log.error("Token validation error found", e);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Server error detected during token validation, please check server log for detail");
		}
	}

}