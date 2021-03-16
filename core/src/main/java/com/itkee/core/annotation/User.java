package com.itkee.core.annotation;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
public class User {

    @JSONField(serializeUsing= ToStringSerializer.class)
    private Integer userId;

    private Integer roleId;

    private String phone;

    private String device;

    private String currentRoomId;
}
