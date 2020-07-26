package com.example;

import com.twilio.Twilio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAppApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(UserAppApplication.class, args);
    }

    public static final String ACCOUNT_SID = "ACa321c66de006af792062a22870571f9f";
    public static final String AUTH_TOKEN  = "a6c37b43d4de73c6e1ec9c6b3ad41fce";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("UserAppApplication");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
}
