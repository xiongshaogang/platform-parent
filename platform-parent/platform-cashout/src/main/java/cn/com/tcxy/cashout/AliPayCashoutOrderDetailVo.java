package cn.com.tcxy.cashout;

import java.util.Date;

/**
 * 	批量付款到支付宝 
 *  异步通知参数类
 * @author DELL
 *
 */
public class AliPayCashoutOrderDetailVo {

	//流水号
	private String serialNumber;	
	//收款账号
	private String  accNo; 			
	//真实姓名
	private String  name;			
	//付款金额
	private String 	sum;			
	//备注
	private String remark;			
	//成功状态
	private String status; 			
	//原因
	private String reason;			
	//支付宝内部流水号
	private String alipaySerialNumber;	
	//完成时间
	private String finishDate;		

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAlipaySerialNumber() {
		return alipaySerialNumber;
	}

	public void setAlipaySerialNumber(String alipaySerialNumber) {
		this.alipaySerialNumber = alipaySerialNumber;
	}

	

	
}
