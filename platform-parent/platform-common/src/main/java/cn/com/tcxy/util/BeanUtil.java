package cn.com.tcxy.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装了org.apache.commons.beanutils包下面的类
 * 
 */
public final class BeanUtil{

	private final static Logger	log	= LoggerFactory.getLogger(BeanUtil.class);

	private BeanUtil(){}

    /**
     * 使用BeanUtils类从对象中取得属性值.
     * 
     * @param bean
     *            bean
     * @param name
     *            属性名称
     * @return 使用BeanUtils类从对象中取得属性值
     */
    public static String getProperty(Object bean,String name){
        try{
            String propertyValue = BeanUtils.getProperty(bean, name);
            return propertyValue;
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        return null;
    }

}
