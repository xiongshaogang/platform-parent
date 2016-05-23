package cn.com.tcxy.payment;

import java.util.Map;

public abstract class PaymentHandler {
	
	//提交订单地址
	private String endPoint;
	public static final String PAYCHANNEL_ALIPAY="alipay";
	public static final String PAYCHANNEL_UNIONPAY="unionpay";

	/**
	 * 生成订单
	 */
	public abstract Map<String,String> sendOrderCreateRequest();
	
	/**
	 * 取消订单
	 */
	public abstract Map<String,String> sendOrderRefundRequest();
	
	/**
	 * 查询订单状态
	 */
	public abstract Map<String,String> sendOrderQueryRequest();
	
	/**
	 * 生成订单请求url for web
	 */
	public abstract String getOrderCreateUrl();
	
    /**
     * 2015年11月10日下午1:24:05<br>
     * 获取请求参数
     *
     * @author liuluming
     * @return
     */
    public abstract Map<String, String> getRequestParam4Wap();
//	/**
//	 * 获取平台支付之后，回call的参数
//	 */
//	public abstract  PaymentResponse getOrderFromPlatform(Map<String,String> map);
//	
	
	/**
	 * 生成订单支付 签名串 for app
	 */
	public abstract String generateOrderSignatureForApp();
	
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	/**
	 * 获取处理类
	 * 
	 */
	public static PaymentHandler getInstance(OrderVO order,String paymentWay){
		PaymentChannel channel = PaymentChannel.get(paymentWay);
		switch(channel){
			case unionpay: return new UnionPaymentHandler(order);
			case wechat: return new WXPaymentHandler(order);
			case alipay: return new AlipayPaymentHandler(order);
			default:return null;
		}
	}
	
    public enum PaymentChannel {
		unionpay,
		alipay,
		wechat;
		
		public static PaymentChannel get(String paymentWay) {  
	        for (PaymentChannel d : PaymentChannel.values()) {  
	           if(d.name().equals(paymentWay)){
	        	   return d;
	           }
	        }
	        return null;  
	    }  
	}

}
