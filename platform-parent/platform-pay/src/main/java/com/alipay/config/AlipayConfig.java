package com.alipay.config;

import java.util.ResourceBundle;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

    public static final ResourceBundle alipay_config = ResourceBundle.getBundle("config/alipay_config");
	
	/**
	 * 交易状态：交易成功且结束，不可再做任何操作。
	 */
	public static final String TRADE_STATUS_TRADE_FINISHED = "TRADE_FINISHED";
	
	/**
	 * 交易状态：交易成功，且可对该交易做操作，如：多级分润、退款等。
	 */
	public static final String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
	
	/**
	 * 服务器异步通知类型：批量退款通知。
	 */
	public static final String NOTIFY_TYPE_BATCH_REFUND_NOTIFY = "batch_refund_notify";
	
	/**
	 * 服务器异步通知类型：交易状态通知。
	 */
	public static final String NOTIFY_TYPE_TRADE_STATUS_SYNC = "trade_status_sync";
	
    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public static final String partner = String.valueOf(alipay_config.getString("partner"));
    public static final String key = String.valueOf(alipay_config.getString("key"));
    public static final String seller_id = String.valueOf(alipay_config.getString("seller_id"));
    public static final String notify_url =String.valueOf(alipay_config.getString("notify_url")); 
    public static final String return_url =String.valueOf(alipay_config.getString("return_url")); 
    public static final String private_key = String.valueOf(alipay_config.getString("private_key"));
    public static final String ali_public_key = String.valueOf(alipay_config.getString("ali_public_key"));

    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    // 调试用，创建TXT日志文件夹路径
    public static final String log_path = "D:\\zfb";
    // 字符编码格式 目前支持 gbk 或 utf-8
    public static final String input_charset =String.valueOf(alipay_config.getString("input_charset"));
    // 签名方式 不需修改
    public static final String sign_type =String.valueOf(alipay_config.getString("sign_type"));


//	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
//	public static String partner = "";
//	// 商户的私钥
//	public static String key = "";
//
//	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
//	
//
//	// 调试用，创建TXT日志文件夹路径
//	public static String log_path = "D:\\";
//
//	// 字符编码格式 目前支持 gbk 或 utf-8
//	public static String input_charset = "utf-8";
//	
//	// 签名方式 不需修改
//	public static String sign_type = "MD5";
	
	


	// ↓↓↓↓↓↓↓↓↓↓请在这里配置基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串 2088101568355903
	//public static String			partner							= String.valueOf(alipay_config.getString("login_partner"));							// "2088101568351631";//"2088201564862550";//=
																																							// "2088401678374334";

	// 交易安全检验码，由数字和字母组成的32位字符串 ldpdr169a3e5n86i9f92sn3cznbyx72s
	//public static String			key								= String.valueOf(alipay_config.getString("login_key"));								// "r7oltisl5570yp07xtsa0q6y4gni8ltz";//"2ttncpw4rjezx7iseue0m7r5bm9okrye";//"o04sv8yg7wq9m97nkmedkr3olh5469aa";

	/**
	 * HTTP形式消息验证地址
	 */
    // public static final String HTTP_VERIFY_URL =
    // "http://notify.alipay.com/trade/notify_query.do?";

	// 当前页面跳转后的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	// 域名不能写成http://localhost/alipay.auth.authorize_jsp_utf8/return_url.jsp ，否则会导致return_url执行无效
	// 124.74.76.70:8002
	// http://www.nikestore.com.cn/member/register.htm
	// http://www.nikestore.com.cn/enterpayment.htm
    // public static final String return_url =
    // String.valueOf(alipay_config.getString("login_return_url")); //
    // "http://180.168.119.194:18005/unitLogin.htm";
    //
    // public static final String query_return_url =
    // String.valueOf(alipay_config.getString("login_query_return_url")); //
    // "http://180.168.119.194:18005/enterpayment.htm";

    // public static final String service =
    // String.valueOf(alipay_config.getString("service"));

    // public static final String regsource =
    // String.valueOf(alipay_config.getString("regsource"));
    //
    // public static final String promote_url =
    // String.valueOf(alipay_config.getString("promote_url"));
    //
    // // 查询服务
    // public static final String query_service =
    // String.valueOf(alipay_config.getString("query_service"));
    //
    // public static final String target_service =
    // String.valueOf(alipay_config.getString("target_service"));
    //
    // public static final String alipay_path =
    // String.valueOf(alipay_config.getString("alipay_path"));
    //
    // public static final String alipayName =
    // String.valueOf(alipay_config.getString("alipayName"));
    //
    // // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	// 调试用，创建TXT日志路径
	//public static String			log_path						= "D:\\alipay_log_" + System.currentTimeMillis() + ".txt";

	// 字符编码格式 目前支持 gbk 或 utf-8
	//public static String			input_charset					= String.valueOf(alipay_config.getString("input_charset"));								;

	// 签名方式 不需修改
	//public static String			sign_type						= String.valueOf(alipay_config.getString("sign_type"));

	// 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
    // public static final String transport =
    // String.valueOf(alipay_config.getString("transport"));
    //
    // // alipay用户等级
    // public static final String vip_userGrade =
    // String.valueOf(alipay_config.getString("vip_userGrade"));
    //
    // // alipay用户等级状态
    // public static final String user_grade_type =
    // String.valueOf(alipay_config.getString("user_grade_type")); ;

	/**
	 * 支付宝快捷登录,自动设值用户 默认的密码,<br>
	 * create by jinxin (2011-8-12 下午01:31:30)
	 */
    // public static final String alipayLoginAutoPassword =
    // String.valueOf(alipay_config.getString("alipayLoginAutoPassword"));
    //
    // /** 支付宝渠道code */
    // public static final String ALIPAY_CHANNEL_CODE =
    // String.valueOf(alipay_config.getString("ALIPAY_CHANNEL_CODE"));
    //
    // public static final String DEFAULT_CHANNEL_CODE =
    // String.valueOf(alipay_config.getString("DEFAULT_CHANNEL_CODE"));
    //
    // public static final String APLIPAY_QUERYTIME_SERVICENAME =
    // String.valueOf(alipay_config.getString("APLIPAY_QUERYTIME_SERVICENAME"));
    //
    // // ----------------支付帐号------------------------------
    // public static final String payment_partner =
    // String.valueOf(alipay_config.getString("payment_partner"));
    //
    // public static final String payment_key =
    // String.valueOf(alipay_config.getString("payment_key"));
    //
    // public static final String payment_seller_email =
    // String.valueOf(alipay_config.getString("payment_seller_email"));
    //
    // public static final String alipay_server_host =
    // String.valueOf(alipay_config.getString("alipay_server_host"));

}
