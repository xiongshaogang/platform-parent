package cn.com.tcxy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.common.base.Strings;

/**
 * 日期格式化
 * 
 */
public final class DateUtil {

    public static final String LONG_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String PURE_LONG_DATE_TIME_FORMAT_STR = "yyyyMMddHHmmssSSS";

    public static final String STANDARD_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_DATE_TIME_FORMAT_STR_EXT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String PURE_STANDARD_DATE_TIME_FORMAT_STR = "yyyyMMddHHmmss";
    
    public static final String STANDARD_DATE_TIME_ZONE_FORMAT_STR ="yyyy-MM-dd HH:mm:ss Z"; 

    public static final String MIDDLE_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm";
    public static final String PURE_MIDDLE_DATE_TIME_FORMAT_STR = "yyyyMMddHHmm";

    public static final String SHORT_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH";
    public static final String PURE_SHORT_DATE_TIME_FORMAT_STR = "yyyyMMddHH";

    public static final String STANDARD_DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String PURE_STANDARD_DATE_FORMAT_STR = "yyyyMMdd";

    public static final String MIDDLE_DATE_FORMAT_STR = "yyyy-MM";
    public static final String PURE_MIDDLE_DATE_FORMAT_STR = "yyyyMM";

    public static final String YEAR_FORMAT_STR = "yyyy";
    
    public static final String SHORT_STANDARD_DATE_FORMAT_STR = "MM-dd";
    public static final String PURE_SHORT_STANDARD_DATE_FORMAT_STR = "MMdd";

    // add by xieyingbin for log record start
    public static final String EXCEL_DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    // add by xieyingbin for log record end

    /**
     * 通用日期模式
     */
    private static final String[] GENERIC_DATE_PATTERNS = {
            LONG_DATE_TIME_FORMAT_STR, STANDARD_DATE_TIME_FORMAT_STR,
            MIDDLE_DATE_TIME_FORMAT_STR, STANDARD_DATE_FORMAT_STR,STANDARD_DATE_TIME_FORMAT_STR_EXT,MIDDLE_DATE_FORMAT_STR,PURE_STANDARD_DATE_FORMAT_STR,
            STANDARD_DATE_TIME_ZONE_FORMAT_STR, EXCEL_DATE_TIME_FORMAT_STR};

    private DateUtil() {
    }

    /**
     * 日期字符串转化为日期
     * 
     * @param src
     *            日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String str) {
        try {
            return DateUtils.parseDate(str, GENERIC_DATE_PATTERNS);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期时间格式(yyyyMMdd)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatPureStandardDatetime(Date date) {
        return DateFormatUtils.format(date, PURE_STANDARD_DATE_FORMAT_STR);
    }

    /**
     * 日期时间格式(yyyyMMddHHmmss)
     * 
     * @param date
     * @return yyyyMMddHHmmss
     * @throws ParseException
     */
    public static String formatPureStandardDate(Date date) {
        return DateFormatUtils.format(date, PURE_STANDARD_DATE_TIME_FORMAT_STR);
    }

    /**
     * 日期时间格式(yyyy-MM-dd HH:mm:ss)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatStandardDatetime(Date date) {
        return DateFormatUtils.format(date, STANDARD_DATE_TIME_FORMAT_STR);
    }

    /**
     * 日期时间格式(yyyyMMddHHmmssSSS)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatPureLongDatetime(Date date) {
        return DateFormatUtils.format(date, PURE_LONG_DATE_TIME_FORMAT_STR);
    }
    /**
     * 日期时间格式(yyyy-MM-dd HH:mm:ss SSS)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatPureLongDatetime2(Date date) {
        return DateFormatUtils.format(date, LONG_DATE_TIME_FORMAT_STR);
    }
    /**
     * 日期时间格式(yyyy-MM-dd HH:mm)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatMiddleDatetime(Date date) {
    	return DateFormatUtils.format(date, MIDDLE_DATE_TIME_FORMAT_STR);
    }

    /**
     * 日期时间格式(yyyyMMddHHmm)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatPureMiddleDatetime(Date date) {
        return DateFormatUtils.format(date, PURE_MIDDLE_DATE_TIME_FORMAT_STR);
    }

    /**
     * 格式日期为系统的标准格式(yyyy-MM-dd)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatStandardDate(Date date) {
        return DateFormatUtils.format(date, STANDARD_DATE_FORMAT_STR);
    }
    
    /**
     * 格式日期为系统的标准格式(MM-dd)
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatShortStandardDate(Date date) {
        return DateFormatUtils.format(date, SHORT_STANDARD_DATE_FORMAT_STR);
    }

    /**
     * 根据出生日期计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(String birthday) {
    	if (Strings.isNullOrEmpty(birthday)) {
    		return 0;
    	}
    	
        return getAgeByBirthday(parseDate(birthday));
    }
    
    /**
     * 根据出生日期计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date brithday) {
    	if (brithday == null) {
    		return 0;
    	}
    	
        Calendar todayCalendar = Calendar.getInstance();
        Calendar brithdayCalendar = Calendar.getInstance();
        brithdayCalendar.setTime(brithday);
        
        return todayCalendar.get(Calendar.YEAR) - brithdayCalendar.get(Calendar.YEAR);
    }
  

    /**
     * 根据日期计算天数差
     * @param beforeDate 早日期
     * @param afterDate 晚日期
     * @return
     */
	public static int getDaysNum(Date beforeDate,
			Date afterDate) {
		Calendar calBefore = Calendar.getInstance();
		Calendar calAfter = Calendar.getInstance();
		calBefore.setTime(beforeDate);
		calAfter.setTime(afterDate);
		long day = ((calAfter.getTimeInMillis() - calBefore.getTimeInMillis()) / (60 * 60 * 24 * 1000));
		return (int) day;
	}
	
