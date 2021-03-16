package com.itkee.admin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO{

    private Integer roleId;

    private Integer enable;

    private String name;

    private String code;

    private String createTime;

}
