package com.biubiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Haibiao.Zhang on 2019-03-30 15:01
 */
@SpringBootApplication
@EnableEurekaClient
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class);
    }

}
