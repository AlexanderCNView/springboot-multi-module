package com.itkee.admin.mapstruct;

import com.itkee.admin.entity.dto.LoginResponse;
import com.itkee.admin.pojo.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author rabbit
 */
@Mapper
public interface LoginInfoConvert {

    LoginInfoConvert MAPPER = Mappers.getMapper(LoginInfoConvert.class);
    /**
     * sysUserToLoginResponse
     * @param sysUser
     * @return
     */
    LoginResponse sysUserToLoginResponse(SysUser sysUser);
}
