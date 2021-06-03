package com.spring.fleamarket.global.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.spring.fleamarket.global.security.filter.RestAuthenticationFilter;
import com.spring.fleamarket.global.security.filter.RestAuthorizationFilter;
import com.spring.fleamarket.global.security.handler.RestLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService service;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.httpBasic().disable()
			.formLogin().disable()
			.logout().logoutSuccessHandler(logoutSuccessHandler())
			.and()
			.authorizeRequests()
				.antMatchers("/api/test").hasRole("USER")
//				.antMatchers("/auth/**").hasRole("USER")
			.anyRequest().permitAll()
			.and()
			.addFilter(corsFilter())
			.addFilter(restAuthenticationFilter())
			.addFilter(restAuthorizationFilter());
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public RestAuthenticationFilter restAuthenticationFilter() throws Exception {
		RestAuthenticationFilter filter = new RestAuthenticationFilter();
		filter.setAuthenticationManager(this.authenticationManager());
		return filter;
	}
	
	@Bean
	public RestLogoutSuccessHandler logoutSuccessHandler() {
		return new RestLogoutSuccessHandler();
	}
	
	@Bean 
	public RestAuthorizationFilter restAuthorizationFilter() throws Exception {
		return new RestAuthorizationFilter(this.authenticationManager());
	}
	
	@Bean 
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
