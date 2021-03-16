package com.itkee.common.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author rabbit
 */
@Configuration
public class RestConfiguration {

    @Resource
    RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }
}




