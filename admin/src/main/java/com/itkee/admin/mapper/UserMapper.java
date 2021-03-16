package com.itkee.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itkee.admin.entity.dto.MenuDTO;
import com.itkee.admin.entity.dto.UserInfoDTO;
import com.itkee.admin.pojo.Menu;
import com.itkee.admin.pojo.SysUser;
import com.itkee.admin.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rabbit
 */
@Mapper
public interface UserMapper{
    /**
     * 用户登录
     * @param username username
     * @param password password
     * @return SysUser
     */
    SysUser login(@Param("username") String username, @Param("password") String password);

    /**
     * 获取用户信息
     * @param userId userId
     * @return UserInfoDTO
     */
    UserInfoDTO getUserByUserId(@Param("userId") Integer userId);

    /**
     * 角色查询接口
     * @param userId userId
     * @return UserRole
     */
    UserRole getUserRoleByUserId(@Param("userId") Integer userId);

    /**
     *  获取上帝用户
     * @return UserInfoDTO
     */
    UserInfoDTO getGodUser(@Param("userId") Integer userId);

    /**
     *
     * @return List<MenuDTO>
     */
    List<MenuDTO> getGodMenus();
}
