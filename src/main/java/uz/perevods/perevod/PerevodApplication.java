package uz.perevods.perevod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ServletComponentScan
@SpringBootApplication
public class PerevodApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerevodApplication.class, args);
    }

}
