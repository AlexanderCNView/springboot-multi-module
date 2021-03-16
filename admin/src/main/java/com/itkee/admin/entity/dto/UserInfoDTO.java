package com.itkee.admin.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author rabbit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    @TableField(value = "name")
    private String username;

    @TableField(value = "avatar")
    private String avatar;

    @ApiModelProperty(value = "菜单列表", dataType = "List")
    private List<MenuDTO> menuList;
}
