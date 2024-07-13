package com.hhplus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HhplusConcertReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HhplusConcertReservationServiceApplication.class, args);
	}

}
