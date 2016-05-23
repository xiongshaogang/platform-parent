package cn.com.tcxy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

/**
 * 标注当前类是可以被mybatis-spring插件扫描器Mapper类
 * 
 * @author Johnny Wang
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Mapper {
}
