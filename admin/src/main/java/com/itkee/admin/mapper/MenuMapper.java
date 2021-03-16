package com.itkee.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itkee.admin.entity.dto.MenuButtonDTO;
import com.itkee.admin.entity.dto.MenuDTO;
import com.itkee.admin.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rabbit
 */
public interface MenuMapper  extends BaseMapper<Menu> {

    /**
     * 获取所有菜单
     * @return MenuDTO
     */
    List<MenuDTO> getMenus();

    /**
     * enableMenuWithIds
     * @param ids ids
     * @param enable enable
     * @return int
     */
    int enableMenuWithIds( @Param("ids") String ids ,@Param("enable") int enable);

    /**
     * 根据名字查询是否有重复
     * @param name name
     * @return count
     */
    int selectCountMenuWithName(@Param("name") String name);

    /**
     *  获取所有菜单按钮
     * @return List<MenuDTO>
     */
    List<MenuButtonDTO> getMenusBtnList();

    /**
     * 查询当前选中的菜单
     * @param roleId roleId
     * @return List<Integer>
     */
    List<Integer> getMenuCheckKeys(@Param("roleId") Integer roleId);

    /**
     * 获取菜单所有按钮
     * @param menuId menuId
     * @return List<MenuButtonDTO>
     */
    List<MenuButtonDTO> getMenuBtns(@Param("menuId") Integer menuId);
}
