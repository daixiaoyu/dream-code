package com.dai.iot.model;

import com.dai.iot.util.DateUtil;

import java.io.Serializable;

public class TemperatureData implements Serializable {
    public static final int FIELD_COUNT = 3;
    public static final int WARNING_THAN_AVERAGE = 5;

    private final String deviceId;
    private final String createTime;
    private final Float value;

    private final Long eventTime;

    private final String source;


    public String getDeviceId() {
        return deviceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Float getValue() {
        return value;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public String getSource() {
        return source;
    }

    public TemperatureData(String deviceId, String createTime, Float value, String dataLine) throws Exception {
        this.deviceId = deviceId;
        this.createTime = createTime;
        this.value = value;
        this.eventTime = DateUtil.getTimeLong(createTime);
        this.source = dataLine;
    }
}
