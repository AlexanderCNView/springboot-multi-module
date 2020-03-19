package com.itkee.app;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author rabbit
 */
@SpringBootApplication
@Slf4j
@ComponentScan({"com.itkee.core","com.itkee.app","com.itkee.common"})
@MapperScan("com.itkee.app.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
