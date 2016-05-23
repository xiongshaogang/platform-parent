package cn.com.tcxy.payment.vo.request;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.tcxy.payment.vo.PaymentRequest;

import com.alipay.config.AlipayConfig;
import com.alipay.util.RSA;

public class AlipayRequest extends PaymentRequest{
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayRequest.class);

    /**
     * 支付宝合作商户网站唯一订单号
     */
	private String out_trade_no;
    /**
     * 支付类型。仅支持：1（商品购买）。
     */
	private String payment_type="1";
    /**
     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     */
	private String body = "咨询服务";
    /**
     * 支付宝服务器主动通知商户网站里指定的页面http路径
     */
	private String notify_url;
    /**
     * 支付宝处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径
     */
    private String return_url;
    /**
     * 卖家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
     */
    private String seller_id;
    /**
     * 接口名称
     */
    private String service;
    /**
     * 商品的标题/交易标题/订单标题/订单关键字等。 该参数最长为128个汉字
     */
	private String subject;
    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
	private String total_fee;

    private String private_key;

    /**
     * 商户网站使用的编码格式，仅支持utf-8。
     */
    private String input_charset;

    /**
     * 签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
     */
    private String partner;

    private String seller_user_id;
	
	public AlipayRequest(){
	    notify_url = AlipayConfig.notify_url;
        seller_id = AlipayConfig.seller_id;
        seller_user_id=AlipayConfig.partner;
        private_key = AlipayConfig.private_key;
        input_charset = AlipayConfig.input_charset;
        partner = AlipayConfig.partner;
        return_url=AlipayConfig.return_url;
	}
	
	@Override
	public void sign(Map<String, String> map) {
		
		StringBuffer sb = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		
		String param = sb.toString().substring(0, sb.toString().length()-1);
		
        String params = RSA.sign(param, private_key, input_charset);
		String sign = URLEncoder.encode(params);
        map.put("sign", "\"" + sign + "\"");
        map.put("sign_type", "\"" + "RSA" + "\"");
	}

	@Override
	public Map<String, String> getRequestParam() {
		
		Map<String, String> map =  new HashMap<String, String>();
        map.put("_input_charset", "\"" + input_charset + "\"");
		map.put("out_trade_no", "\""+out_trade_no+"\"");
        map.put("partner", "\"" + partner + "\"");
		map.put("payment_type", "\""+payment_type+"\"");
		map.put("body", "\""+body+"\"");
		map.put("notify_url", "\""+notify_url+"\"");
		map.put("seller_id", "\""+seller_id+"\"");
		map.put("service", "\""+"mobile.securitypay.pay"+"\"");
		map.put("subject", "\""+subject+"\"");
		map.put("total_fee", "\""+total_fee+"\"");

		sign(map);
		LOGGER.info("【本次支付请求参数】："+map.toString());
		return map;
	}

    @Override
    public Map<String, String> getRequestParam4Wap() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("_input_charset", input_charset);
        map.put("out_trade_no", out_trade_no);
        map.put("partner", partner);
        map.put("payment_type", payment_type);
        map.put("body", body);
        map.put("notify_url", notify_url);
        map.put("seller_id", seller_id);
        map.put("service", "alipay.wap.create.direct.pay.by.user");
        map.put("subject", subject);
        map.put("return_url", return_url);
        map.put("total_fee", total_fee);

        // sign(map);

        return map;
    }
    @Override
    public Map<String, String> getRequestParam4Refund() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("_input_charset", input_charset);
        map.put("partner", partner);
        map.put("seller_user_id", seller_user_id);
        map.put("notify_url", notify_url);
        map.put("seller_id", seller_id);
        map.put("service", "refund_fastpay_by_platform_pwd");
        map.put("return_url", return_url);

        // sign(map);

        return map;
    }

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller_user_id() {
        return seller_user_id;
    }

    public void setSeller_user_id(String seller_user_id) {
        this.seller_user_id = seller_user_id;
    }
    
}
