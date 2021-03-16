package com.itkee.imserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author rabbit
 */
@SpringBootApplication
@Slf4j
@ComponentScan({"com.itkee.core","com.itkee.imserver","com.itkee.common"})
public class ImserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImserverApplication.class, args);
    }

}
