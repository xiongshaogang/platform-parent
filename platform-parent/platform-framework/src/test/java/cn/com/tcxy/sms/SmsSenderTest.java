package cn.com.tcxy.sms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.model.SmsVerificationLog;
import cn.com.tcxy.test.BaseSpringTest;

public class SmsSenderTest extends BaseSpringTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsSenderTest.class);

    @Autowired
    private SmsService smsSender;

    @Test
    public void sendVerificationCode() {
        String type = SmsVerificationLog.TYPE_FORGOT_PW;
        String mobile = "13916292663";
        smsSender.sendVerificationCode(mobile, type);
    }

    @Test
    public void verifySmsCode() {
        String type = SmsVerificationLog.TYPE_FORGOT_PW;
        String verifyCode = null;// 短信验证码
        String mobile = "13916292663";
        smsSender.verifySmsCode(mobile, verifyCode, type);
    }

    @Test
    public void verifyRepeatedSmsCode() {
        String type = SmsVerificationLog.TYPE_FORGOT_PW;
        String mobile = "13916292663";
        smsSender.isPermittedSendVerificationCode(mobile, type);

    }
    
    /**
     * 发送手机短信
     */
    @Test
    public void testSend(){   	
    	String phone ="13167119278";
    	String result = this.smsSender.sendSMS(phone, "小云健康");
    }

}
