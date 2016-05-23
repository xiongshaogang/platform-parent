package cn.com.tcxy.cashout.chinapay.vo;

import cn.com.tcxy.cashout.Constant;

public class ChinaPayRequestVo {
    private String merId;
    private String merDate;
    private String merSeqId;
    private String cardNo;
    private String usrName;
    private String openBank;
    private String prov;
    private String city;
    private String transAmt;
    private String purpose;//目的
    private String subBank;//支行
    private String flag=Constant.CHINA_PAY_PRIVATE_FLAG;//“00”对私，“01”对公
    private String version=Constant.CHINA_PAY_VERSION;
    private String signFlag="1";
    private String termType;//07：互联网 08：移动端
    private String chkValue;
    
    public ChinaPayRequestVo(){
        
    }
    public ChinaPayRequestVo(String merId, String merDate, String merSeqId, String cardNo, String usrName,
            String openBank, String prov, String city, String transAmt, String purpose, String subBank, String flag,
            String version, String termType) {
        super();
        this.merId = merId;
        this.merDate = merDate;
        this.merSeqId = merSeqId;
        this.cardNo = cardNo;
        this.usrName = usrName;
        this.openBank = openBank;
        this.prov = prov;
        this.city = city;
        this.transAmt = transAmt;
        this.purpose = purpose;
        this.subBank = subBank;
        this.flag = flag;
        this.version = version;
        this.termType = termType;
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
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getUsrName() {
        return usrName;
    }
    public void setUsrName(String usrName) {
        this.usrName = usrName;
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
    public String getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public String getSubBank() {
        return subBank;
    }
    public void setSubBank(String subBank) {
        this.subBank = subBank;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getTermType() {
        return termType;
    }
    public void setTermType(String termType) {
        this.termType = termType;
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
        .append(this.cardNo)
        .append(this.usrName)
        .append(this.openBank)
        .append(this.prov)
        .append(this.city)
        .append(this.transAmt)
        .append(this.purpose)
        .append(this.subBank)
        .append(this.flag)
        .append(this.version)
        .append(this.termType);
        return sb.toString();
    }
    @Override
    public String toString() {
        return "ChinaPayRequestVo [merId=" + merId + ", merDate=" + merDate + ", merSeqId=" + merSeqId + ", cardNo="
                + cardNo + ", usrName=" + usrName + ", openBank=" + openBank + ", prov=" + prov + ", city=" + city
                + ", transAmt=" + transAmt + ", purpose=" + purpose + ", subBank=" + subBank + ", flag=" + flag
                + ", version=" + version + ", termType=" + termType + ", chkValue=" + chkValue + "]";
    }
    
}
