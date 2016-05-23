package cn.com.tcxy.template;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.test.BaseSpringTest;

public class FreeMarkerTemplateTest extends BaseSpringTest {

    @Autowired
    private FreeMarkerTemplate freeMarkerTemplate;

    @Test
    public void testLoadTemplateForWelcomeMail() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nickname", "张三");
        model.put("currentDate", "2014-07-01");
        String str = this.freeMarkerTemplate.loadTemplate(
                "mail/welcome.ftl", model);
        System.out.println("str:" + str);
    }

    @Test
    public void testLoadTemplateForVerificationCode() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("VerificationCode", "123");
        String str = this.freeMarkerTemplate.loadTemplate(
                "sms/verification-code.ftl", model);
        System.out.println("str:" + str);
    }
}
