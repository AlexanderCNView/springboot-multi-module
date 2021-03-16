package com.itkee.app.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author rabbit
 */
@Data
@ApiModel(description = "用户登录请求实体")
public class LoginVO {
    @ApiModelProperty(value = "用户名", dataType = "String",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", dataType = "String",required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;

}
