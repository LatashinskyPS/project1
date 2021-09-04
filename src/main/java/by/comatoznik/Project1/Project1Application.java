package by.comatoznik.Project1;

import by.comatoznik.Project1.entities.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.List;

@SpringBootApplication
public class Project1Application {
    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(Project1Application.class, args);
    }
}
