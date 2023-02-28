package info.nmrony.spring.tutorials.ms.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "info.nmrony.spring.tutorials.ms")
public class StocksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksApplication.class, args);
    }

}
