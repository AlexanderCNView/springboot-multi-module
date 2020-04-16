package com.itkee.app.controller;
import com.alibaba.fastjson.JSON;
import com.itkee.app.entity.RealTimeEntity;
import com.itkee.common.util.RedisUtil;
import com.itkee.core.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author rabbit
 */
@RestController
@Slf4j
public class FlowHttpController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RestTemplate restTemplate;

    @Value("${project.web-url}")
    private String webUrl;

    @GetMapping("/getRealTimeInfo/{routerId}")
    public BaseResult<RealTimeEntity> getRealTimeInfo(@PathVariable int routerId){
        String redisKey =  "car-" + routerId;
        Optional<RealTimeEntity> carRouterData = redisUtil.get(redisKey);
        if(carRouterData.isPresent()){
            return BaseResult.<RealTimeEntity>builder().code(100).data(carRouterData.get()).build();
        }else {
            MultiValueMap<String, Integer> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("routeId", routerId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, Integer>> httpEntity = new HttpEntity<>(paramMap, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(webUrl, httpEntity, String.class);
            RealTimeEntity realTimeEntity = JSON.parseObject(stringResponseEntity.getBody(), RealTimeEntity.class);
            redisUtil.set(redisKey, realTimeEntity, 50L);
            return BaseResult.<RealTimeEntity>builder().code(100).data(realTimeEntity).build();
        }
    }
}
