package com.biubiu.present.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by Haibiao.Zhang on 2019-03-27 14:38
 */
@FeignClient(value = "presnet-service", path = "/rest", fallback = PresentServiceHystrix.class)
public interface PresentService {
}
