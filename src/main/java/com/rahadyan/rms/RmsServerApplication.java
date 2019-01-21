package com.rahadyan.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 
 * @author Rahadyan_W995
 *
 */

@EntityScan(basePackageClasses = { 
		RmsServerApplication.class,
		Jsr310JpaConverters.class 
})
@SpringBootApplication
public class RmsServerApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RmsServerApplication.class, args);
	}

}

