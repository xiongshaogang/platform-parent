package cn.com.tcxy.cashout.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 *处理支付http请求和返回类
 * @author llr
 *
 */
public class HttpReqRespUtil {
    private static Logger loger =Logger.getLogger(HttpReqRespUtil.class);
   /**
    * 要求bean的属性必须与参数名一致
    * 封装response成bean
    * @param clazz
    * @param str  需要分割的字符串 responseCode=0000&merId=606060103099001
    * @param parmSeparator 参数间的分隔符 &
    * @param kvSeparator   参数名与参数值的分割 =
    * @return
    */
    public static <T> T KeyValue2Bean(Class<T> clazz,String str,String parmSeparator,String kvSeparator){
       T t=null;
       if(StringUtils.isNotBlank(str)){
           Map<String, String> map = SplitsKeyValue(str, parmSeparator, kvSeparator);
            try {
                Field[] fields=clazz.getDeclaredFields();
                t = clazz.newInstance();
                String fieldName=null;
                String fieldNameUpper=null;
                for(Field field:fields){
                    fieldName=field.getName();
                    fieldNameUpper = fieldName.replaceFirst(fieldName.substring(0, 1),
                                                      fieldName.substring(0, 1).toUpperCase());
                    
                    Method m = clazz.getMethod("set" + fieldNameUpper,field.getType());
                    m.invoke(t,map.get(fieldName));
                }
            } catch (Exception e) {
                loger.error("转换异常", e);
              }
       }else{
           loger.error("input param str is null:"+str);
       }
       return t;
   }
   /**
    * 根据分割符拆分成key-value的map
    * e.g:responseCode=0000&merId=606060103099001&merDate=20120530
    * @param str
    * @param parmSeparator
    * @param kvSeparator
    * @return
    */
private static Map<String, String> SplitsKeyValue(String str, String parmSeparator, String kvSeparator) {
    String[]res=str.split(parmSeparator);
       Map <String,String>map=new HashMap<String,String>();
       for(String s:res){
           String []kv=s.split(kvSeparator);
           map.put(kv[0], kv[1]);
       }
    return map;
}
/**
 * 要求bean的属性必须与参数名一致
 * 根据bean封装http请求参数
 * @param t  bean的实例
 * @return  NameValuePair[] request 参数列表
 * @throws Exception
 */
public static <T>NameValuePair[] buildRequestParams(T t) throws Exception {
    Class cazz=t.getClass();
    Field[] fields=cazz.getDeclaredFields();
    int length=fields.length;
    NameValuePair[] formparams = new NameValuePair[length];
    String fieldName=null;
    String fieldNameUpper=null;
    String fieldValue=null;
    for(int i=0;i<length;i++){
        fieldName=fields[i].getName();
        fieldNameUpper = fieldName.replaceFirst(fieldName.substring(0, 1),
                                          fieldName.substring(0, 1).toUpperCase());
        Method m = cazz.getMethod("get" + fieldNameUpper);
        fieldValue=(String) m.invoke(t);
        formparams[i]=new NameValuePair(fieldName,fieldValue);
        fieldName=null;
        fieldValue=null;
    }
 return formparams;
}
}
