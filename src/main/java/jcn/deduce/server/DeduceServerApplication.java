package jcn.deduce.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DeduceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeduceServerApplication.class, args);
	}
}
