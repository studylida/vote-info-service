package dgu.se.bananavote.vote_info_service;

import dgu.se.bananavote.vote_info_service.district.DistrictDataUpdater;
import dgu.se.bananavote.vote_info_service.news.News;
import dgu.se.bananavote.vote_info_service.news.NewsCrawler;
import dgu.se.bananavote.vote_info_service.news.NewsCrawlerHan;
import dgu.se.bananavote.vote_info_service.party.PartyDataUpdater;
import dgu.se.bananavote.vote_info_service.candidate.CandidateDataUpdater;
import dgu.se.bananavote.vote_info_service.poll.PollDataUpdater;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VoteInfoServiceApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv("SPRING_SECURITY_USER_PASSWORD"));

		SpringApplication.run(VoteInfoServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			// Get the DistrictDataUpdater bean from the Spring context
			PartyDataUpdater partyDataUpdater = context.getBean(PartyDataUpdater.class);
			DistrictDataUpdater districtDataUpdater = context.getBean(DistrictDataUpdater.class);
			PollDataUpdater pollDataUpdater = context.getBean(PollDataUpdater.class);
			CandidateDataUpdater candidateDataUpdater = context.getBean(CandidateDataUpdater.class);
			NewsCrawlerHan newsCrawler = context.getBean(NewsCrawlerHan.class);


			// Execute the updateDistrictData method
			partyDataUpdater.updatePartyData();
			districtDataUpdater.updateDistrictData();
			pollDataUpdater.updatePollData();
			candidateDataUpdater.updateCandidateData();
			newsCrawler.crawlAndSaveNews();
		};
	}
}
