package com.rajan.springrediscache.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = {"customer"})
public class DefaultCustomerService implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(DefaultCustomerService.class);

    @Override
    @Cacheable(key = "#id")
    public Customer getCustomerById(Long id) {
        log.info(String.format("Getting customer by id %d", id));
        return new Customer("rajan", "rajan44chauhan@gmail.com", id);
    }
}
