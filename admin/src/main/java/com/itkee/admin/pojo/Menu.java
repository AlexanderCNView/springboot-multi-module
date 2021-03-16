package com.itkee.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author rabbit
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("menu")
public class Menu {

    @ApiModelProperty(value = "主键", dataType = "Integer")
    @TableId
    @TableField(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单名称", dataType = "String")
    private String name;

    @ApiModelProperty(value = "菜单图标", dataType = "String")
    private String icon;

    @ApiModelProperty(value = "唯一码", dataType = "String")
    private String code;

    @ApiModelProperty(value = "跳转路径", dataType = "String")
    private String path;

    @ApiModelProperty(value = "保存状态", dataType = "Boolean")
    @TableField(value = "keeplive",fill = FieldFill.INSERT)
    private Integer keeplive;

    @ApiModelProperty(value = "上级Id", dataType = "Integer")
    @TableField(value = "parentId")
    private Integer parentId;

    @ApiModelProperty(value = "创建时间", dataType = "String")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private String updateTime;

    @TableField(value = "enable",fill = FieldFill.INSERT)
    private Integer enable;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;

    @TableField(value = "sort",fill = FieldFill.INSERT)
    private Integer sort;

    @ApiModelProperty(value = "是否可用", dataType = "Integer")
    @TableField(value = "hidden",fill = FieldFill.INSERT)
    private Integer hidden;
}
