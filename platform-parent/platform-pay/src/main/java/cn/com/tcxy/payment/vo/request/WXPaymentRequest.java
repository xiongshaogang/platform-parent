package cn.com.tcxy.payment.vo.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import cn.com.tcxy.payment.prop.PaymentProperties;
import cn.com.tcxy.payment.vo.PaymentRequest;

import com.alipay.sign.MD5;

public class WXPaymentRequest  extends PaymentRequest{
	
	//：开放平台账户的唯一标识
	private String appid;
	
	private String appKey;
	
	//：由开发者自定义，可用于订单的查询与跟踪，建议根据支付用户信息生成此id
	private String  traceid;
	
	//packageParams.put("bank_type", "WX"); //商品描述   
	private String  bank_type="WX";
	
	//packageParams.put("body", "测试商品名称"); //商品描述   
	private String  body;
	
	//packageParams.put("notify_url", notify_url); //接收财付通通知的URL 
	private String  notify_url;
	
	//packageParams.put("partner", partner); //商户号    
	private String  partner;
	
	//packageParams.put("out_trade_no", "out_trade_no"); //商家订单号  
	private String  out_trade_no;
	
	//packageParams.put("total_fee", "1"); //商品金额,以分为单位  
	private String  total_fee;
	
	//packageParams.put("spbill_create_ip", request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP  
	private String  spbill_create_ip;
	
	//packageParams.put("fee_type", "1"); //币种，1人民币   66
	private String  fee_type = "1";
	
	//packageParams.put("input_charset", "GBK"); //字符编码
	private String  input_charset="GBK";
	
	private String key;
	
	private String charset = "GBK";
	
	//初始化加载
	public WXPaymentRequest(){
		appid = PaymentProperties.wechatProperties.getProperty("wechat.app.id");
		appKey =PaymentProperties.wechatProperties.getProperty("wechat.app.key");
		notify_url = PaymentProperties.wechatProperties.getProperty("wechat.back.url");
		partner = PaymentProperties.wechatProperties.getProperty("wechat.mer.id");
		key = PaymentProperties.wechatProperties.getProperty("wechat.mer.key");
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
			//要采用URLENCODER的原始值！
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
		System.out.println("sha1 sb:" + params);
		
		String sign = getSha1(params);
		
		map.put("app_signature",sign);
		map.put("sign_method", "sha1");
		
	}

	@Override
	public Map<String, String> getRequestParam() {
		
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		signParams.put("appid", appid);
		signParams.put("appkey", appKey);
		signParams.put("noncestr", MD5.sign(String.valueOf(new Random().nextInt(10000)), "","UTF-8"));
		signParams.put("package", generatePackage());
		signParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		signParams.put("traceid", traceid);
		
		sign(signParams);
		
		return signParams;
	}
	
    @Override
    public Map<String, String> getRequestParam4Wap() {
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appid", appid);
        signParams.put("appkey", appKey);
        signParams.put("noncestr", MD5.sign(String.valueOf(new Random().nextInt(10000)), "", "UTF-8"));
        signParams.put("package", generatePackage());
        signParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        signParams.put("traceid", traceid);

        // sign(signParams);

        return signParams;
    }

	//生成package参数
	private String generatePackage(){
		
		SortedMap<String, String> map = new TreeMap<String, String>();
		
		if(StringUtils.isNotEmpty(bank_type)){map.put("bank_type",bank_type);}
		if(StringUtils.isNotEmpty(body)){map.put("body",body);}
		if(StringUtils.isNotEmpty(notify_url)){map.put("notify_url",notify_url);}
		if(StringUtils.isNotEmpty(partner)){map.put("partner",partner);}
		if(StringUtils.isNotEmpty(out_trade_no)){map.put("out_trade_no",out_trade_no);}
		if(StringUtils.isNotEmpty(total_fee)){map.put("total_fee",total_fee);}
		if(StringUtils.isNotEmpty(spbill_create_ip)){map.put("spbill_create_ip",spbill_create_ip);}
		if(StringUtils.isNotEmpty(input_charset)){map.put("input_charset",input_charset);}
		
		
		StringBuffer sb = new StringBuffer();
		StringBuffer sbUrl = new StringBuffer();
		Set es = map.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
				try {
					sbUrl.append(k + "=" + URLEncoder.encode(v, this.charset).replace("+", "%20") + "&");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("========md5之前========"+sb.toString()+"key=" + key);
		String sign = MD5.sign(sb.toString(), "key=" + key, input_charset);
		System.out.println("========md5之后========"+sign);
		
		String packageValue = sbUrl.append("sign=" + sign).toString();
		return packageValue;
	}
	
	//Sha1签名
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("GBK"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] argv){
		System.out.println(getSha1("chenshi"));
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}
	
	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
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

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
