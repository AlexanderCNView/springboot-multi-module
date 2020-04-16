package com.itkee.app.entity;

import lombok.Data;

@Data
public class BusPosition {
    private double lat;
    private double lng;
    private int position;
    private int positionDetail;

}