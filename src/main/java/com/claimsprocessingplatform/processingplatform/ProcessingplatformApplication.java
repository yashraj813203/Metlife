package com.claimsprocessingplatform.processingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProcessingplatformApplication {
	public static void main(String[] args) {
        SpringApplication.run(ProcessingplatformApplication.class, args);
	}
    @GetMapping("/")
    public String healthcheck(){
        return "Successfully run the application";
    }

}
