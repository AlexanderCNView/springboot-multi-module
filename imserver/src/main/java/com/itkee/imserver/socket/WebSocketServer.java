package com.itkee.imserver.socket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itkee.core.annotation.User;
import com.itkee.imserver.connect.ConnectMap;
import com.itkee.imserver.pojo.EventName;
import com.itkee.imserver.pojo.Room;
import com.itkee.imserver.pojo.SendData;
import com.itkee.imserver.pojo.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author rabbit
 */
@ServerEndpoint("/ws/{userId}/{device}")
@Component
public class WebSocketServer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServer.class);

    private String userId;

    private static String avatar = "p1.jpeg";

    /**
     * socket连接
     * @param session
     * @param userId
     * @param device
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("device") String device) {
        User user = ConnectMap.users.get(userId);
        if (user == null) {
            user = new User();
            user.setDevice(device);
            user.setUserId(Integer.valueOf(userId));
        }
        LOG.info("用户登陆:" + userId + ",session:" + session.getId());
        this.userId = userId;
        //加入列表
        ConnectMap.users.put(userId, user);
        ConnectMap.userSessions.put(userId, session);
        // 登陆成功，返回个人信息
        HashMap<String, Object> myMap  = new HashMap<>(2);
        myMap.put("code", StatusCode.SUCCESS);
        myMap.put("event_name",EventName.ON_LOGIN.getTypeName());
        System.out.println(JSON.toJSONString(myMap));
        session.getAsyncRemote().sendText(JSON.toJSONString(myMap));
    }

    /**
     * socket关闭
     */
    @OnClose
    public void onClose() {
        System.out.println(userId + "-->onClose......");
        userExit();
    }

    @OnError
    public void onError(Session session, Throwable t) {
        LOG.error("异常错误---{}",t.getMessage());
        LOG.info("用户{}异常退出", userId);
        /**
         * 解决websocket服务器断开socket 导致@ClientEndPoint生成的WebScoket-AsyncIO线程退出的问题
         * 这里再次建立socket连接
         */
    }


    @OnMessage
    public void onMessage(String message) {
        System.out.println("receive data:" + message);
        handleMessage(message);
    }

    /**
     * 发送消息
     * @param message
     * @param
     */
    private void handleMessage(String message) {
        Map<String, Object> data = JSONObject.parseObject(message);
        switch (data.get("event_name").toString()) {
            case "create_room":
                createRoom(Integer.parseInt(data.get("room_type").toString()));
                break;
            case "call":
                invite(data.get("users").toString());
                break;
            case "up":
                up(data.get("room").toString());
                break;
            case "hang_up":
                reject(data.get("room").toString());
                break;
            case "ice_candidate":
                iceCandidate(data);
                break;
            case "offer":
                offer(data);
                break;
            case "answer":
                answer(data);
                break;
//            case "__leave":
//                leave(message, data.getData());
//                break;
//            case "__audio":
//                transAudio(message, data.getData());
//                break;
//            case "__disconnect":
//                disconnet(message, data.getData());
//                break;
            default:
                break;
        }

    }

    /**
     * 创建房间
     * @param type
     */
    private void createRoom(int type) {
        System.out.println(String.format("创建房间:%s ", this.userId));

        User my = ConnectMap.users.get(this.userId);
        if(my.getCurrentRoomId()==null){
            // 创建房间
            String roomId = UUID.randomUUID().toString();
            //修改当前用户所在房间
            my.setCurrentRoomId(roomId);

            Room room = Room.builder().roomId(roomId).roomType(type).hostId(userId).build();
            // 将房间储存起来
            ConnectMap.rooms.put(roomId,room);
            // 将自己加入到房间里
            CopyOnWriteArrayList<User> copy = new CopyOnWriteArrayList<>();
            copy.add(my);
            room.setUserList(copy);

            HashMap<String, Object> build = SendData.builder()
                    .addParam("roomId", roomId)
                    .addParam("code", StatusCode.SUCCESS)
                    .addParam("event_name", EventName.ON_ROOM_CREATED.getTypeName())
                    .build();
            System.out.println(JSON.toJSONString(build));
            sendMsg(my,JSON.toJSONString(build));
        }
    }

    /**
     * 邀请聊天
     * @param userIds
     */
    private void invite(String userIds) {
        /**
         * 邀请人的用户id
         */
        List<String> ids = JSONObject.parseArray(userIds,String.class);
        /**
         * 当前房间
         */
        User my = ConnectMap.users.get(userId);
        Room room = ConnectMap.rooms.get(my.getCurrentRoomId());

        System.out.println("邀请用户加入聊天");
        // 给其他人发送邀请
        for (String userId : ids) {
            User user = ConnectMap.users.get(userId);
            if (user != null) {
                HashMap<String, Object> build = SendData.builder()
                        .addParam("roomId", room.getRoomId())
                        .addParam("code", StatusCode.SUCCESS)
                        .addParam("event_name", EventName.ON_CALL.getTypeName())
                        .addParam("user", ConnectMap.users.get(this.userId))
                        .build();
                System.out.println(JSON.toJSONString(build));
                sendMsg(user, JSON.toJSONString(build));
            }
        }
    }

    /**
     * 同意接通
     * @param roomId
     */
    private void up(String roomId) {

        Room room = ConnectMap.rooms.get(roomId);
        User my = ConnectMap.users.get(this.userId);
        if(room == null){
            HashMap<String, Object> build = SendData.builder()
                    .addParam("code", StatusCode.FAIR)
                    .addParam("event_name", EventName.ON_JOIN_ROOM.getTypeName())
                    .build();
            System.out.println(JSON.toJSONString(build));
            String str = JSON.toJSONString(build);
            sendMsg(my, str);
            return;
        }
        //设置当前房间id
        my.setCurrentRoomId(roomId);
        List<User> list = room.getUserList();
        HashMap<String, Object> build = SendData.builder()
                .addParam("code", StatusCode.SUCCESS)
                .addParam("event_name", EventName.ON_JOIN_ROOM.getTypeName())
                .addParam("user", room.getHostId())
                .addParam("host", false)
                .build();
        System.out.println(JSON.toJSONString(build));
        String str = JSON.toJSONString(build);
        sendMsg(my, str);

        build = SendData.builder()
                .addParam("code", StatusCode.SUCCESS)
                .addParam("event_name", EventName.ON_JOIN_ROOM.getTypeName())
                .addParam("user", this.userId)
                .addParam("host", true)
                .build();
        str = JSON.toJSONString(build);
        sendMsg(list.get(0), str);

        //把自己加入房间
        list.add(my);
    }

    /**
     * 主动取消/拒绝接听
     * @param roomId
     */
    private void reject(String roomId) {
        Room room = ConnectMap.rooms.get(roomId);
        if(room != null){
            int roomType = room.getRoomType();
            //1v1
            if(roomType == 0){
                //销毁房间
                ConnectMap.rooms.remove(room.getRoomId());
                User host = ConnectMap.users.get(room.getHostId());
                //清空用户的房间id
                host.setCurrentRoomId(null);
                HashMap<String, Object> build = SendData.builder()
                        .addParam("code", StatusCode.SUCCESS)
                        .addParam("event_name", EventName.ON_HUNG_UP.getTypeName())
                        .build();
                System.out.println(JSON.toJSONString(build));
                String str = JSON.toJSONString(build);
                sendMsg(host, str);
            }
        }

    }

    /**
     * 发送offer
     * @param data
     */
    private void offer(Map<String, Object> data) {
        /**
         * 接受者id
         */
        String userId = (String) data.get("user");
        /**
         * 指令
         */
        String sdp = (String) data.get("sdp");

        User receiver = ConnectMap.users.get(userId);

        HashMap<String, Object> build = SendData.builder()
                .addParam("code", StatusCode.SUCCESS)
                .addParam("sdp", sdp)
                .addParam("user", this.userId)
                .addParam("event_name", EventName.ON_OFFER.getTypeName())
                .build();
        String str = JSON.toJSONString(build);
        System.out.println(str);
        sendMsg(receiver, str);
    }

    /**
     * 发送answer
     * @param data
     */
    private void answer(Map<String, Object> data) {
        String userId = (String) data.get("user");
        User user = ConnectMap.users.get(userId);
        String sdp = (String) data.get("sdp");
        if (user == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        HashMap<String, Object> build = SendData.builder()
                .addParam("code", StatusCode.SUCCESS)
                .addParam("sdp", sdp)
                .addParam("user", this.userId)
                .addParam("event_name", EventName.ON_ANSWER.getTypeName())
                .build();
        String str = JSON.toJSONString(build);
        sendMsg(user, str);

    }

    /**
     * 发送ice
     * @param data
     */
    private void iceCandidate(Map<String, Object> data) {
        String userId = (String) data.get("user");
        String sdp_mid = (String) data.get("sdp_mid");
        String sdp_m_line_index = (String) data.get("sdp_m_line_index");
        String sdp = (String) data.get("sdp");
        User user = ConnectMap.users.get(userId);
        if (user == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        HashMap<String, Object> build = SendData.builder()
                .addParam("code", StatusCode.SUCCESS)
                .addParam("sdp", sdp)
                .addParam("sdp_m_line_index", sdp_m_line_index)
                .addParam("sdp_mid", sdp_mid)
                .addParam("user", this.userId)
                .addParam("event_name", EventName.ON_ICE_CANDIDATE.getTypeName())
                .build();
        String str = JSON.toJSONString(build);
        System.out.println(str);

        sendMsg(user, str);
    }

    /*// 切换到语音接听
    private void transAudio(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = MemCons.userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, -1, message);
    }

    // 意外断开
    private void disconnet(String message, Map<String, Object> data) {
        String userId = (String) data.get("userID");
        UserBean userBean = MemCons.userBeans.get(userId);
        if (userBean == null) {
            System.out.println("用户 " + userId + " 不存在");
            return;
        }
        sendMsg(userBean, -1, message);
    }



    // 离开房间
    private void leave(String message, Map<String, Object> data) {
        String room = (String) data.get("room");
        String userId = (String) data.get("fromID");
        if (userId == null) return;
        RoomInfo roomInfo = MemCons.rooms.get(room);
        CopyOnWriteArrayList<UserBean> roomInfoUserBeans = roomInfo.getUserBeans();
        Iterator<UserBean> iterator = roomInfoUserBeans.iterator();
        while (iterator.hasNext()) {
            UserBean userBean = iterator.next();
            if (userId.equals(userBean.getUserId())) {
                continue;
            }
            sendMsg(userBean, -1, message);

            if (roomInfoUserBeans.size() == 1) {
                System.out.println("房间里只剩下一个人");
                if (roomInfo.getMaxSize() == 2) {
                    MemCons.rooms.remove(room);
                }
            }

            if (roomInfoUserBeans.size() == 0) {
                System.out.println("房间无人");
                MemCons.rooms.remove(room);
            }
        }


    }*/


    private static final Object OBJECT = new Object();

    /**
     * 发送消息
     * @param user
     * @param str
     */
    private void sendMsg(User user,String str) {
        Session phoneSession = ConnectMap.userSessions.get(String.valueOf(user.getUserId()));
        if (phoneSession != null && phoneSession.isOpen()) {
            synchronized (OBJECT) {
                try {
                    phoneSession.getBasicRemote().sendText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用户异常退出
     */
    private void userExit(){
        User user = ConnectMap.users.get(userId);
        if (user != null) {
            /**
             * 清空当前所在房间
             */
            Session userSession = ConnectMap.userSessions.get(user.getDevice());
            if (userSession != null) {
                try {
                    userSession.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ConnectMap.users.remove(userId);
                ConnectMap.userSessions.remove(user.getDevice());
            }
            LOG.info("用户离开:" + user.getUserId());
            //判断用户是否在房间内
            if(user.getCurrentRoomId() != null){
                Room room = ConnectMap.rooms.get(user.getCurrentRoomId());
                if(room != null){
                    //1v1 直接释放房间 并通知对方挂断
                    if(room.getRoomType() == 0){
                        dealloc(room);
                    }else{
                        //多人聊天 房间内只剩两个人
                        if(room.userList.size() == 2){
                            dealloc(room);
                        }else{
                            /**
                             * 删除房间内当前用户
                             */
                            room.userList.remove(user);
                            room.userList.forEach(u->{
                                u.setCurrentRoomId(null);
                                HashMap<String, Object> build = SendData.builder()
                                        .addParam("code", StatusCode.SUCCESS)
                                        .addParam("event_name", EventName.ON_HUNG_UP.getTypeName())
                                        .addParam("user", user)
                                        .build();
                                sendMsg(u,JSON.toJSONString(build));
                            });
                        }
                    }
                }
            }
        }
    }

    /**
     * 释放房间
     * @param room
     */
    private void dealloc(Room room){
        ConnectMap.rooms.remove(room.getRoomId());
        room.userList.forEach(u->{
            u.setCurrentRoomId(null);
            HashMap<String, Object> build = SendData.builder()
                    .addParam("code", StatusCode.SUCCESS)
                    .addParam("event_name", EventName.ON_HUNG_UP.getTypeName())
                    .build();
            sendMsg(u,JSON.toJSONString(build));
        });
    }
}