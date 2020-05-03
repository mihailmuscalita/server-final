package com.ubb.mihail.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EntityScan(basePackages = {"com.ubb.mihail.license.domain"})
@EnableScheduling
public class LicenseapplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseapplicationApplication.class, args);
    }

}
