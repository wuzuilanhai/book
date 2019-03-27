package com.biubiu.book.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by Haibiao.Zhang on 2019-03-27 14:38
 */
@FeignClient(value = "book-service", path = "/rest", fallback = BookServiceHystrix.class)
public interface BookService {
}
