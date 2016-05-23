package cn.com.tcxy.model;

import cn.com.tcxy.model.base.BaseSmsVerificationLog;

 

public class SmsVerificationLog extends BaseSmsVerificationLog{
	
	/**
	 * 注册,
	 */
	public static final String 				TYPE_REGISTER="REGISTER";
	/**
	 * 忘记密码,
	 */
	public static final String 				TYPE_FORGOT_PW="FORGOTPW";
}
