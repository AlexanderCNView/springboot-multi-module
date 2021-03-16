package com.itkee.admin;

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
@ComponentScan({"com.itkee.core","com.itkee.admin","com.itkee.common"})
@MapperScan("com.itkee.admin.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
