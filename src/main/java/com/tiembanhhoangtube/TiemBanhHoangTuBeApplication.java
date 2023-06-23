package com.tiembanhhoangtube;

import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.config.StogareProprties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StogareProprties.class)
public class TiemBanhHoangTuBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiemBanhHoangTuBeApplication.class, args);
	}

	CommandLineRunner init(StogareService stogareService){
		return (args -> {
			stogareService.init();
		});
	}
}
