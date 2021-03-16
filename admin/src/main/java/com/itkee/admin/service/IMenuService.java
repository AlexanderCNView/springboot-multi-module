package com.itkee.admin.service;

import com.itkee.admin.entity.dto.MenuButtonDTO;
import com.itkee.admin.entity.dto.MenuDTO;

import java.util.List;

/**
 * @author rabbit
 */
public interface IMenuService {

    /**
     * 获取所有菜单
     * @return
     */
    List<MenuDTO> getMenus();

    /**
     * 获取所有菜单按钮
     * @return
     */
    List<MenuButtonDTO> getMenusBtn();

    /**
     * 获取默认选中菜单
     * @param roleId roleId
     * @return list
     */
    List<Integer> getMenuCheckKeys(Integer roleId);

    /**
     * 获取菜单操作按钮
     * @param menuId menuId
     * @return List<MenuButtonDTO>
     */
    List<MenuButtonDTO> getMenuBtns(Integer menuId);
}
