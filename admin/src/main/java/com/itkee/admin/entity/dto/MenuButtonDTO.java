package com.itkee.admin.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author rabbit
 */
@Data
public class MenuButtonDTO {

    @ApiModelProperty(value = "菜单名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "主键", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(value = "上级主键", dataType = "Integer")
    private Integer parentId;

    private List<MenuButtonDTO> children;
}
