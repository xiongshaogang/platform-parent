package cn.com.tcxy.util;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils{

	private final static Logger	log	= LoggerFactory.getLogger(Utils.class);

	/** Don't let anyone instantiate this class. */
	private Utils(){}

	/*************************************************************************************************************/
	/**
	 * 判断元素是否为空
	 * <p>
	 * 目前可以判断一下元素
	 * <ul>
	 * <li>Collection,使用其isEmpty()</li>
	 * <li>Map,使用其isEmpty()</li>
	 * <li>Object[],判断length==0</li>
	 * <li>String,使用.trim().length()效率高</li>
	 * <li>Enumeration,使用hasMoreElements()</li>
	 * <li>Iterator,使用hasNext()</li>
	 * </ul>
	 * 
	 * @param value
	 *            可以是Collection,Map,Object[],String,Enumeration,Iterator类型
	 * @return 空返回true
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNull(Object value){
		if (null == value){
			return true;
		}
		/*****************************************************************************/
		boolean flag = false;
		// 集合
		if (value instanceof Collection){
			flag = ((Collection) value).isEmpty();
		}
		// map
		else if (value instanceof Map){
			flag = ((Map) value).isEmpty();
		}
		// Object[]object数组
		else if (value instanceof Object[]){
			flag = ((Object[]) value).length == 0;
		}
		// 字符串
		else if (value instanceof String){
			// 比较字符串长度, 效率高
			flag = value.toString().trim().length() <= 0;
		}
		// 枚举
		else if (value instanceof Enumeration){
			flag = !((Enumeration) value).hasMoreElements();
		}
		// Iterator迭代器
		else if (value instanceof Iterator){
			flag = !((Iterator) value).hasNext();
		}
		return flag;
	}

	/**
	 * 如果value 为FeiLongValidator.isNull,则返回nullValue<br>
	 * 如果value 为FeiLongValidator.isNotNull,则返回notNullValue<br>
	 * 
	 * @param <T>
	 * @param value
	 *            判断值
	 * @param nullValue
	 *            如果value 为FeiLongValidator.isNull,则返回nullValue
	 * @param notNullValue
	 *            如果value 为FeiLongValidator.isNotNull,则返回notNullValue
	 * @return 如果value 为FeiLongValidator.isNull,则返回nullValue<br>
	 *         如果value 为FeiLongValidator.isNotNull,则返回notNullValue<br>
	 * @since 1.0
	 */
	public static <T> Object isNull(Object value,T nullValue,T notNullValue){
		if (Utils.isNull(value)){
			return nullValue;
		}
		return notNullValue;
	}

	/**
	 * 判断元素是否不为空,调用<code>isNull</code>方法
	 * <p>
	 * 目前可以判断一下元素
	 * <ul>
	 * <li>Collection,使用其isEmpty()</li>
	 * <li>Map,使用其isEmpty()</li>
	 * <li>Object[],判断length==0</li>
	 * <li>String,使用.trim().length()效率高</li>
	 * <li>Enumeration,使用hasMoreElements()</li>
	 * <li>Iterator,使用hasNext()</li>
	 * </ul>
	 * 
	 * @param value
	 *            可以是Collection,Map,Object[],String,Enumeration,Iterator类型
	 * @return 不为空返回true
	 * @since 1.0
	 */
	public static boolean isNotNull(Object value){
		return !isNull(value);
	}
	
	/**
	 * 打印request参数键值对
	 */
	public static void printRequestKeyAndValue(HttpServletRequest request) {
		Enumeration pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			String name = (String) pNames.nextElement();
			String value = request.getParameter(name);
			System.out.print(name);
			System.out.print(":");
			System.out.print(value);
			System.out.println();
		}
	}
}