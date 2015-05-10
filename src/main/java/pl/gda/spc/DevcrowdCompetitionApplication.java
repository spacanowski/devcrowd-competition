package pl.gda.spc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("root-context.xml")
public class DevcrowdCompetitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevcrowdCompetitionApplication.class, args);
    }
}
