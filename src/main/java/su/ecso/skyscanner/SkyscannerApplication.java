package su.ecso.skyscanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import su.ecso.skyscanner.service.PriceAirplaneService;

import java.time.LocalDate;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SkyscannerApplication {

	@Autowired
	private PriceAirplaneService service;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Executor threadPoolExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(15);
		executor.setThreadNamePrefix("Skyscanner-Thread-");
		executor.setQueueCapacity(2);
		executor.initialize();
		return executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(SkyscannerApplication.class, args);
	}

	@Scheduled(cron = "${cronTimer}")
	@Async("threadPoolExecutor")
	public void processTravelsWrapper() {
		service.processTravels(LocalDate.now());
	}
}
