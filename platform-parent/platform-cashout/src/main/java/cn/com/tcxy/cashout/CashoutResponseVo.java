/**
 * 
 */
package cn.com.tcxy.cashout;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author llr
 * @date 2015年9月19日
 */
public class CashoutResponseVo {
    /**
     * 提现状态: 申请
     */
    public static final String CAST_OUT_STATUS_REQUEST="request";
    /**
     * 提现状态:受理中     
     */
    public static final String CAST_OUT_STATUS_ACCEPT="accept";
    /**
     * 提现状态:拒绝
     */
    public static final String CAST_OUT_STATUS_REJECT="reject";
    /**
     * 提现状态:成功
     */
    public static final String CAST_OUT_STATUS_SUCCESS="success";
    /**
     * 提现状态:失败
     */
    public static final String CAST_OUT_STATUS_FAILURE="failure";
    /**
     * 系统异常
     */
    public static final String CAST_OUT_SYSTEM_STATUS_FAILURE="systemfailure";
    
        private String requestId;//订单请求id
        private String merDate;//订单交易日期 YYYYMMDD
        private String accountId;

        private String accountNum;

        private String accountName;

        private BigDecimal amount;
        //开户行
        private String openBank;
        
        //开户省
        private String prov;
         //开户市
        private String city;

        private String status;

        private String receiptNo;

        private Integer receiptSerialNo;

        private String errorMsg;

  


        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId == null ? null : accountId.trim();
        }

       

        public String getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(String accountNum) {
            this.accountNum = accountNum == null ? null : accountNum.trim();
        }

       
        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName == null ? null : accountName.trim();
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status == null ? null : status.trim();
        }

        

        public String getReceiptNo() {
            return receiptNo;
        }

        public void setReceiptNo(String receiptNo) {
            this.receiptNo = receiptNo == null ? null : receiptNo.trim();
        }

        public Integer getReceiptSerialNo() {
            return receiptSerialNo;
        }

        public void setReceiptSerialNo(Integer receiptSerialNo) {
            this.receiptSerialNo = receiptSerialNo;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg == null ? null : errorMsg.trim();
        }

        public String getOpenBank() {
            return openBank;
        }

        public void setOpenBank(String openBank) {
            this.openBank = openBank;
        }

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getMerDate() {
            return merDate;
        }

        public void setMerDate(String merDate) {
            this.merDate = merDate;
        }

       

}
