package cn.com.tcxy.sms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.model.SmsVerificationLog;
import cn.com.tcxy.test.BaseSpringTest;

public class SmsServiceTest extends BaseSpringTest {
    private static Logger LOGGER = LoggerFactory.getLogger(SmsServiceTest.class);
    
    @Autowired
    private SmsService smsService;
    
    /**
     * 发送手机验证码
     */
    @Test
    public void testSendVerificationCode(){
    	//String method = SmsVerifyLog.METHOD_FORGOT_PW;//SmsVerifyLog.METHOD_REGISTER
    	String phone ="18221558590";
    	//this.smsService.sendVerificationCode(phone, SmsVerificationLog.TYPE_REGISTER);
    	//Boolean result = this.smsService.verifySmsCode(phone, "123123", SmsVerifyLog.TYPE_FORGOT_PW);
    	//Boolean result = this.smsService.isPermittedSendVerificationCode(phone, SmsVerificationLog.TYPE_REGISTER);
    	Boolean result = this.smsService.notifyNewOrder(phone, "111333");
    	//LOGGER.info("result:" + result);
    }
    
    

    
    

}
