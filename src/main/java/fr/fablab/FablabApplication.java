package fr.fablab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class FablabApplication {

    public static void main(String[] args) {
        SpringApplication.run(FablabApplication.class, args);
    }

    /** L'horloge est injectée : les tests fixent la date du jour. */
    @Bean
    Clock horloge() {
        return Clock.systemDefaultZone();
    }
}
