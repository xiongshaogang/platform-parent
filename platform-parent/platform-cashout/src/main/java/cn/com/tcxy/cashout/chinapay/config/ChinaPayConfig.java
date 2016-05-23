package cn.com.tcxy.cashout.chinapay.config;

import java.util.Properties;

import cn.com.tcxy.cashout.prop.CashoutProperties;

public class ChinaPayConfig {
    
    public static  String CHINAPAY_REQUEST_URL;
    public static  String CHINAPAY_MERID;
    public static  String CHINAPAY_INPUT_CHARSET;
    public static  String CHINAPAY_PRIVATE_KEY_FILE_NAME;
    public static  String CHINAPAY_PUBLIC_KEY_FILE_NAME;
    public static  String CHINAPAY_RESPONSE_SEPARATOR;
    public static  String CHINAPAY_RESPONSE_KV_SEPARATOR;
    public static  String CHINAPAY_REQUEST_QUERY_URL;
    public static  String CHINAPAY_VERIFY_MERID;
    
    private final static Properties chinapayCashoutProperties=CashoutProperties.getChinapayCashoutProperties();
    private ChinaPayConfig(){}
    public static String getValue(String key){
        return chinapayCashoutProperties.getProperty(key);
    }
    static{
        CHINAPAY_REQUEST_URL=getValue("chinapay.request.url");
        CHINAPAY_MERID=getValue("chinapay.merid");
        CHINAPAY_INPUT_CHARSET=getValue("chinapay.input.charset");
        CHINAPAY_PRIVATE_KEY_FILE_NAME=getValue("chinapay.private.key.file.name");
        CHINAPAY_PUBLIC_KEY_FILE_NAME=getValue("chinapay.public.key.file.name");
        CHINAPAY_RESPONSE_SEPARATOR=getValue("chinapay.response.separator");
        CHINAPAY_RESPONSE_KV_SEPARATOR=getValue("chinapay.response.kv.separator");
        CHINAPAY_REQUEST_QUERY_URL=getValue("chinapay.request.query.url");
        CHINAPAY_VERIFY_MERID=getValue("chinapay.verify.merid");
    }
}
