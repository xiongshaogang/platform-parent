package cn.com.tcxy.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.com.tcxy.payment.prop.PaymentProperties;
import cn.com.tcxy.payment.vo.request.WXPaymentRequest;

import com.alibaba.fastjson.JSON;

public class WXPaymentHandler extends PaymentHandler {

	private static String prepay_url = "https://api.weixin.qq.com/pay/genprepay?access_token=ACCESS_TOKEN";
	
	private static String accessToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private String accessToken;
	
	private WXPaymentRequest wXPaymentRequest;
	
	public WXPaymentHandler(OrderVO order){
		wXPaymentRequest =  new WXPaymentRequest();
		wXPaymentRequest.setBody(order.getGoods());
		wXPaymentRequest.setTraceid(order.getOrderNo());
		wXPaymentRequest.setOut_trade_no(order.getOrderNo());
		wXPaymentRequest.setTotal_fee(String.valueOf(order.getPrice()
				.multiply(BigDecimal.valueOf(100)).intValue()));
		wXPaymentRequest.setSpbill_create_ip(order.getIp());
		
	}
	
	@Override
	public Map<String, String> sendOrderCreateRequest() {
		String url = prepay_url.replace("ACCESS_TOKEN", getAccessToken());
		Map<String, String> map = sendHttpRequest(url,wXPaymentRequest.getRequestParam());
		return map;
	}

	@Override
	public Map<String, String> sendOrderRefundRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> sendOrderQueryRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrderCreateUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String generateOrderSignatureForApp() {
		Map<String, String> map = sendOrderCreateRequest();
		if (map.containsKey("prepayid")) {
			return map.get("prepayid");
		}
		return null;
	}
	
	private long lastTime ;
	private long now;
	private String getAccessToken(){
		
		now = System.currentTimeMillis(); 
		if(now - lastTime > 7200000){
			RefreshToken();
			lastTime = now;
		}
		
		return accessToken;
	}
	
	/**
	 * 获取access token
	 */
	private void RefreshToken(){
		String url = accessToken_url
				.replace("APPID",PaymentProperties.wechatProperties.getProperty("wechat.app.id"))
				.replace("APPSECRET", PaymentProperties.wechatProperties.getProperty("wechat.app.secret"));
		
		Map<String, String> map = sendHttpRequest(url,null);
		if(!map.isEmpty() && map.containsKey("access_token")){
			System.out.println("[SUCCESS]获取token成功");
			accessToken = map.get("access_token");
			return;
		}
		System.out.println("[FAIL]获取token失败");
	}
	
	/**
	 * 微信请求方式 
	 */
	private Map<String, String> sendHttpRequest(String url,
			Map<String, String> param) {
		
		String requestData = param==null?"":JSON.toJSONString(param);
		System.out.println("=========================start================================");
		System.out.println("[==]"+url);
		System.out.println("[>>]"+requestData);
		
		
		HttpPost method = new HttpPost(url); 
		StringEntity entity = new StringEntity(requestData,"utf-8");  
        entity.setContentEncoding("UTF-8");    
        entity.setContentType("application/json");    
        method.setEntity(entity);
        
        DefaultHttpClient httpClient = new DefaultHttpClient();  
        try {
			HttpResponse result = httpClient.execute(method);
			String resData = EntityUtils.toString(result.getEntity(),"UTF-8");  
			System.out.println("[<<]"+resData);
			Map<String,String> map = (Map<String,String>)JSON.parse(resData); 
			
			System.out.println("=========================end================================");
			
			return map;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
       
        System.out.println("=========================end================================");
		return null;
	}

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.tcxy.payment.PaymentHandler#getRequestParam()
     */
    @Override
    public Map<String, String> getRequestParam4Wap() {
        Map<String, String> map = wXPaymentRequest.getRequestParam();
        return map;
    }

}
