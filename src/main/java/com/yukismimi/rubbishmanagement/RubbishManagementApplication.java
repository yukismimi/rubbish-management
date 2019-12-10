package com.yukismimi.rubbishmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RubbishManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RubbishManagementApplication.class, args);
    }

}
