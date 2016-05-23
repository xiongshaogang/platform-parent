package cn.com.tcxy.payment.vo;


public abstract class PaymentResponse {
	
	public static final String ORDERSTATUS_SUCCESS ="success";
	public static final String ORDERSTATUS_FAIL ="fail";
	
	//获取订单状态
	public abstract String getOrderStatus();
	
	//检查订单签名
	public abstract boolean checkSignature();
	
}
