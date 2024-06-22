package com.example.contact_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

@SpringBootApplication
@EnableConfigurationProperties
//@EntityScan(basePackages = {"com.entity"})
public class ContactManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagementSystemApplication.class, args);
    }

}
