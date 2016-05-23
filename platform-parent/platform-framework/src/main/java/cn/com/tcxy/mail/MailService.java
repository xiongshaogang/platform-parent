package cn.com.tcxy.mail;

/**
 * 邮件发送服务类.
 *
 */
public interface MailService extends NotifyService{

	/**
	 * 生成并发送邮件验证码.
	 * @param email 邮件地址.
	 * @param method 操作类型,取值为MailVerifyLog.METHOD_REFISTER,MailVerifyLog.METHOD_FORGOT_PW其中之一.
	 * @return 
	 */
	Boolean sendVerificationCode(String email, String method);
	 /**
     * 检查验证码是否在有效期内,默认有效期为一天.
     * @param email 待验证的邮箱号.
     * @param verifyCode 验证码.
     * @param method 操作类型,取值为MailVerifyLog.METHOD_REFISTER,MailVerifyLog.METHOD_FORGOT_PW其中之一.
     * @return 有效,返回true,无效返回false.
     */
    Boolean verifyEmailCode(String email, String verifyCode, String method);
    /**
     * 检查邮箱是否在一分钟内重复生成验证码.
     * <br/>注意:在调用sendVerificationCode()方法之前总是应该先调用此方法,来检查是否应该生成并发送一个验证码.
     * @param mobile 待检查的邮箱.
     * @param method 操作类型,取值为MailVerifyLog.METHOD_REFISTER,MailVerifyLog.METHOD_FORGOT_PW其中之一.
     * @return 如果待检查的手机号生成的验证码时间距上次生成的验证码时间超过一分钟,允许生成新的验证码,返回true.
     * <br/>不足一分钟则不允许生成新的验证码，返回false.
     */
    Boolean isPermittedSendVerificationCode(String email, String method);
    
	/**
	 * 发送欢迎邮件.
	 * @param to 目标邮箱.
	 * @param nickname 收件人昵称.
	 * @return 发送成功返回true,否则返回false.
	 */
    Boolean sendWelcomeMail(String to, String nickname);

    /**
     * 新订单发送邮件通知.
     * @param email 目标邮箱.
     * @param orderCode 订单号.
     */
    public void sendNewOrderMail(String email, String orderCode);

    /**
     * 
     * @param mailTo
     * @param loginName
     * @param validationUrl
     */
    void sendForgotMail(String mailTo, String loginName, String validationUrl);
    
    /**
     * Api发送简单验证码
     * @param email 目标邮箱.
     * @param title 标题
     * @param content 内容.
     */
    Boolean sendApiVerificationEmail(String email,String title,String content);

}
