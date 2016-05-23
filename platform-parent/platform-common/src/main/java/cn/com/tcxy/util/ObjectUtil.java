package cn.com.tcxy.util;

import java.math.BigDecimal;

/**
 * object工具类
 * 
 */
public final class ObjectUtil{

	/** Don't let anyone instantiate this class. */
	private ObjectUtil(){}

	/**
	 * 非空判断两个值是否相等 <br>
	 * 当两个值都不为空,且object.equals(object2)才返回true
	 * 
	 * @param object
	 *            object
	 * @param object2
	 *            object2
	 * @return 当两个值都不为空,且object.equals(object2)才返回true
	 */
	public static Boolean equalsNotNull(Object object,Object object2){
		return (Utils.isNotNull(object) && Utils.isNotNull(object2) && object.equals(object2));
	}

	/**
	 * 判断两个值是否相等,允许两个值都为null
	 * 
	 * @param object
	 *            object
	 * @param object2
	 *            object2
	 * @param flag_nullType
	 *            标识null和""相比的情况,默认值为false 标识不相等
	 * @return 判断两个值是否相等
	 */
	public static Boolean equals(Object object,Object object2,boolean flag_nullType){
		if (object == object2){
			return true;
		}
		// object 是空
		if (null == object){
			// 标识null和""相比的情况
			if (flag_nullType){
				if ("".equals(object2.toString().trim())){
					return true;
				}
			}
		}else{
			// 标识null和""相比的情况
			if ("".equals(object.toString().trim())){
				if (null == object2){
					if (flag_nullType){
						return true;
					}
				}else{
					if ("".equals(object2.toString().trim())){
						return true;
					}
				}
			}else{
				return object.equals(object2);
			}
		}
		return false;
	}

	/**
	 * 判断两个值是否相等,允许两个值都为null
	 * 
	 * @param object
	 *            object
	 * @param object2
	 *            object2
	 * @return 判断两个值是否相等 标识null和""相比的情况,默认值为false 标识不相等
	 */
	public static Boolean equals(Object object,Object object2){
		return equals(object, object2, false);
	}

	/**
	 * 判断对象是不是boolean类型数据
	 * 
	 * @param object
	 *            对象
	 * @return 是返回true
	 */
	public static Boolean isBoolean(Object object){
		return object instanceof Boolean;
	}

	/**
	 * 判断对象是不是Integer类型
	 * 
	 * @param object
	 *            对象
	 * @return 是返回true
	 */
	public static Boolean isInteger(Object object){
		return object instanceof Integer;
	}

	/**
	 * object 类型转换成boolean类型
	 * 
	 * @param object
	 *            object
	 * @return boolean
	 */
	public static Boolean toBoolean(Object object){
		if (null == object){
			throw new IllegalArgumentException("object can't be null/empty!");
		}
		return Boolean.parseBoolean(object.toString());
	}

	/**
	 * object转成integer类型
	 * 
	 * @param value
	 *            值
	 * @return Integer
	 */
	public static Integer toInteger(Object value){
		Integer returnValue = null;
		if (Utils.isNotNull(value)){
			// Integer
			if (value instanceof Integer){
				returnValue = (Integer) value;
			}else{
				try{
					returnValue = new Integer(value.toString().trim());
				}catch (Exception e){
					throw new IllegalArgumentException("Input param:\"" + value + "\", convert to integer exception");
				}
			}
		}
		return returnValue;
	}

	/**
	 * object类型转换成BigDecimal
	 * 
	 * @param value
	 *            值
	 * @return BigDecimal
	 */
	public static BigDecimal toBigDecimal(Object value){
		BigDecimal returnValue = null;
		if (Utils.isNotNull(value)){
			if (value instanceof BigDecimal){
				returnValue = (BigDecimal) value;
			}else{
				returnValue = new BigDecimal(value.toString().trim());
			}
		}
		return returnValue;
	}

	/**
	 * 把对象转换为long类型
	 * 
	 * @param value
	 *            包含数字的对象.
	 * @return long 转换后的数值,对不能转换的对象返回null。
	 */
	public static Long toLong(Object value){
		if (Utils.isNotNull(value)){
			if (value instanceof Long){
				return (Long) value;
			}
			return Long.parseLong(value.toString());
		}
		return null;
	}

	/**
	 * Object to double
	 * 
	 * @param value
	 * @return
	 */
	public static Double toDouble(Object value){
		if (Utils.isNotNull(value)){
			if (value instanceof Double){
				return (Double) value;
			}
			return new Double(value.toString());
		}
		return null;
	}

	/**
	 * object to float
	 * 
	 * @param value
	 * @return
	 */
	public static Float toFloat(Object value){
		if (Utils.isNotNull(value)){
			if (value instanceof Float){
				return (Float) value;
			}
			return new Float(value.toString());
		}
		return null;
	}

	/**
	 * object to short
	 * 
	 * @param value
	 * @return
	 */
	public static Short toShort(Object value){
		if (Utils.isNotNull(value)){
			if (value instanceof Short){
				return (Short) value;
			}
			return new Short(value.toString());
		}
		return null;
	}

	/**
	 * 把对象转换成字符串 create by 徐新望
	 * 
	 * @param value
	 *            参数值
	 * @return String 转换成字符串,若对象为null,则返回"".
	 * @since 1.0
	 */
	public static String toString(Object value){
		if (Utils.isNotNull(value)){
			if (value instanceof String){
				return (String) value;
			}
			return value.toString();
		}
		return null;
	}

	/**
	 * object to T
	 * 
	 * @param <T>
	 * @param value
	 * @param class1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toT(Object value,Class<?> class1){
		if (null == value){
			return null;
		}
		if (class1 == String.class){
			return (T) toString(value);
		}else if (class1 == Boolean.class){
			return (T) toBoolean(value);
		}else if (class1 == Integer.class){
			return (T) toInteger(value);
		}else if (class1 == BigDecimal.class){
			return (T) toBigDecimal(value);
		}else if (class1 == Long.class){
			return (T) toLong(value);
		}else if (class1 == Double.class){
			return (T) toDouble(value);
		}else if (class1 == Float.class){
			return (T) toFloat(value);
		}else if (class1 == Short.class){
			return (T) toShort(value);
		}
		return null;
	}

	/**
	 * 去除空格
	 * 
	 * <pre>
	 * trim(null) --------&gt; &quot;&quot;
	 * trim(&quot;null&quot;) --------&gt; &quot;null&quot;
	 * 
	 * </pre>
	 * 
	 * @param obj
	 *            obj
	 * @return 去除空格
	 */
	public static String trim(Object obj){
		return obj == null ? "" : obj.toString().trim();
	}
}
