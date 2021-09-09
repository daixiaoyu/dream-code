package com.dai.iot.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil implements Serializable {

    public static Long getTimeLong(String time) throws Exception {
        return getTime(time).getTime();
    }


    public static Date getTime(String time) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(time);

    }


    public static String getDt(long end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(end);
        return simpleDateFormat.format(date);
    }

    public static String getDt(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(time);
        return simpleDateFormat.format(parse);
    }

}
