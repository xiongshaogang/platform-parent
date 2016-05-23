package cn.com.tcxy.payment.vo;

import java.util.Map;

public abstract class PaymentRequest {

	/* 生成签名串
	 * @return
	 */
	public abstract void sign(Map<String,String> map);
	
	/* 获取提交参数
	 * @return
	 */
	public abstract Map<String,String> getRequestParam();

    /**
     * 2015年11月11日上午11:07:33<br>
     *
     * @author liuluming
     * @return
     */
    public abstract Map<String, String> getRequestParam4Wap();

    /**
     *2016年4月5日下午4:00:46<br>
     *
     *@author liuluming
     *@return
     */
    public Map<String, String> getRequestParam4Refund() {
        // TODO Auto-generated method stub
        return null;
    }
}
