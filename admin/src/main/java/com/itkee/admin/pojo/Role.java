package com.itkee.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer id;

    private String name;

    private String code;

    @TableField(value = "enable", fill = FieldFill.INSERT)
    private Integer enable;

    @TableLogic
    private Integer deleted;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private String createTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private String updateTime;

    @TableField(value = "sort", fill = FieldFill.INSERT)
    private Integer sort;
}
