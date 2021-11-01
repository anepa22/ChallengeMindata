package com.mindata.challenge.web;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindata.challenge.annotations.LoggerAnnotation;
import com.mindata.challenge.component.JwtProvider;
import com.mindata.challenge.entity.User;

@RestController
@RequestMapping("/auth")
@Validated
public class SuperHeroeAuthControler {
	@Autowired
	JwtProvider superHeroeUserComp;
	
	@Autowired
    HttpServletResponse response;

	@LoggerAnnotation
	@RequestMapping(path = "/authUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authUSer(@Valid @RequestBody User user) {
		
		String token = superHeroeUserComp.authUSer(user);
		return ResponseEntity.ok()
			      .header(JwtProvider.AUTHORIZATION, token)
			      // Por razones de practicidad lo agrego en el body tambien.
			      .body(token);
	}
	
	@RequestMapping(path = "/refreshToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> refreshToken(@RequestHeader(JwtProvider.AUTHORIZATION) String token) {
		
		String tokenRefresh = superHeroeUserComp.refreshToken(token);
		return ResponseEntity.ok()
			      .header(JwtProvider.AUTHORIZATION, token)
			      // Por razones de practicidad lo agrego en el body tambien.
			      .body(tokenRefresh);
	}
}
