package cn.com.tcxy.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * UUID生成
 * 
 */
public class UUIDUtil {

    private UUIDUtil() {
    }

    public final static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public final static String getVehicleCode() {
        Random random = new Random();
        return DateUtil.formatPureMiddleDatetime(new Date())
                + RandomUtils.nextInt(random, 10)
                + RandomUtils.nextInt(random, 10);
    }

    public final static String getTimestampUUID() {
        return DateUtil.formatPureLongDatetime(new Date())
                + RandomUtils.nextInt(10);
    }
    
    /**
     * 获取16位主键（日期+4位随机数）
    * @return
    * @author: jiawei
    * @2015年9月21日
     */
    public final static String getRequestId(){
        Date date =  new Date();
        String dateStr = DateFormatUtils.format(date, "yyMMddHHmmss");
        String s = RandomStringUtils.randomNumeric(4);  
        return dateStr+s;
    }
}
