package com.pol.payment_service.client;

import com.pol.payment_service.dto.CoursePriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @GetMapping("/product-service/courses/price/{id}")
    CoursePriceDTO getCoursePriceById(@PathVariable("id")UUID id);
}