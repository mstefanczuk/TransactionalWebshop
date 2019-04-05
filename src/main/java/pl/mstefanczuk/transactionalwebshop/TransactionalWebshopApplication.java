package pl.mstefanczuk.transactionalwebshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionalWebshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionalWebshopApplication.class, args);
    }

}
