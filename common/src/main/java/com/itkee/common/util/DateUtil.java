package com.itkee.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author rabbit
 */
public class DateUtil {

    /**
     * @return 当前日期字符串
     */
    public static String getCurrentDateString(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ldt.format(dtf);
    }
}
