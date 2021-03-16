package com.itkee.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itkee.admin.entity.dto.RoleDTO;
import com.itkee.admin.entity.dto.UserInfoDTO;
import com.itkee.admin.pojo.Role;
import com.itkee.admin.pojo.RoleMenu;
import com.itkee.admin.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author rabbit
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取所有角色
     * @return List<RoleDTO>
     */
    List<RoleDTO> getRoles();

    /**
     * enableMenuWithIds
     * @param ids ids
     * @param enable enable
     * @return int
     */
    int enableRoleWithIds( @Param("ids") String ids ,@Param("enable") int enable);

    /**
     * 根据名字查询是否有重复
     * @param name name
     * @return count
     */
    int selectCountRoleWithName(@Param("name") String name);

    /**
     * 保存角色菜单
     * @param roleMenus roleMenus
     * @return 影响行数
     */
    int insertRoleMenus(@Param("roleMenus") List<RoleMenu> roleMenus);

    /**
     * 删除角色菜单
     * @param roleId roleId
     */
    void deleteRoleMenuByRoleId(Integer roleId);
}
