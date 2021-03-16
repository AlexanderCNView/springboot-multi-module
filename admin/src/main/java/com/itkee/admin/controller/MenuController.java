package com.itkee.admin.controller;

import com.itkee.admin.constant.Message;
import com.itkee.admin.entity.dto.MenuButtonDTO;
import com.itkee.admin.entity.dto.MenuDTO;
import com.itkee.admin.mapper.MenuMapper;
import com.itkee.admin.pojo.Menu;
import com.itkee.admin.service.IMenuService;
import com.itkee.core.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author rabbit
 */
@RestController
@Api(tags = {"菜单接口"})
public class MenuController {

    @Resource
    IMenuService iMenuService;

    @Resource
    MenuMapper menuMapper;

    @GetMapping("menus")
    @ApiOperation(value = "获取所有菜单", notes = "获取所有菜单")
    public BaseResult<List<MenuDTO>> getMenus(){
        List<MenuDTO> menuDTOS = iMenuService.getMenus();
        return BaseResult.<List<MenuDTO>>builder().data(menuDTOS).msg(Message.GET_SUCCESS).code(200).build();
    }

    @GetMapping("menu/menus_btn")
    @ApiOperation(value = "获取所有菜单和控制按钮", notes = "获取所有菜单和控制按钮")
    public BaseResult<List<MenuButtonDTO>> getMenusBtn(){
        List<MenuButtonDTO> menuDTOS = iMenuService.getMenusBtn();
        return BaseResult.<List<MenuButtonDTO>>builder().data(menuDTOS).msg(Message.GET_SUCCESS).code(200).build();
    }

    @PutMapping("menu/enable/{id}/{enable}")
    @ApiOperation(value = "启用禁用菜单", notes = "启用禁用菜单")
    public BaseResult<Object> enableMenu(@PathVariable String id, @PathVariable Integer enable){
        int row = menuMapper.enableMenuWithIds(id,enable);
        if(row == 1){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("menu/enable/{enable}")
    @ApiOperation(value = "启用禁用菜单", notes = "启用禁用菜单")
    public BaseResult<Object> enableMenuWithIds(@RequestBody List<String> ids,@PathVariable int enable){
        String inIds = String.join(",", ids);
        int row = menuMapper.enableMenuWithIds(inIds,enable);
        if(row > 0){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("menu/delete")
    @ApiOperation(value = "启用禁用菜单", notes = "启用禁用菜单")
    public BaseResult<Object> deleteMenuWithIds(@RequestBody List<String> ids){
        int row = menuMapper.deleteBatchIds(ids);
        if(row > 0){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("saveMenu")
    @ApiOperation(value = "保存菜单", notes = "保存菜单")
    public BaseResult<Integer> saveMenu(@RequestBody Menu menu){
        int row;
        if(Objects.nonNull(menu.getId())){
            row = menuMapper.updateById(menu);
        }else {
            int count = menuMapper.selectCountMenuWithName(menu.getName());
            if(count == 0){
                row = menuMapper.insert(menu);
            }else {
                return BaseResult.<Integer>builder().data(-1).msg(Message.SAVE_MENU_NAME_ERROR).code(201).build();
            }
        }
        if(row == 1){
            return BaseResult.<Integer>builder().data(menu.getId()).msg(Message.SAVE_SUCCESS).code(200).build();
        }
        return BaseResult.<Integer>builder().data(-1).msg(Message.SAVE_ERROR).code(201).build();
    }

    @GetMapping("menu/getMenuCheckKeys/{roleId}")
    @ApiOperation(value = "获取默认选中菜单", notes = "获取默认选中菜单")
    public BaseResult<List<Integer>> getMenuCheckKeys(@PathVariable Integer roleId){
        List<Integer> defaultKeys = iMenuService.getMenuCheckKeys(roleId);
        return BaseResult.<List<Integer>>builder().data(defaultKeys).msg(Message.GET_SUCCESS).code(200).build();
    }

    @PostMapping("menu/saveMenuBtn")
    @ApiOperation(value = "添加菜单按钮", notes = "添加菜单按钮")
    public BaseResult<Integer> saveMenuBtn(@RequestBody Menu menu){
        int row = menuMapper.insert(menu);
        if(row == 1){
            return BaseResult.<Integer>builder().data(menu.getId()).msg(Message.SAVE_SUCCESS).code(200).build();
        }
        return BaseResult.<Integer>builder().data(-1).msg(Message.SAVE_ERROR).code(201).build();
    }

    @GetMapping("menu/getMenuBtns/{menuId}")
    @ApiOperation(value = "获取菜单按钮", notes = "获取菜单按钮")
    public BaseResult<List<MenuButtonDTO>> getMenuBtns(@PathVariable Integer menuId){
        List<MenuButtonDTO> menuButtons = iMenuService.getMenuBtns(menuId);
        return BaseResult.<List<MenuButtonDTO>>builder().data(menuButtons).msg(Message.GET_SUCCESS).code(200).build();
    }
    @DeleteMapping("menu/deleteMenuBtn/{menuId}")
    @ApiOperation(value = "删除菜单按钮", notes = "删除菜单按钮")
    public BaseResult<Object> deleteMenuBtn(@PathVariable Integer menuId){
        int row = menuMapper.deleteById(menuId);
        if(row > 0){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }
}
