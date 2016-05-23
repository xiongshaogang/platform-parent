package cn.com.tcxy.mail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.mail.MailService;
import cn.com.tcxy.model.MailVerificationLog;
import cn.com.tcxy.model.SmsVerificationLog;
import cn.com.tcxy.test.BaseSpringTest;

public class MailServiceTest extends BaseSpringTest {
    private static Logger LOGGER = LoggerFactory.getLogger(MailServiceTest.class);
    @Autowired
    private MailService mailService;
    
    
    @Test
    public void sendMail(){
    	
    	String email = "lijiepeng.tuobi@gmail.com";
    	
    	//this.mailService.sendForgotMail(email, "loginName", "www.baidu.com");
    	//this.mailService.sendNewOrderMail(email, "234");
    	this.mailService.sendVerificationCode(email, MailVerificationLog.METHOD_FORGOT_PW);
    	//Boolean result = this.mailService.sendWelcomeMail(email, "nickname");
    	//Boolean result = this.mailService.verifyEmailCode(email, "401716", MailVerifyLog.METHOD_FORGOT_PW);
    	//Boolean result = this.mailService.isPermittedSendVerificationCode(email, MailVerificationLog.METHOD_FORGOT_PW);
    	
    	//LOGGER.info("result:" + result);
    	
    }

}
