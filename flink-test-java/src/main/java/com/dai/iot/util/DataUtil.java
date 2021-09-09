package com.dai.iot.util;


import com.dai.iot.model.OilQualityData;
import com.dai.iot.model.OilQualityType;
import com.dai.iot.model.TemperatureData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class DataUtil implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(DataUtil.class);

    public static Optional<TemperatureData> parseToTem(String dataLine) {

        String s = dataLine.replaceAll(";", "");
        // 因为只有非空的数据才会进入，所以不用特判 空场景
        String[] split = s.split(",");

        if (split.length < TemperatureData.FIELD_COUNT) {
            logger.warn("异常非法数据:{}", dataLine);
            return Optional.empty();
        }


        try {
            TemperatureData temperatureData = new TemperatureData(split[0], split[1], new Float(split[2]), dataLine);
            return Optional.of(temperatureData);
        } catch (Exception e) {
            logger.warn("数据异常:{},异常:{}", dataLine, e);
        }

        return Optional.empty();
    }

    public static Optional<OilQualityData> parseToOil(String dataLine) {
        // 因为只有非空的数据才会进入，所以不用特判 空场景
        String s = dataLine.replaceAll(";", "");
        String[] split = s.split(",");

        if (split.length < OilQualityData.FIELD_COUNT) {
            logger.warn("异常非法数据:{}", dataLine);
            return Optional.empty();
        }

        try {
            Map<String, Float> dataMap = getDataInner(split);
            OilQualityData oilQualityData = new OilQualityData(split[0], split[1], dataMap);
            return Optional.of(oilQualityData);
        } catch (Exception e) {
            logger.warn("数据异常:{},异常:{}", dataLine, e);
        }

        return Optional.empty();

    }

    private static Map<String, Float> getDataInner(String[] split) {
        Map<String, Float> map = new HashMap<>();
        for (int i = 2; i < split.length; i++) {
            String s = split[i];
            String[] splitInner = s.split(":");
            map.put(splitInner[0], new Float(splitInner[1]));
        }
        return map;
    }


    public static Float divide(Float sum, Long count) {
        BigDecimal divide = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        return divide.floatValue();
    }




    public static boolean isWarning(OilQualityData oilQualityData, OilQualityData pre) {

        Map<String, Float> dataMapNow = oilQualityData.getDataMap();
        Map<String, Float> dataMapPre = pre.getDataMap();

        /**
         * 这里将 油品 质量的类型 维护在 枚举以及 BO 的 map容器中，方便后期扩展需要监控的属性
         */
        for (OilQualityType oilQualityType : OilQualityType.values()) {
            String code = oilQualityType.getCode();
            Float nowValue = dataMapNow.get(code);
            Float preValue = dataMapPre.get(code);
            float result = (nowValue - preValue) * 100 / preValue;
            if (result > OilQualityData.MAX_UP) {
                oilQualityData.generateWarningMessage(oilQualityType,String.valueOf(nowValue));
                return true;
            }
        }

        return false;
    }



}
