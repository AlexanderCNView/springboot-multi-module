package com.itkee.admin.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author rabbit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

    @ApiModelProperty(value = "菜单名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "菜单地址", dataType = "String")
    private String path;

    @ApiModelProperty(value = "菜单图标", dataType = "String")
    private String icon;

    @ApiModelProperty(value = "主键", dataType = "Integer")
    private Integer menuId;

    @ApiModelProperty(value = "父级名称", dataType = "String")
    private String parentName;

    @ApiModelProperty(value = "是否可用", dataType = "Integer")
    private Integer enable;

    @ApiModelProperty(value = "编码", dataType = "String")
    private String code;

    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;

    @ApiModelProperty(value = "上级主键", dataType = "Integer")
    private Integer parentId;

    @ApiModelProperty(value = "是否可用", dataType = "Integer")
    private Integer hidden;

    @ApiModelProperty(value = "保存状态", dataType = "Integer")
    private Integer keeplive;

    private Integer sort;

    private Boolean hasChild;

    private List<MenuDTO> children;

}
