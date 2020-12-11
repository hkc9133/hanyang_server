package com.hanyang.startup.hanyangstartup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class HanyangStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanyangStartupApplication.class, args);
    }

}
