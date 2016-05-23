package cn.com.tcxy.cashout.chinapay;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.log4j.Logger;

import cn.com.tcxy.cashout.chinapay.config.ChinaPayConfig;
import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
/**
 * China Pay 工具类
 * @author fds
 *
 */
public class ChinaPaySign {
   private static Logger loger =Logger.getLogger(ChinaPaySign.class);
   /**
    * 对发送的请求参数进行签名
    * @param message
    * @param merId
    * @return
    * @throws Exception
    */
   public static String sign(String message,String merId) throws Exception{
       String messageBase64 = encode(message);
       String chkValue="";
       loger.info("ChinaPayConfig key path:"+ChinaPayConfig.CHINAPAY_PRIVATE_KEY_FILE_NAME);
       //URL url=ChinaPaySign.class.getResource(ChinaPayConfig.CHINAPAY_PRIVATE_KEY_FILE_NAME);
       //String priKeyPath=url.getPath();
       String priKeyPath=ChinaPayConfig.CHINAPAY_PRIVATE_KEY_FILE_NAME;
       chinapay.PrivateKey key = buildSignKey(merId,priKeyPath);
       //签名
       SecureLink s = new SecureLink(key);
       chkValue = s.Sign(messageBase64);
       loger.info("signFlag=1 时签名内容:"+ chkValue);
       return chkValue;
   }
/**
 * 构建签名的key
 * @param merId
 * @return
 * @throws Exception
 */
private static PrivateKey buildSignKey(String merId,String keyPath) throws Exception {
    boolean flag=false; 
       loger.info("keyPath:"+keyPath);
       PrivateKey key=new chinapay.PrivateKey();
       flag=key.buildKey(merId,0,keyPath); 
       if (flag==false) 
          {   
             loger.error("build key error!");
             throw new Exception("build key error.");
           }
    return key;
}
 /**  
  * 验证签名
  * @param plainData 用于数字签名的字符串
  * @param checkValue 校验值，要验证的字符串的数字签名，长度为256字节的字符串
  * @param merId
  * @return
  * @throws Exception
  */
   public static Boolean verifySign(String plainData,String checkValue,String merId) throws Exception{
       boolean verifyFlag=false;
       String messageBase64 = encode(plainData);
       /*key path 指定在服务器目录
        * URL url=ChinaPaySign.class.getResource(ChinaPayConfig.CHINAPAY_PUBLIC_KEY_FILE_NAME);
       String priKeyPath=url.getPath();*/
       String priKeyPath=ChinaPayConfig.CHINAPAY_PUBLIC_KEY_FILE_NAME;
       chinapay.PrivateKey key = buildSignKey(merId,priKeyPath);
       //验签
       SecureLink s = new SecureLink(key);
       verifyFlag=s.verifyAuthToken(messageBase64, checkValue);
       return verifyFlag;
   }
   /**
    * 对签名的数据进行Base64编码
    * @param message
    * @return
 * @throws Exception 
    */
   public static String encode(String message) throws Exception{
       String result=null;
       loger.info("要签名的数据1:"+ message);
       //对签名的数据进行Base64编码
       result = new String(Base64.encode(message.getBytes("GBK")));
       //result = new String(Base64.encode(message.getBytes()));
       loger.info("要签名的数据进行Base64编码后为:"+ result);
       return result;
   }
   
   public static void  main(String[] agrs) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
     // System.out.println(ChinaPaySign.class.getResource("/key/MerPrK_808080211302039_20150828163742.key"));
       /*
       ChinaPayRequestVo chinaPayRequestVo=new ChinaPayRequestVo();
       Field[] fields=chinaPayRequestVo.getClass().getDeclaredFields();
       for(Field field:fields){
         System.out.println(field.getName());
         String name=field.getName();
         name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                 .toUpperCase());
         Method m = chinaPayRequestVo.getClass().getMethod("get" + name);
     
         System.out.println(m.invoke(chinaPayRequestVo));
       }
       */

   }
}
