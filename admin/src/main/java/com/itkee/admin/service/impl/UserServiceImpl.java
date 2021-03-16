package com.itkee.admin.service.impl;

import com.itkee.admin.constant.Constant;
import com.itkee.admin.constant.Message;
import com.itkee.admin.entity.dto.LoginResponse;
import com.itkee.admin.entity.dto.MenuDTO;
import com.itkee.admin.entity.dto.UserInfoDTO;
import com.itkee.admin.mapper.UserMapper;
import com.itkee.admin.mapstruct.LoginInfoConvert;
import com.itkee.admin.pojo.SysUser;
import com.itkee.admin.pojo.UserRole;
import com.itkee.admin.service.IUserService;
import com.itkee.core.result.BaseResult;
import com.itkee.core.utils.JwtHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author rabbit
 */
@Service
public class UserServiceImpl implements IUserService{

    @Resource
    UserMapper userMapper;

    @Resource
    JwtHelper jwtHelper;

    @Override
    public BaseResult<LoginResponse> login(String username, String password) {
        Optional<SysUser> sysUser = Optional.ofNullable(userMapper.login(username, password));
        if(sysUser.isPresent()){
            Optional<UserRole> userRole = Optional.ofNullable(userMapper.getUserRoleByUserId(sysUser.get().getId()));
            if(!userRole.isPresent()){
                return BaseResult.<LoginResponse>builder().code(201).msg(Message.USER_ROLE_DISABLE).build();
            }
            if(sysUser.get().getDel() == Constant.DELETED){
                return BaseResult.<LoginResponse>builder().code(201).msg(Message.USER_DELETED).build();
            }
            if(sysUser.get().getEnable() == Constant.DISABLE){
                return BaseResult.<LoginResponse>builder().code(201).msg(Message.USER_DISABLE).build();
            }
            LoginResponse loginResponse = LoginInfoConvert.MAPPER.sysUserToLoginResponse(sysUser.get());
            loginResponse.setToken(jwtHelper.generateToken(String.valueOf(sysUser.get().getId()),String.valueOf(userRole.get().getRoleId()),String.valueOf(userRole)));
            return BaseResult.<LoginResponse>builder().data(loginResponse).code(200).msg(Message.LOGIN_SUCCESS).build();
        }
        return BaseResult.<LoginResponse>builder().code(201).msg(Message.NAME_OR_PWD_ERROR).build();
    }

    @Override
    public UserInfoDTO getUserInfoByUserId(Integer userId, Integer roleId) {
        UserInfoDTO userInfoDTO;
        List<MenuDTO> menus;
        if(roleId == 1 || roleId == 2){
            userInfoDTO = userMapper.getGodUser(userId);
            menus = userMapper.getGodMenus();
        }
        else{
            userInfoDTO = userMapper.getUserByUserId(userId);
            menus = userInfoDTO.getMenuList();
        }

        menus.stream()
                .filter(menu -> Objects.isNull(menu.getParentId()))
                .forEach(menu -> menu.setChildren(getChildrenMenuList(menus,menu.getMenuId())));
        userInfoDTO.setMenuList(menus.stream()
                .filter(menu ->Objects.isNull(menu.getParentId()))
                .collect(Collectors.toList()));
        return userInfoDTO;
    }

    private List<MenuDTO> getChildrenMenuList(List<MenuDTO> menuList,Integer parentId){
       return menuList.stream()
                .filter(menu-> Objects.equals(parentId,menu.getParentId()))
                .collect(Collectors.toList());
    }
}
