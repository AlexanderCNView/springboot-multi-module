package com.itkee.imserver.pojo;

import java.util.HashMap;

/**
 * @author rabbit
 */
public class SendData {

    public static SendData.Builder builder() {
        return new SendData.Builder();
    }

    public static class Builder {
        private HashMap<String, Object> data = new HashMap<>(2);
        public Builder addParam(String key,Object value){
            data.put(key,value);
            return this;
        }
        public HashMap<String, Object> build() {
            return data;
        }
    }
}
