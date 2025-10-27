package org.elis.horizon.horizonrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;

@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public class HorizonRentApplication {

    public static void main(String[] args) {
        SpringApplication.run(HorizonRentApplication.class, args);
    }

}
