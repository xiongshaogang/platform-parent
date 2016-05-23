package com.dvn.telemedicine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadPropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(ReadPropertiesUtil.class);
	/**
	 *读取资源文件
	 * @param fileName
	 * @return
	 */
	public static  String getProperties(String key,String fileName){
		Properties props = null;
		try {
			props = sysProperties.get(fileName);
			if(props == null){
				props = new Properties();
				ClassLoader cl = new ReadPropertiesUtil().getClass().getClassLoader();
				InputStream is = cl.getResourceAsStream(fileName);
				props.load(is);
				sysProperties.put(fileName, props);
			}
			return props.getProperty(key);
			
		} catch (IOException e) {
			logger.error("not properties file found @"+fileName);
		}
		return null;
	}
	
	public static String get(String key){
		return getProperties(key, "telemedicine.properties");
	}
	
	//国际化文件
	public static  String getPropertiesByLocale(String key,String locale){
		List<String> locales=new ArrayList<String>();
		locales.add("zh_CN");
		locales.add("zh_TW");
		locales.add("en_US");
		locales.add("ja_JP");
		
		if(languageProperties == null){
			languageProperties = new HashMap<String,Properties>();
			ClassLoader cl = new ReadPropertiesUtil().getClass().getClassLoader();
			Properties props = null;
			for(String l:locales){
				String fileName = "telemedicine_"+l+".properties";
				InputStream is = cl.getResourceAsStream(fileName);
				props = new Properties();
				try {
					props.load(is);
				} catch (IOException e) {
					logger.error("not properties file found @"+fileName);
				}
				languageProperties.put(l, props);
			}
			
		}
		
		
		
		if(locale!=null&&!"".equals(locale)){
			if(!locales.contains(locale)){
				locale = "zh_CN";
			}
		}else{
			locale = "zh_CN";
		}
		String value = languageProperties.get(locale).getProperty(key);
		return value != null ? value : key;
	}
	
	public static Map<String,Properties> languageProperties ;
	
	public static Map<String,Properties> sysProperties = new HashMap<String,Properties>();

	
}
