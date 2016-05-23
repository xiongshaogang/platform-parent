package cn.com.tcxy.payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 通用订单类
 * 
 */
public class OrderVO {
	
	//订单号
	private String orderNo;
	
	//订单价格
	private BigDecimal price;
	
	//物品
	private String goods;
	
	//物品
	private Date createTime;
	
	//ip
	private String ip;
	
	//交易类型
	private String type;
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
