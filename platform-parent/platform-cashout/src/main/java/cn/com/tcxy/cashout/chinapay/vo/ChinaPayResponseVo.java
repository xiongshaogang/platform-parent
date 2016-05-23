package cn.com.tcxy.cashout.chinapay.vo;

public class ChinaPayResponseVo {
    private String responseCode;
    private String merId;
    private String merDate;
    private String merSeqId;
    private String cpDate;
    private String cpSeqId;
    private String transAmt;
    private String stat;
    private String cardNo;
    private String chkValue;

    
    public ChinaPayResponseVo(){
        
    }
    
    public ChinaPayResponseVo(String responseCode, String merId, String merDate, String merSeqId, String cpDate,
            String cpSeqId, String transAmt, String stat, String cardNo, String chkValue) {
        super();
        this.responseCode = responseCode;
        this.merId = merId;
        this.merDate = merDate;
        this.merSeqId = merSeqId;
        this.cpDate = cpDate;
        this.cpSeqId = cpSeqId;
        this.transAmt = transAmt;
        this.stat = stat;
        this.cardNo = cardNo;
        this.chkValue = chkValue;
    }



    public String getResponseCode() {
        return responseCode;
    }



    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }



    public String getMerId() {
        return merId;
    }



    public void setMerId(String merId) {
        this.merId = merId;
    }



    public String getMerDate() {
        return merDate;
    }



    public void setMerDate(String merDate) {
        this.merDate = merDate;
    }



    public String getMerSeqId() {
        return merSeqId;
    }



    public void setMerSeqId(String merSeqId) {
        this.merSeqId = merSeqId;
    }



    public String getCpDate() {
        return cpDate;
    }



    public void setCpDate(String cpDate) {
        this.cpDate = cpDate;
    }



    public String getCpSeqId() {
        return cpSeqId;
    }



    public void setCpSeqId(String cpSeqId) {
        this.cpSeqId = cpSeqId;
    }



    public String getTransAmt() {
        return transAmt;
    }



    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }



    public String getStat() {
        return stat;
    }



    public void setStat(String stat) {
        this.stat = stat;
    }



    public String getCardNo() {
        return cardNo;
    }



    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    public String getChkValue() {
        return chkValue;
    }



    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }



    public String buildSingMessage(){
        StringBuilder sb=new StringBuilder();
        sb.append(this.responseCode)  
        .append(this.merId)    
        .append(this.merDate)  
        .append(this.merSeqId) 
        .append(this.cpDate)   
        .append(this.cpSeqId)   
        .append(this.transAmt) 
        .append(this.stat)
        .append(this.cardNo);    
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ChinaPayResponseVo [responseCode=" + responseCode + ", merId=" + merId + ", merDate=" + merDate
                + ", merSeqId=" + merSeqId + ", cpDate=" + cpDate + ", cpSeqId=" + cpSeqId + ", transAmt=" + transAmt
                + ", stat=" + stat + ", cardNo=" + cardNo + ", chkValue=" + chkValue + "]";
    }

}
