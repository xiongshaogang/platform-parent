
package cn.com.tcxy.cashout.util;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import cn.com.tcxy.cashout.AliPayCashoutOrderDetailVo;

/* *
 *类名：UtilDate
 *功能：自定义订单类
 *详细：工具类，可以用作获取系统日期、订单编号等
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class UtilDate {
	
    /** 年月日时分秒(无下划线) yyyyMMddHHmmss */
    public static final String dtLong                  = "yyyyMMddHHmmss";
    
    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String simple                  = "yyyy-MM-dd HH:mm:ss";
    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort                 = "yyyyMMdd";
	
    
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 产生随机的五位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(100000)+"";
	}
	
	/**
	 * 拼接支付宝提现详情
	 */
	public static String joinstring(List<String> list){
		 String inforString = list.get(0);
	        for (int i = 1, k = list.size(); i < k; i++) {
	           if (i % 5 == 0) {
	               inforString += "|";
	           } else {
	               inforString += "^";
	           }
	            inforString += list.get(i);
	       }
	        return inforString;
	}
	
	/**
     * 开始解析字符串
     */
	public static AliPayCashoutOrderDetailVo explainstring(String list){
        String[] usersInfor = list.split("\\|");
        System.out.println(usersInfor.length);
        AliPayCashoutOrderDetailVo alipay =new AliPayCashoutOrderDetailVo();;
        String[] userString;
        for (int i = 0, k = usersInfor.length; i < k; i++) {
            userString = usersInfor[i].split("\\^");
            alipay.setSerialNumber(userString[0]);
            alipay.setAccNo(userString[1]);
            alipay.setName(userString[2]);
            alipay.setSum(userString[3]);
            alipay.setStatus(userString[4]);
            alipay.setReason(userString[5]);
            alipay.setAlipaySerialNumber(userString[6]);
            alipay.setFinishDate(userString[7]);
            System.out.println("User" + i + "=" + alipay);
        }
        return alipay;
 
	}
	
}
