package com.itkee.admin.controller;

import com.itkee.admin.constant.Message;
import com.itkee.admin.entity.dto.RoleDTO;
import com.itkee.admin.mapper.RoleMapper;
import com.itkee.admin.pojo.Role;
import com.itkee.admin.service.IRoleService;
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
@Api(tags = {"角色接口"})
public class RoleController {

    @Resource
    IRoleService iRoleService;

    @Resource
    RoleMapper roleMapper;

    @GetMapping("roles")
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色")
    public BaseResult<List<RoleDTO>> getRoles(){
        List<RoleDTO> roles = iRoleService.getRoles();
        return BaseResult.<List<RoleDTO>>builder().data(roles).msg(Message.GET_SUCCESS).code(200).build();
    }

    @PutMapping("role/enable/{id}/{enable}")
    @ApiOperation(value = "启用禁用角色", notes = "启用禁用角色")
    public BaseResult<Object> enableMenu(@PathVariable String id, @PathVariable Integer enable){
        int row = roleMapper.enableRoleWithIds(id,enable);
        if(row == 1){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("role/enable/{enable}")
    @ApiOperation(value = "启用禁用角色", notes = "启用禁用角色")
    public BaseResult<Object> enableMenuWithIds(@RequestBody List<String> ids,@PathVariable int enable){
        String inIds = String.join(",", ids);
        int row = roleMapper.enableRoleWithIds(inIds,enable);
        if(row > 0){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("role/delete")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public BaseResult<Object> deleteMenuWithIds(@RequestBody List<String> ids){
        int row = roleMapper.deleteBatchIds(ids);
        if(row > 0){
            return BaseResult.builder().msg(Message.OPERATION_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.OPERATION_ERROR).code(201).build();
    }

    @PostMapping("saveRole")
    @ApiOperation(value = "保存角色", notes = "保存角色")
    public BaseResult<Integer> saveMenu(@RequestBody Role role){
        int row;
        if(Objects.nonNull(role.getId())){
            row = roleMapper.updateById(role);
        }else {
            int count = roleMapper.selectCountRoleWithName(role.getName());
            if(count == 0){
                row = roleMapper.insert(role);
            }else {
                return BaseResult.<Integer>builder().data(-1).msg(Message.SAVE_ROLE_NAME_ERROR).code(201).build();
            }
        }
        if(row == 1){
            return BaseResult.<Integer>builder().data(role.getId()).msg(Message.SAVE_SUCCESS).code(200).build();
        }
        return BaseResult.<Integer>builder().data(-1).msg(Message.SAVE_ERROR).code(201).build();
    }

    @PostMapping("role/saveRoleMenu/{roleId}")
    @ApiOperation(value = "保存角色", notes = "保存角色")
    public BaseResult<Object> saveRoleMenu(@RequestBody List<Integer> ids,@PathVariable Integer roleId){
        int row = iRoleService.saveRoleMenu(ids,roleId);
        if(row > 0){
            return BaseResult.builder().msg(Message.SAVE_SUCCESS).code(200).build();
        }
        return BaseResult.builder().msg(Message.SAVE_ERROR).code(201).build();
    }
}
