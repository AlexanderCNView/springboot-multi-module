package com.itkee.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * @author rabbit
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SysUser {

    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String username;

    @TableLogic
    @Column(name="deleted")
    private int del;

    @Column(name = "enable")
    private int enable;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

}
