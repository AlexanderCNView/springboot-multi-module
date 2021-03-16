package com.itkee.admin.entity.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(description = "登录返回结果集")
public class LoginResponse {

    @ApiModelProperty(value = "用户名", dataType = "String")
    private String username;

    @ApiModelProperty(value = "头像", dataType = "String")
    private String avatar;

    @ApiModelProperty(value = "密钥", dataType = "String")
    private String token;
}
