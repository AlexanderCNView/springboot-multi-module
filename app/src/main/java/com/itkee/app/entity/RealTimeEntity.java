package com.itkee.app.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rabbit
 */

@Data
public class RealTimeEntity implements Serializable {

    private String errcode;
    private String data;
    private String errmsg;
    private String state;

}
