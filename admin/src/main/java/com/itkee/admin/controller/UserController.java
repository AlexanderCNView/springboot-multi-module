package com.itkee.admin.controller;
import com.itkee.admin.constant.Message;
import com.itkee.admin.entity.dto.LoginResponse;
import com.itkee.admin.entity.dto.UserInfoDTO;
import com.itkee.admin.service.IUserService;
import com.itkee.core.annotation.CurrentUser;
import com.itkee.core.annotation.PassToken;
import com.itkee.core.annotation.User;
import com.itkee.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;


/**
 * @author rabbit
 */
@RestController
@Slf4j
@Api(tags = {"用户接口"})
public class UserController {

    @Resource
    IUserService iUserService;


    @GetMapping("/user/login/{username}/{password}")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PassToken
    public BaseResult<LoginResponse> userLogin(@NotNull(message = "用户名不能为空") @ApiParam(value = "用户名",required = true) @PathVariable String username,
                                               @NotNull(message = "密码不能为空") @ApiParam(value = "密码(MD5)",required = true) @PathVariable String password){

        return iUserService.login(username, password);
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public BaseResult<UserInfoDTO> getUserInfo(@CurrentUser User currentUser){
        UserInfoDTO sysUser = iUserService.getUserInfoByUserId(currentUser.getUserId(),currentUser.getRoleId());
        return BaseResult.<UserInfoDTO>builder().data(sysUser).msg(Message.GET_USER_INFO_SUCCESS).code(200).build();
    }
}
