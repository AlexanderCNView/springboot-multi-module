package com.itkee.imserver.pojo;

/**
 * @author rabbit
 */

public enum EventName {
    /**
     * 登录
     */
    ON_LOGIN("on_login"),
    /**
     * 登出
     */
    ON_LOGOUT("on_logout"),

    /**
     * 创建房间
     */
    CREATE_ROOM("create_room"),

    /**
     * 房间创建成功
     */
    ON_ROOM_CREATED("on_room_created"),

    /**
     * 呼叫
     */
    CALL("call"),

    /**
     * 通知被呼叫人
     */
    ON_CALL("on_call"),
    /**
     * 挂断
     */
    HUNG_UP("hand_up"),

    /**
     * 通知被挂断
     */
    ON_HUNG_UP("on_hand_up"),

    /**
     * 接听
     */
    UP("up"),

    /**
     * 加入房间
     */
    ON_JOIN_ROOM("on_join_room"),

    OFFER("offer"),

    ON_OFFER("on_offer"),

    ANSWER("answer"),

    ON_ANSWER("on_answer"),

    ICE_CANDIDATE("ice_candidate"),

    ON_ICE_CANDIDATE("on_ice_candidate");

    EventName(String typeName){
        this.typeName = typeName;
    }

    private final String typeName;

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param typeName 类型名称
     */
    public static EventName fromTypeName(String typeName) {
        for (EventName type : EventName.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
