package cn.com.tcxy.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cn.com.tcxy.sms.SmsServiceImpl;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * 
 * @author johnny wang
 * 
 */
@Component
public class FreeMarkerTemplateImpl implements FreeMarkerTemplate {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    
    @Autowired
    private Configuration freeMarkerConfiguration;

    @Override
    public String loadTemplate(String templateName) {
        return this.loadTemplate(templateName, new HashMap<String, Object>());
    }

    @Override
    public String loadTemplate(String templateName, Map<String, Object> model) {
    	//templateName = "/home/webroot/verification-code.ftl";
        try {
        	Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            //Template t = freeMarkerConfiguration.getTemplate(templateName);
            if (model == null) {
                model = new HashMap<String, Object>();
            }
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (IOException e) {
            LOGGER.error("模板文件不存在：" + e.getMessage());
        } catch (TemplateException e) {
            LOGGER.error("模板存在错误：" + e.getMessage());
        }

        return null;
    }
    
    @Override
    public String parseStringTemplate(String stringTemplate,Map<String,Object> model){
	        StringTemplateLoader stringLoader = new StringTemplateLoader();
	        stringLoader.putTemplate("myTemplate", stringTemplate);
	        freeMarkerConfiguration.setTemplateLoader(stringLoader);
	        Template temp;
			try {
				temp = freeMarkerConfiguration.getTemplate("myTemplate","utf-8");
				return	FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
			} catch (IOException e) {
				 LOGGER.error("模板存在错误：" + e.getMessage());
		    } catch (TemplateException e) {
		    	LOGGER.error("模板存在错误：" + e.getMessage());
		    }
			return null;
    }
    
}