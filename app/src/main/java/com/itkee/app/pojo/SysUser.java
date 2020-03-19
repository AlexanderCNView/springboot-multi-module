package com.itkee.app.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author rabbit
 */
@Data
@Builder
public class SysUser {

    @Column(name = "userId")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @TableLogic
    @Column(name="isDel")
    private int del;
}
