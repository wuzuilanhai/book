package com.biubiu.present;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Haibiao.Zhang on 2019-03-27 14:54
 */
@MapperScan(basePackages = "com.biubiu.present.mapper")
@SpringBootApplication
@EnableEurekaClient
public class PresentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentApplication.class);
    }

}
