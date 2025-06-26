package com.jura_stefanovic.zavrsni;

import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.service.DummyDataGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class ZavrsniApplication {
	private static DummyDataGenerator dummyDataGenerator;

	public ZavrsniApplication(DummyDataGenerator dummyDataGenerator) {
		this.dummyDataGenerator = dummyDataGenerator;
	}

	public static void main(String[] args) {
		SpringApplication.run(ZavrsniApplication.class, args);
		System.out.println("""
    			=========================================================================================
				=================================GENERATING CUSTOM USERS=================================
				=========================================================================================
				""");
		dummyDataGenerator.createGymData();
		dummyDataGenerator.createAdminUser();
		dummyDataGenerator.createTrainerUsers();
		dummyDataGenerator.createGymServices();
		dummyDataGenerator.createUsers();
		dummyDataGenerator.createAppointments();
		dummyDataGenerator.createStats();

		System.out.println("""
		╔═════════════════════════════════════════════════╗
		║ 🚀 ✅  APPLICATION STARTED SUCCESSFULLY! ✅ 🚀 ║
		║   Your Spring Boot backend is now running.      ║
		║   Let's build something awesome! 💪          	  ║
		╚═════════════════════════════════════════════════╝
	""");
	}


}
