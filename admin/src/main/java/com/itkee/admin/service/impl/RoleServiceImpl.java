package com.itkee.admin.service.impl;

import com.itkee.admin.entity.dto.RoleDTO;
import com.itkee.admin.mapper.RoleMapper;
import com.itkee.admin.pojo.RoleMenu;
import com.itkee.admin.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rabbit
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRoleMenu(List<Integer> ids, Integer roleId) {
        roleMapper.deleteRoleMenuByRoleId(roleId);
        List<RoleMenu> roleMenus = ids.stream()
                .map(id -> RoleMenu.builder().menuId(id).roleId(roleId).build())
                .collect(Collectors.toList());
        int row = roleMapper.insertRoleMenus(roleMenus);
        return row;
    }
}
