package cn.com.tcxy.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResourceBundle 工具类
 * 
 * @author Robin.jiao
 */
public class ResourceBundleUtil{

	private final static Logger	log	= LoggerFactory.getLogger(ResourceBundleUtil.class);
	
	public final static <T> T convertStringToT(String value,Class<?> class1){
		return (T) ObjectUtil.toT(value, class1);
	}
	/****************************************************************************************/
	/**
	 * 获取Properties配置文件键值 ,采用java.util.ResourceBundle类的getBundle()方法来读取
	 * 
	 * @param <T>
	 * @param baseName
	 *            配置文件的包+类全名(不要尾缀)
	 * @param key
	 *            Properties配置文件键名
	 * @param defaultValue
	 *            默认值,如果key 对应的value 不存在
	 * @return 该键的值,如果key 对应的value 不存在返回defaultValue
	 */
	@SuppressWarnings("cast")
	public static <T> T getValue(String baseName,String key,T defaultValue){
		ResourceBundle resourceBundle = getResourceBundle(baseName);
		return (T) getValue(resourceBundle, key, defaultValue);
	}

	/**
	 * 获取Properties配置文件键值 ,采用java.util.ResourceBundle类的getBundle()方法来读取
	 * 
	 * @param <T>
	 * @param baseName
	 *            配置文件的包+类全名(不要尾缀)
	 * @param key
	 *            Properties配置文件键名
	 * @param defaultValue
	 *            默认值,如果key 对应的value 不存在
	 * @return 该键的值,如果key 对应的value 不存在返回defaultValue
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(ResourceBundle resourceBundle,String key,T defaultValue){
		String propertyValue = getValue(resourceBundle, key);
		if (Utils.isNull(propertyValue)){
			log.debug("propertyValue is null,return the defaultValue");
			return defaultValue;
		}
		return (T) convertStringToT(propertyValue, defaultValue.getClass());
	}

	/**
	 * 获取Properties配置文件键值,按照typeClass 返回对应的类型
	 * 
	 * @param <T>
	 * @param baseName
	 * @param key
	 * @param typeClass
	 *            指明返回类型,<br>
	 *            如果是String.class,则返回的是String <br>
	 *            如果是Integer.class,则返回的是Integer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String baseName,String key,Class<?> typeClass){
		String value = getValue(baseName, key);
		return (T) convertStringToT(value, typeClass);
	}

	/**
	 * 获取Properties配置文件键值,按照typeClass 返回对应的类型
	 * 
	 * @param <T>
	 * @param resourceBundle
	 * @param key
	 * @param typeClass
	 *            指明返回类型,<br>
	 *            如果是String.class,则返回的是String <br>
	 *            如果是Integer.class,则返回的是Integer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(ResourceBundle resourceBundle,String key,Class<?> typeClass){
		String value = getValue(resourceBundle, key);
		return (T) convertStringToT(value, typeClass);
	}

	/**
	 * 获取Properties配置文件键值 ,采用java.util.ResourceBundle类的getBundle()方法来读取
	 * 
	 * @param baseName
	 *            配置文件的包+类全名(不要尾缀)
	 * @param key
	 *            Properties配置文件键名
	 * @return 该键的值
	 * @since 1.0
	 */
	public static String getValue(String baseName,String key){
		ResourceBundle resourceBundle = getResourceBundle(baseName);
		return getValue(resourceBundle, key);
	}


	/**
	 * 获取Properties配置文件键值 ,采用java.util.ResourceBundle类的getBundle()方法来读取
	 * 
	 * @param resourceBundle
	 *            配置文件的包+类全名(不要尾缀)
	 * @param key
	 *            Properties配置文件键名
	 * @return 该键的值,不存在key,返回null
	 */
	public static String getValue(ResourceBundle resourceBundle,String key){
		if (!resourceBundle.containsKey(key)){
			log.warn("resourceBundle don't containsKey:{}", key);
		}else{
			try{
				String value = resourceBundle.getString(key);
				if (Utils.isNull(value)){
					log.warn("resourceBundle has the key:{},but value is null/empty", key);
				}
				return value;
			}catch (Exception e){
				log.error(e.getMessage());
			}
		}
		return null;
	}


	/*************************************************************************************/
	/**
	 * 读取配置文件,将k/v 统统转成map
	 * 
	 * @param baseName
	 *            配置文件
	 * @return Map<String, String>,如果
	 */
	public static Map<String, String> readAllPropertiesToMap(String baseName){
		ResourceBundle resourceBundle = getResourceBundle(baseName);
		Enumeration<String> enumeration = resourceBundle.getKeys();
		if (Utils.isNotNull(enumeration)){
			Map<String, String> propertyMap = new HashMap<String, String>();
			while (enumeration.hasMoreElements()){
				String key = enumeration.nextElement();
				String value = resourceBundle.getString(key);
				propertyMap.put(key, value);
			}
			return propertyMap;
		}
		return null;
	}





	/*********************************************************************************/
	/**
	 * @param baseName
	 * @param prefix
	 *            前缀
	 * @param spliter
	 * @return
	 */
	public static Map<String, String> readPrefixAsMap(String baseName,String prefix,String spliter){
		Map<String, String> propertyMap = readAllPropertiesToMap(baseName);
		if (Utils.isNotNull(propertyMap)){
			Map<String, String> result = new HashMap<String, String>();
			for (Map.Entry<String, String> entry : propertyMap.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				// 以 prefix 开头
				if (key.startsWith(prefix)){
					// 分隔
					String[] values = key.split(spliter);
					if (values.length >= 2){
						result.put(values[1], value);
					}
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 获得ResourceBundle
	 * 
	 * @param baseName
	 * @return
	 */
	public static ResourceBundle getResourceBundle(String baseName){
		// Locale enLoc = new Locale("en", "US"); // 表示美国地区
		ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		return resourceBundle;
	}
}
