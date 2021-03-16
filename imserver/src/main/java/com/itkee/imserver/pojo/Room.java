package com.itkee.imserver.pojo;


import com.itkee.core.annotation.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rabbit
 */
@Data
@Builder
public class Room {

    public String roomId;

    public int maxSize;

    public List<User> userList;

    public String hostId;

    /**
     * -1 直播 0 点对点视频 1多人会议
     */
    public int roomType;

}
