package com.mindata.challenge.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.mindata.challenge.component.JwtProvider;

public class JwtTokenFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
		JwtProvider jwtProvider = new JwtProvider();
		try {
			String token = jwtProvider.getToken(req.getHeader(JwtProvider.AUTHORIZATION));
			if(token != null){
				// Por las caracteristicas del ejercicio solo autentica y no autoriza(roles)
				jwtProvider.validoTokenJWT(token);
				String user = JWT.decode(token).getSubject();
				GrantedAuthority authority = new SimpleGrantedAuthority("myAuthority");
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, token, Arrays.asList(authority));

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		catch (JWTDecodeException e){
			logger.error("fail token mal formado: " + e.getMessage());
		}
		catch (Exception e){
			logger.error("fail en el método doFilter: " + e.getMessage());
		}
		filterChain.doFilter(req, res);
	}
}
