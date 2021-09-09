package com.dai.iot.model;

import java.io.Serializable;

public enum OilQualityType implements Serializable {

    AB("AB", "酸度"),
    AE("AE", "粘稠度"),
    CE("CE", "含水量");


    private String code;
    private String name;

    OilQualityType(String tag, String name) {
        this.code = tag;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
