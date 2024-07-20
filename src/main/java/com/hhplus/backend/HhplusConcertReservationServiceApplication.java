package com.hhplus.backend;

import com.hhplus.backend.support.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HhplusConcertReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhplusConcertReservationServiceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<LoggingFilter> loggingFilter() {
		FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new LoggingFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

}
