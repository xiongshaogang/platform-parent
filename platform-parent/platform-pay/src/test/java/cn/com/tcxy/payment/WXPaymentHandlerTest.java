package cn.com.tcxy.payment;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import cn.com.tcxy.test.BaseSpringTest;

public class WXPaymentHandlerTest extends BaseSpringTest{
	
	@Test
	public void create() throws InterruptedException{
		
		
		OrderVO order =  new OrderVO();
		try {
			order.setCreateTime(DateUtils.parseDate("2015-03-04 17:19:10", "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setGoods("咨询服务");
		order.setIp("127.0.0.1");		
		order.setOrderNo("201503041255466670");
		order.setPrice(BigDecimal.valueOf(1));
		
		PaymentHandler handler = PaymentHandler.getInstance(order, "wechat");
		for(int i =0;i<100;i++){
			Thread.sleep(1000);
			String paymentSignature =  handler.generateOrderSignatureForApp();
			System.out.println(paymentSignature);
		}
		
		
		
	}

}
