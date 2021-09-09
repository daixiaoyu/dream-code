package com.dai.iot.model;

import java.io.Serializable;

public enum DeviceType implements Serializable {
    TEMPERATURE("T", "温度传感器"),
    OIL_QUALITY("Q", "油品质量");


    private String code;
    private String name;

    DeviceType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


}
