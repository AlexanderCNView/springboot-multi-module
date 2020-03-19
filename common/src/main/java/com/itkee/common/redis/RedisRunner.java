package com.itkee.common.redis;

import com.itkee.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author rabbit
 */
@Component
@Order(1)
@Slf4j
public class RedisRunner implements ApplicationRunner {

    @Resource
    RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args){
        log.info("redis--- ℹ️ will connect 😊");
        redisUtil.get("redis connect");
    }
}
