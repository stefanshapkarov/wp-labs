package mk.ukim.finki.wp.lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WpLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(WpLabApplication.class, args);
    }

}
