package com.itkee.app.controller;
import com.google.common.collect.ImmutableList;
import com.itkee.app.mapper.TestMapper;
import com.itkee.app.pojo.SysUser;
import com.itkee.common.util.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author rabbit
 */
@RestController
public class TestController {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TestMapper mapper;

    @GetMapping("/test")
    public String test(@RequestParam int id){

        Optional<SysUser> sysUser = mapper.findById(id);

        ImmutableList<String> list = ImmutableList.of("1","2","3");

        list.stream().filter(i -> Integer.parseInt(i) > 2 ).forEach(System.out::print);

        if(sysUser.isPresent()){
            return "222";
        }

        return "123";
    }

}
