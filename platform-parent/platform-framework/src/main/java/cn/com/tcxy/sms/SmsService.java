package cn.com.tcxy.sms;

import java.util.Map;

import cn.com.tcxy.mail.NotifyService;

/**
 * 手机短信服务类.
 *
 */
public interface SmsService extends NotifyService{
	
	/**
	 * 生成并发送手机验证码.
	 * <br/><li>在数据库中生成验证码日志记录,并通过第三方接口将验证码发送到指定手机.</li>
	 * <br/>注意:调用此方法前总是应该先调用isPermittedSendVerificationCode()用来检查是否应该生成并发送一个验证码.
	 * @param mobile
	 * @param method 操作类型,取值为SmsVerifyLog.TYPE_REGISTER,SmsVerifyLog.TYPE_FORGOT_PW其中之一.
	 * @return 获取正常生成的验证码,
	 */
    String sendVerificationCode(String mobile, String method);

    /**
     * 检查验证码是否在有效期内,默认有效期为一天.
     * @param mobile 待验证的手机号.
     * @param verificationCode 验证码.
     * @param method 操作类型,取值为SmsVerifyLog.TYPE_REGISTER,SmsVerifyLog.TYPE_FORGOT_PW其中之一.
     * @return 有效,返回true,无效返回false.
     */
    Boolean verifySmsCode(String mobile, String verificationCode,
            String method);
    
    /**
     * 检查手机号是否在一分钟内重复生成验证码.
     * <br/>注意:在调用sendVerificationCode()方法之前总是应该先调用此方法,来检查是否应该生成并发送一个验证码.
     * @param mobile 待检查的手机号.
     * @param method 操作类型,取值为SmsVerifyLog.TYPE_REGISTER,SmsVerifyLog.TYPE_FORGOT_PW其中之一.
     * @return 如果待检查的手机号生成的验证码时间距上次生成的验证码时间超过一分钟,允许生成新的验证码,返回true.
     * <br/>不足一分钟则不允许生成新的验证码，返回false.
     */
    Boolean isPermittedSendVerificationCode(String mobile,
            String method);

    /**
     * 新订单生成时发送的短信信息.
     * @param mobile 手机号.
     * @param orderCode 订单号.
     * @return 发送成功返回true,发送失败返回false.
     */
    Boolean notifyNewOrder(String mobile, String orderCode);
    
    /**
     * 最新发送短信方法
     * @author 朱毅
     * @param mobile 电话号码，多个号码用逗号隔开（20个以内）
     * @param msg 短息发送内容
     * @return
     */
    public String sendSMS(String mobile, String msg);

    /**
     * 执行短信发送.
     * @param mobile 手机号. 
     * @param template 模板名称,模板默认放在sms/xxx.ftl下,此名称为"xxx"部分.
     * @param params 模板中需要替换的参数键值对.
     */
    public void executeSmsTask(final String mobile, final String template,
            final Map<String, Object> params) ;

    /**发送短信
     * @author zhu yi
     * @param cellphone 电话号码
     * @param content 短信内容
     * @return
     */
    Boolean personalSendSMS(String cellphone, String content);    
}
