package cn.com.tcxy.cashout;

import java.util.List;

/**
 * 批量付款到支付宝 
 *  请求参数类
 * @author DELL
 *
 */
public class AliPayCashoutOrderVo {

	
	private  String aliemail="shuqian@tcxy.com.cn";   //付款账号
	
	private  String account_name="上海数乾数码科技有限公司"; //付款账户名
	
	private String pay_date;	//付款当天日期  20150610
	
	private String batch_no;	//批次号  201506100001
	
	private String batch_fee;	//付款总金额
	
	private String batch_num; 	//付款笔数

	
	
	private String  notify_time;		//通知时间
	
	private String notify_type;			//通知类型
	
	private String notify_id;			//通知校验ID
	
	private String pay_user_id;			//付款账号ID
	
	private String pay_user_name;		//付款账号姓名
	
	private String pay_account_no;		//付款账号
	
	private List<AliPayCashoutOrderDetailVo> details;		//付款详细数据
	

	public List<AliPayCashoutOrderDetailVo> getDetails() {
		return details;
	}

	public void setDetails(List<AliPayCashoutOrderDetailVo> details) {
		this.details = details;
	}

	public String getAliemail() {
		return aliemail;
	}

	public void setAliemail(String aliemail) {
		this.aliemail = aliemail;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getPay_date() {
		return pay_date;
	}

	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBatch_fee() {
		return batch_fee;
	}

	public void setBatch_fee(String batch_fee) {
		this.batch_fee = batch_fee;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getPay_user_id() {
		return pay_user_id;
	}

	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}

	public String getPay_user_name() {
		return pay_user_name;
	}

	public void setPay_user_name(String pay_user_name) {
		this.pay_user_name = pay_user_name;
	}

	public String getPay_account_no() {
		return pay_account_no;
	}

	public void setPay_account_no(String pay_account_no) {
		this.pay_account_no = pay_account_no;
	}


}
