package com.itkee.admin.service;

import com.itkee.admin.entity.dto.RoleDTO;

import java.util.List;

/**
 * @author rabbit
 */
public interface IRoleService {

    /**
     * 获取所有菜单
     * @return
     */
    List<RoleDTO> getRoles();

    /**
     * 保存用户菜单
     * @param ids ids
     * @param roleId roleId
     * @return
     */
    int saveRoleMenu(List<Integer> ids, Integer roleId);
}
