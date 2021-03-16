package com.itkee.app.controller;
import com.itkee.app.entity.vo.LoginVO;
import com.itkee.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @author rabbit
 */
@RestController
@Slf4j
@Api(tags = {"用户接口"})
public class SmartUserController {

    @GetMapping("/user/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public BaseResult userLogin(@ApiParam(value = "请求参数") @RequestBody LoginVO loginVO){
        return null;
    }
}
