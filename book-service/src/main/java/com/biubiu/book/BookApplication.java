package com.biubiu.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Haibiao.Zhang on 2019-03-27 14:34
 */
@MapperScan(basePackages = "com.biubiu.book.mapper")
@SpringBootApplication
@EnableEurekaClient
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class);
    }

}
