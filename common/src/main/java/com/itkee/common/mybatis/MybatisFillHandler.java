package com.itkee.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.itkee.common.util.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author rabbit
 */
@Component
public class MybatisFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "createTime", String.class, DateUtil.getCurrentDateString());
        strictInsertFill(metaObject, "hidden",Integer.class, 0);
        strictInsertFill(metaObject, "sort", Integer.class,1);
        strictInsertFill(metaObject, "deleted", Integer.class,0);
        strictInsertFill(metaObject, "keeplive",Integer.class, 0);
        strictInsertFill(metaObject, "enable", Integer.class,1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillStrategy(metaObject,"updateTime", DateUtil.getCurrentDateString());
    }
}
