package com.itkee.admin.service.impl;

import com.itkee.admin.entity.dto.MenuButtonDTO;
import com.itkee.admin.entity.dto.MenuDTO;
import com.itkee.admin.mapper.MenuMapper;
import com.itkee.admin.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rabbit
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenus() {
        return menuMapper.getMenus();
    }

    @Override
    public List<MenuButtonDTO> getMenusBtn() {
        List<MenuButtonDTO> menus = menuMapper.getMenusBtnList();
        List<MenuButtonDTO> finalMenus = menus;
        menus.forEach(menu -> menu.setChildren(getChildrenMenuList(finalMenus,menu.getId())));
        menus = menus.stream()
                .filter(menu -> Objects.isNull(menu.getParentId()))
                .collect(Collectors.toList());

        return menus;
    }

    @Override
    public List<Integer> getMenuCheckKeys(Integer roleId) {
        return menuMapper.getMenuCheckKeys(roleId);
    }

    @Override
    public List<MenuButtonDTO> getMenuBtns(Integer menuId) {
        return menuMapper.getMenuBtns(menuId);
    }

    private List<MenuButtonDTO> getChildrenMenuList(List<MenuButtonDTO> menuList,Integer parentId){
        return menuList.stream()
                .filter(menu-> Objects.equals(parentId,menu.getParentId()))
                .collect(Collectors.toList());
    }
}
