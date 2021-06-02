package com.spring.fleamarket.global.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:/config/config.properties")
public class ConfigProperties {
	
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}
	
}
