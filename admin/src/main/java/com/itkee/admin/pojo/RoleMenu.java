package com.itkee.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

/**
 * @author rabbit
 */
@Data
@Builder
public class RoleMenu {

    @TableId
    private Integer id;

    private Integer roleId;

    private Integer menuId;
}
