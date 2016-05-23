package cn.com.tcxy.payment;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import cn.com.tcxy.payment.vo.request.UnionPayRequest;
import cn.com.tcxy.test.BaseSpringTest;

public class UnionPaymentHandlerTest extends BaseSpringTest{

	@Test
	public void createBack() {
		OrderVO order =  new OrderVO();
		try {
			order.setCreateTime(DateUtils.parseDate("2015-03-04 17:19:10", "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//order.setGoods("咨询服务");
		//order.setIp("127.0.0.1");		
		order.setOrderNo("201503041255466670");
		order.setPrice(BigDecimal.valueOf(1));
		
		UnionPaymentHandler unionPaymentHandler= new UnionPaymentHandler(order);
		unionPaymentHandler.generateOrderSignatureForApp();
		
	}
	
	@Test
	public void createFront() {
		OrderVO order =  new OrderVO();
		try {
			order.setCreateTime(DateUtils.parseDate("2015-03-04 17:19:10", "yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//order.setGoods("咨询服务");
		//order.setIp("127.0.0.1");		
		order.setOrderNo("201503041255466670");
		order.setPrice(BigDecimal.valueOf(11));
		
		UnionPaymentHandler unionPaymentHandler= new UnionPaymentHandler(order);
		System.out.println(unionPaymentHandler.getOrderCreateUrl());
		
	}
	
	@Test
	public void query() {
		OrderVO order =  new OrderVO();
		order.setOrderNo("20150305142420274");
		try {
			order.setCreateTime(DateUtils.parseDate("20150305142420", "yyyyMMddHHmmss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		order.setPrice(BigDecimal.valueOf(0.01));
		
		UnionPaymentHandler unionPaymentHandler= new UnionPaymentHandler(order);
		unionPaymentHandler.sendOrderQueryRequest();
		
	}
	
	
	@Test
	public void refund() {
		OrderVO order =  new OrderVO();
		order.setOrderNo("20150305162244683");
		try {
			order.setCreateTime(DateUtils.parseDate("20150305162244", "yyyyMMddHHmmss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		order.setPrice(BigDecimal.valueOf(0.01));
		order.setType(UnionPayRequest.TXN_TYPE_PAYMENT_YU_CANCEL);
		
		UnionPaymentHandler unionPaymentHandler= new UnionPaymentHandler(order);
		unionPaymentHandler.sendOrderRefundRequest();
	}
	
}
