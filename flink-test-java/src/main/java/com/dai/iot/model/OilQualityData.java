package com.dai.iot.model;

import com.dai.iot.util.DateUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class OilQualityData implements Serializable {


    public static final int FIELD_COUNT = 5;

    public static final int MAX_UP = 10;

    private final String deviceId;
    private final String createTime;

    private final Long eventTime;
    private final Map<String, Float> dataMap;

    private String warningMsg;
    private String waringType;

    public OilQualityData(String deviceId, String createTime, Map<String, Float> dataMap) throws Exception {
        this.deviceId = deviceId;
        this.createTime = createTime;
        this.eventTime = DateUtil.getTimeLong(this.createTime);
        this.dataMap = dataMap;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCreateTime() {
        return createTime;
    }


    public Map<String, Float> getDataMap() {
        return dataMap;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public String getWaringType() {
        return waringType;
    }

    public void setWaringType(String waringType) {
        this.waringType = waringType;
    }

    public void appendWarning(String times) {
        this.warningMsg = this.warningMsg + " " + times + waringType + "过高";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OilQualityData)) return false;
        OilQualityData that = (OilQualityData) o;
        return getDeviceId().equals(that.getDeviceId()) && getCreateTime().equals(that.getCreateTime()) && getEventTime().equals(that.getEventTime()) && getWarningMsg().equals(that.getWarningMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getCreateTime(), getEventTime(), getWarningMsg());
    }


    public void generateWarningMessage(OilQualityType oilQualityType, String nowValue) {
        StringBuilder waringMessageBuilder = new StringBuilder(this.deviceId);

        waringMessageBuilder
                .append(",")
                .append(this.createTime)
                .append(",")
                .append(oilQualityType.getName()).append(":")
                .append(nowValue);

        this.warningMsg = waringMessageBuilder.toString();
        this.waringType = oilQualityType.getName();
    }


}