	 /**
     * 根据天数，计算日期
     * @param date 日期
     * @param dayNum 天数差
     * @return
     */
	public static Date getDateByDayNum(Date date,int dayNum){
		long oneDayMillis = (60 * 60 * 24 * 1000);
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return new Date(calDate.getTimeInMillis()+oneDayMillis*dayNum);
	}
	
	
	
	/**
     * 根据日期计算星期
     * @param date
     * @return
     */
	public static Integer getDayOfWeek(Date date){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
     * 根据日期计算月份
     * @param date
     * @return
     */
	public static Integer getMonth(Date date){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate.get(Calendar.MONTH);
	}
	
	/**
     * 根据日期计算天数
     * @param date
     * @return
     */
	public static Integer getDayOfMonth(Date date){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate.get(Calendar.DAY_OF_MONTH);
	}
	

	/**
     * 根据日期计算年
     * @param date
     * @return
     */
	public static Integer getYear(Date date){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate.get(Calendar.YEAR);
	}
	

	/**
     * 根据日期计算年
     * @param date
     * @return
     */
	public static Integer getHour(Date date){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		return calDate.get(Calendar.HOUR);
	}
	
	/**
     * 根据数字获取对应的星期数
     * 
     * @author 欧阳俊伟
     * @param dayOfWeek 数字
     * @param desc 描述（周、星期或者不填）
     * @param isEnglish 是否英文
     * @return String
     */
    public static String getDayOfWeekText(int dayOfWeek, String desc, boolean isEnglish) {
        String dayOfWeekText = null;
        switch (dayOfWeek){ 
            case 1 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "日";
            	} else {
            		dayOfWeekText = "Sun.";
            	}
                break; 
            case 2 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "一"; 
            	} else {
            		dayOfWeekText = "Mon.";
            	}
                break; 
            case 3 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "二"; 
		        } else {
		    		dayOfWeekText = "Tues.";
		    	}
                break; 
            case 4 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "三"; 
			    } else {
					dayOfWeekText = "Wed.";
				}
                break; 
            case 5 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "四"; 
				} else {
					dayOfWeekText = "Thur.";
				}
                break; 
            case 6 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "五"; 
	            } else {
	        		dayOfWeekText = "Fri.";
	        	}
                break;
            case 7 : 
            	if (!isEnglish) {
            		dayOfWeekText = desc + "六"; 
	            } else {
	        		dayOfWeekText = "Sat.";
	        	}
                break; 
        } 
        return dayOfWeekText;
    }
    
    /**
     * 日期时间格式(yyyy-MM-dd HH:mm:ss.SSS)
     * 
     * @param date
     * @return
     * @throws ParseException
     * @author xieyingbin add 2015/12/29
     */
    public static String formatExcelLongDatetime(Date date) {
        return DateFormatUtils.format(date, EXCEL_DATE_TIME_FORMAT_STR);
    }
    
    /**
    * 是否在指定日期区间，beginDay等于endDay时，返回是否是当天
    * @param date
    * @param beginDay
    * @param endDay
    * @return
    *  @author jiawei
    */
    public static boolean isTheDay(final Date date, final Date beginDay, final Date endDay) {
            return date.getTime() >= DateUtil.dayBegin(beginDay).getTime()
                            && date.getTime() <= DateUtil.dayEnd(endDay).getTime();
    }
    /**
    * 获取指定时间的那天 00:00:00.000 的时间
    * @param date
    * @return
    * @author jiawei
    */
    public static Date dayBegin(final Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
    }
    /**
    * 获取指定时间的那天 23:59:59.999 的时间
    *
    * @param date
    * @return
    * @author jiawei
    */
    public static Date dayEnd(final Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            return c.getTime();
    }
    /**
     * 获取昨天日期
     *
     * @param date
     * @return
     * @author ys
     */
    public static Date yesterday(final Date date) {
    	Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
//        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        return cal.getTime();
    }
    /**
     * 获取本周一和周末的日期
     *
     * @return
     * @author ys
     */
    public static List<Date> week() {
    	Calendar calendar = Calendar.getInstance();
        List<Date> week = new ArrayList<Date>();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        for (int i = 0; i < 7; i++) {
        	if (i == 0 || i == 6) {
        		week.add(calendar.getTime());
        	}
        	calendar.add(Calendar.DATE, 1);
        }
        return week;
    }
    

    // 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月  
    /**
     * 
     * @param month 月份
     * @param isQuarterStart 是否为季度首月
     * @return 月份
     * @createDate：2016年3月29日
     * @author：jiawei
     */
    public static int getQuarterInMonth(int month, boolean isQuarterStart) {  
        int months[] = { 1, 4, 7, 10 }; 
        if (!isQuarterStart) {  
            months = new int[] { 3, 6, 9, 12 };  
        }  
        if (month >= 1 && month <= 3)  
            return months[0];  
        else if (month >= 3 && month <= 5)  
            return months[1];  
        else if (month >= 6 && month <= 8)  
            return months[2];  
        else  
            return months[3];  
    }  
}
