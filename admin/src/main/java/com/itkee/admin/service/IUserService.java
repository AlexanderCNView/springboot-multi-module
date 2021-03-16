package com.itkee.admin.service;

import com.itkee.admin.entity.dto.LoginResponse;
import com.itkee.admin.entity.dto.UserInfoDTO;
import com.itkee.core.result.BaseResult;

/**
 * @author rabbit
 */
public interface IUserService {

    /**
     * 管理后台登录
     * getUserByUserNameAndPassword
     * @param username
     * @param password
     * @return
     */
    BaseResult<LoginResponse> login(String username, String password);

    /**
     * 获取用户信息
     * @param userId userId
     * @param roleId roleId
     * @return
     */
    UserInfoDTO getUserInfoByUserId(Integer userId, Integer roleId);


}
