package cn.com.tcxy.payment;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.com.tcxy.payment.vo.request.AlipayRequest;

/**
 *  支付宝2.0
 *  @author Chenshi
 * 
 */
public class AlipayPaymentHandler extends PaymentHandler{
	
	private AlipayRequest alipayRequest;
	
	public AlipayPaymentHandler(OrderVO order){
		alipayRequest =  new AlipayRequest();
		alipayRequest.setSubject(order.getGoods());
		alipayRequest.setTotal_fee(String.valueOf(order.getPrice()));
		alipayRequest.setOut_trade_no(order.getOrderNo());
	}
	
	@Override
	public Map<String, String> sendOrderCreateRequest() {
        // TODO Auto-generated method stub
        return null;
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
		Map<String, String> map = alipayRequest.getRequestParam();
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
		return param;
	}

    @Override
    public Map<String, String> getRequestParam4Wap() {
        Map<String, String> map = alipayRequest.getRequestParam4Wap();
        return map;
    }

}
