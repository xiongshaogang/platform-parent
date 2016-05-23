package cn.com.tcxy.cashout;

import org.junit.Test;

import cn.com.tcxy.test.BaseSpringTest;

public class UnionPayCashoutTest extends BaseSpringTest{

	@Test
	public void createOrder() {

		String txnTime = "20150602125111";// --订单发送时间
		String orderId = "201506021251111459";// --商户订单号
		
		UnionPayCashoutOrderVo order = new UnionPayCashoutOrderVo();
		
		order.setOrderId(orderId);
		order.setTxnTime(txnTime);
		order.setAccNo("6216261000000000018");
		order.setCustomerNm("全渠道");
		order.setTxnAmt("1");
		
		UnionPayCashoutHandler cashoutHandler = new UnionPayCashoutHandler();
		UnionPayCashoutOrderVo cashoutOrderVo = cashoutHandler.createOrder(order);
		System.out.println(cashoutOrderVo.getStatus());
	}
	
}
