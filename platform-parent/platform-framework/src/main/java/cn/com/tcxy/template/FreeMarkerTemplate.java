package cn.com.tcxy.template;

import java.util.Map;

/**
 * 系统模板接口
 * 
 * @author johnny wang
 * 
 */
public interface FreeMarkerTemplate {

    String loadTemplate(String templateName);

    String loadTemplate(String templateName, Map<String, Object> model);

	String parseStringTemplate(String stringTemplate, Map<String, Object> model);

}
