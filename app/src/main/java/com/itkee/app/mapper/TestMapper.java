package com.itkee.app.mapper;

import com.itkee.app.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author rabbit
 */
@Mapper
public interface TestMapper {

    /**
     * 根据用户id获取用户信息
     * @param id 主键
     * @return SysUser
     */
    Optional<SysUser> findById(@Param("id") int id);
}
