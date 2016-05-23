package cn.com.tcxy.cashout;

public class UnionPayCashoutOrderVo {	
	//银行卡号
	private String accNo;
	//持卡人姓名
	private String customerNm;
	//对应系统的订单号
	private String orderId;
	//提交时间 YYYYMMDDhhmmss
	private String txnTime;
	//银行的查询流水号
	private String queryId;
	//1 成功  ，2 失败
	private int status;
	//提现金额(以分为单位)
	private String txnAmt;
	//订单不成功的错误信息
	private String errMsg;
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCustomerNm() {
		return customerNm;
	}
	public void setCustomerNm(String customerNm) {
		this.customerNm = customerNm;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
public static enum Status {
		
		/** 成功-1. */
		SUCCESS(1),
	    
    	/** 失败 0. */
		FAILURE(2);

	    /** The value. */
    	private Integer value;

	    /**
    	 * Instantiates a new invite type.
    	 *
    	 * @param value the value
    	 */
    	private Status(Integer value) {
	        this.value = value;
	    }

	    /**
    	 * Gets the value.
    	 *
    	 * @return the value
    	 */
	    public Integer getValue() {
	        return value;
	    }

	    /**
    	 * Sets the value.
    	 *
    	 * @param value the value to set
    	 */
	    public void setValue(Integer value) {
	        this.value = value;
	    }

	}
}
