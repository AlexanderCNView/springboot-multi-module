package com.itkee.imserver.connect;

import com.itkee.core.annotation.User;
import com.itkee.imserver.pojo.Room;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author rabbit
 */
public class ConnectMap {

    /**
     * 在线用户
     */
    public static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    /**
     * 存在的房间
     */
    public static ConcurrentHashMap<String, Room> rooms = new ConcurrentHashMap<>();

    /**
     * 用户session
     */
    public static ConcurrentHashMap<String, Session> userSessions = new ConcurrentHashMap<>();
}
