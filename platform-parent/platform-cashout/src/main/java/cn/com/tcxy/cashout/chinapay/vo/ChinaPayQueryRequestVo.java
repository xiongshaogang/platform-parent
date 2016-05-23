package cn.com.tcxy.cashout.chinapay.vo;

import cn.com.tcxy.cashout.Constant;

public class ChinaPayQueryRequestVo {
    private String merId;
    private String merDate;
    private String merSeqId;
    private String version=Constant.CHINA_PAY_QUERY_VERSION;
    private String signFlag="1";
    private String chkValue;
    
    public ChinaPayQueryRequestVo(){
        
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
   
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getSignFlag() {
        return signFlag;
    }
    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag;
    }
    public String getChkValue() {
        return chkValue;
    }
    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }
    
    public String buildSingMessage(){
        StringBuilder sb=new StringBuilder();
        sb.append(this.merId)
        .append(this.merDate)
        .append(this.merSeqId)
        .append(this.version);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ChinaPayQueryRequestVo [merId=" + merId + ", merDate=" + merDate + ", merSeqId=" + merSeqId
                + ", version=" + version + ", signFlag=" + signFlag + ", chkValue=" + chkValue + "]";
    }
    
    
}
