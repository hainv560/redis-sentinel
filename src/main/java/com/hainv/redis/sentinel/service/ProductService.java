package com.hainv.redis.sentinel.service;

import com.hainv.redis.sentinel.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@CacheConfig(cacheNames = "product")
public class ProductService {

    @Cacheable(cacheNames = "shoes",key = "#root.methodName+'['+#id+']'")
    public Product getProductById(Long id){
        log.info("Get Product inside service's method -->" + id);
        return Product.builder()
                .id(id)
                .name("Adidas")
                .size("43")
                .build();
    }
}
