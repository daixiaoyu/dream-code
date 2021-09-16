package com.dai.flink.test;

import com.dai.flink.model.OilQualityData;
import com.dai.flink.model.OilQualityType;
import com.dai.flink.util.DataUtil;
import com.dai.flink.util.DateUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataUtilTest {

    @Test
    public void testDivide() {
        Float divide = DataUtil.divide(76F, 3L);
        Assert.assertEquals(divide, 25.33F);
    }

    @Test
    public void testParseOil() {
        Optional<OilQualityData> oilQualityData = DataUtil.parseToOil("Q1,2020-01-30 19:30:10,AB:37.8,AE:100,CE:0.11;");
    }


    @Test
    public void testIsWaringWillWaringWhenNowIsMoreThanPre() throws Exception {

        Map<String, Float> preData = new HashMap<>();
        Map<String, Float> nowData = new HashMap<>();

        for (OilQualityType oilQualityType : OilQualityType.values()) {
            preData.put(oilQualityType.getCode(), 10.00F);
            nowData.put(oilQualityType.getCode(), 50.00F);
        }

        OilQualityData pre = new OilQualityData("1", "2021-01-01 01:00:00", preData);
        OilQualityData now = new OilQualityData("1", "2021-01-01 01:00:00", nowData);

        boolean warning = DataUtil.isWarning(now, pre);

        String warningMsg = now.getWarningMsg();

        Assert.assertEquals(warning, true);

        Assert.assertEquals("1,2021-01-01 01:00:00,酸度:50.0", warningMsg);
    }


    @Test
    public void testIsWaringNoneWaringWhenNowNotMoreThanPre() throws Exception {
//        String deviceId, String createTime, Map<String, Float> dataMap)

        Map<String, Float> preData = new HashMap<>();
        Map<String, Float> nowData = new HashMap<>();

        for (OilQualityType oilQualityType : OilQualityType.values()) {
            preData.put(oilQualityType.getCode(), 48.00F);
            nowData.put(oilQualityType.getCode(), 50.00F);
        }

        OilQualityData pre = new OilQualityData("1", "2021-01-01 01:00:00", preData);
        OilQualityData now = new OilQualityData("1", "2021-01-01 01:00:00", nowData);

        boolean warning = DataUtil.isWarning(now, pre);

        Assert.assertEquals(warning, false);

        Assert.assertNull(now.getWarningMsg());
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();


    @Test
    public void test2(){
        unsafe.compareAndSwapInt(this,1,1,1);
    }


}
