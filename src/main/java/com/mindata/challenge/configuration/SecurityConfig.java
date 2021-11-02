package com.mindata.challenge.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mindata.challenge.security.JwtEntryPoint;
import com.mindata.challenge.security.JwtTokenFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable CORS
        http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/**",
				"/v2/api-docs", 
                "/swagger-resources/**",  
                "/swagger-ui.html",
                "/webjars/**",
                "/actuator/**").permitAll() //permitimos el acceso a /auth a cualquiera
		.anyRequest().authenticated() //cualquier otra peticion requiere autenticacion
		.and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
