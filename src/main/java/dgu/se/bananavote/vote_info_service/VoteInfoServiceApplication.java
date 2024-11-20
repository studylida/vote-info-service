package dgu.se.bananavote.vote_info_service;

import dgu.se.bananavote.vote_info_service.district.DistrictDataUpdater;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoteInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VoteInfoServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			// Get the DistrictDataUpdater bean from the Spring context
			DistrictDataUpdater updater = context.getBean(DistrictDataUpdater.class);

			// Execute the updateDistrictData method
			updater.updateDistrictData();
		};
	}
}
