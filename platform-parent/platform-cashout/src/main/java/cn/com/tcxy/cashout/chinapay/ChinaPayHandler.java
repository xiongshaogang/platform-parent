package cn.com.tcxy.cashout.chinapay;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import cn.com.tcxy.cashout.CashoutResponseVo;
import cn.com.tcxy.cashout.Constant;
import cn.com.tcxy.cashout.chinapay.config.ChinaPayConfig;
import cn.com.tcxy.cashout.chinapay.vo.ChinaPayQueryRequestVo;
import cn.com.tcxy.cashout.chinapay.vo.ChinaPayQueryResponseVo;
import cn.com.tcxy.cashout.chinapay.vo.ChinaPayRequestVo;
import cn.com.tcxy.cashout.chinapay.vo.ChinaPayResponseVo;
import cn.com.tcxy.cashout.util.HttpReqRespUtil;
import cn.com.tcxy.cashout.util.UtilDate;
import cn.com.tcxy.cashout.util.httpClient.HttpProtocolHandler;
import cn.com.tcxy.cashout.util.httpClient.HttpRequest;
import cn.com.tcxy.cashout.util.httpClient.HttpResponse;
import cn.com.tcxy.cashout.util.httpClient.HttpResultType;


/**
 * China Pay请求,返回处理类
 * @author llr
 *
 */
public class ChinaPayHandler {
    private static Logger loger =Logger.getLogger(ChinaPayHandler.class);
   /**
    * 对外提供的代付接口
    * @param accountCashOut
    * @return
    * @auth llr
    * @date 2015年9月19日
    */
    public CashoutResponseVo chinaPay(CashoutResponseVo cashoutResponseVo){
        
        ChinaPayRequestVo chinaPayRequestVo=transfer2RequestVo(cashoutResponseVo);
        try {
            Map <String,String>resp= this.singlePay(chinaPayRequestVo);
            cashoutResponseVo.setStatus(resp.get("status"));
            cashoutResponseVo.setErrorMsg(resp.get("msg"));
            cashoutResponseVo.setReceiptNo(resp.get("sequence"));
            cashoutResponseVo.setMerDate(resp.get("merDate"));
        } catch (Exception e) {
            loger.error("代付失败", e);
            cashoutResponseVo.setStatus(CashoutResponseVo.CAST_OUT_STATUS_FAILURE);
            cashoutResponseVo.setErrorMsg("失败");
        }
       
        return cashoutResponseVo;
        
    }
    /**
     * 把请求的Vo封装成chinapay银行的请求Vo
     * @param accountCashOut
     * @auth llr
     * @date 2015年9月19日
     */
private ChinaPayRequestVo transfer2RequestVo(CashoutResponseVo cashoutResponseVo) {
    ChinaPayRequestVo chinaPayRequestVo=new ChinaPayRequestVo();
    chinaPayRequestVo.setCardNo(cashoutResponseVo.getAccountNum());//银行卡号
    chinaPayRequestVo.setUsrName(cashoutResponseVo.getAccountName());//持卡人姓名
    chinaPayRequestVo.setMerSeqId(cashoutResponseVo.getRequestId());//orderId
    BigDecimal multiper=new BigDecimal(100);
    loger.info("input para:"+cashoutResponseVo.getAmount().toString());
    //提现金额(以分为单位)
    //String transAmt=cashoutResponseVo.getAmount().multiply(multiper).toString();
    BigDecimal afterMultiply=cashoutResponseVo.getAmount().multiply(multiper);
    String transAmt=afterMultiply.setScale(0,BigDecimal.ROUND_HALF_EVEN).toString();
    loger.info("after para:"+transAmt);
    chinaPayRequestVo.setTransAmt(transAmt);
    //设置交易时间
    String merDate = UtilDate.getDate();
    chinaPayRequestVo.setMerDate(merDate);//提交时间 YYYYMMDD
    chinaPayRequestVo.setProv(cashoutResponseVo.getProv());
    chinaPayRequestVo.setCity(cashoutResponseVo.getCity());
    chinaPayRequestVo.setOpenBank(cashoutResponseVo.getOpenBank());
    chinaPayRequestVo.setFlag(Constant.CHINA_PAY_PRIVATE_FLAG);
    chinaPayRequestVo.setMerId(ChinaPayConfig.CHINAPAY_MERID);
    chinaPayRequestVo.setPurpose(Constant.CHINA_PAY_PURPOSE);
    chinaPayRequestVo.setTermType(Constant.CHINA_PAY_INTERNET_TERM_TYPE);
    chinaPayRequestVo.setVersion(Constant.CHINA_PAY_VERSION);
    
    return chinaPayRequestVo;
}
    /**
    * 处理请求
    * @param chinaPayRequestVo
    * @return
    * @throws Exception
    */
    public Map <String,String> singlePay(ChinaPayRequestVo chinaPayRequestVo) throws Exception{
        Map <String,String>resp=new HashMap<String,String>();
        //目前没有支行
        chinaPayRequestVo.setSubBank("");
        String message=chinaPayRequestVo.buildSingMessage();
       String merId=ChinaPayConfig.CHINAPAY_MERID;
       String chkValue="";
       String reqURL=ChinaPayConfig.CHINAPAY_REQUEST_URL; 
       chkValue=ChinaPaySign.sign(message, merId);
       chinaPayRequestVo.setChkValue(chkValue);
       NameValuePair []formparams = HttpReqRespUtil.buildRequestParams(chinaPayRequestVo);
       HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
       HttpRequest request = new HttpRequest(HttpResultType.BYTES);
       //设置编码集
       request.setCharset(ChinaPayConfig.CHINAPAY_INPUT_CHARSET);
       request.setUrl(reqURL);
       request.setParameters(formparams);
       HttpResponse response = (HttpResponse) httpProtocolHandler.execute(request,"","");
       if (response == null) {
           return null;
       }
       String strResult = new String(response.getByteResult(),ChinaPayConfig.CHINAPAY_INPUT_CHARSET);
       resp=processResponse(strResult);
      return resp;
   }
  /**
   * 处理返回的结果
   * @param response
   * @throws Exception
   */
    public Map <String,String> processResponse(String response) throws Exception{
        loger.info("input param :"+response);
        Map <String,String>resp=new HashMap<String,String>();
        ChinaPayResponseVo chinaPayResponseVo=null;
        if(!StringUtils.isBlank(response))
          {
             String signMsg=response;
              chinaPayResponseVo=HttpReqRespUtil.
                      KeyValue2Bean(ChinaPayResponseVo.class, 
                              response, ChinaPayConfig.CHINAPAY_RESPONSE_SEPARATOR
                              , ChinaPayConfig.CHINAPAY_RESPONSE_KV_SEPARATOR);
              int dex = signMsg.lastIndexOf("&");
              //返回值验签数据需要除去&chkValue=""的字符串,其他保持不变
              boolean verify=ChinaPaySign.verifySign(signMsg.substring(0,dex), chinaPayResponseVo.getChkValue(), ChinaPayConfig.CHINAPAY_VERIFY_MERID);
              if(verify){
                   resp=processReponseCode(chinaPayResponseVo.getResponseCode(),
                           chinaPayResponseVo.getStat(),
                           chinaPayResponseVo.getCpSeqId(),"0000");
                   }else{
                       loger.error("verify failed");
                       resp.put("status", CashoutResponseVo.CAST_OUT_STATUS_FAILURE);
                       resp.put("msg", "验签失败");
                   }
          }else{
              resp.put("status", CashoutResponseVo.CAST_OUT_STATUS_REQUEST);
              resp.put("msg", "待查询");
              loger.error("response is null");
          }
        resp.put("merDate", chinaPayResponseVo.getMerDate());
        
        return resp;
 
    }
/**
 *  处理交易银行返回code与信息映射
 *   映射成系统需要的值
 * @param responseCode 返回的code
 * @param sate 状态
 * @param cpSeqId 返回的银行端的流水号
 * @param successCode 银行成功的code编码,支付时:0000,查询时:000
 * @return
 * @auth llr
 * @date 2015年9月19日
 */
private Map<String,String>processReponseCode(String responseCode,String sate,String cpSeqId,String successCode) {
    Map <String,String>resp=new HashMap<String,String>();
    String msg=responseCode+":";
    String msgkey="";
    loger.info("code:"+responseCode+",sate:"+sate);
    if(successCode.equals(responseCode)){
        msg+=ChinaPayConfig.getValue(sate);
        if("s".equals(sate)){
            msgkey=CashoutResponseVo.CAST_OUT_STATUS_SUCCESS; 
        }else if("6".equals(sate)){
            msgkey=CashoutResponseVo.CAST_OUT_STATUS_FAILURE;
        }else{
            msgkey=CashoutResponseVo.CAST_OUT_STATUS_ACCEPT;
        }
       }else{
           msg+=ChinaPayConfig.getValue(responseCode);
           msgkey=CashoutResponseVo.CAST_OUT_STATUS_FAILURE;
       }
    resp.put(msgkey, msg);
    resp.put("status", msgkey);
    resp.put("msg", msg);
    resp.put("sequence", cpSeqId);
    return resp;
}
/**
 * 对外查询接口
 * @param cashoutResponseVo
 * @return
 * @throws Exception
 * @auth llr
 * @date 2015年9月19日
 */
public CashoutResponseVo chinaPayQuery(CashoutResponseVo cashoutResponseVo){
    ChinaPayQueryRequestVo chinaPayQueryRequestVo=new ChinaPayQueryRequestVo();
    try{
        chinaPayQueryRequestVo.setMerDate(cashoutResponseVo.getMerDate());
        chinaPayQueryRequestVo.setMerId(ChinaPayConfig.CHINAPAY_MERID);
        chinaPayQueryRequestVo.setMerSeqId(cashoutResponseVo.getRequestId());
        Map <String,String>resp=this.singleQuery(chinaPayQueryRequestVo);
        cashoutResponseVo.setStatus(resp.get("status"));
        cashoutResponseVo.setErrorMsg(resp.get("msg"));
        cashoutResponseVo.setReceiptNo(resp.get("sequence"));
    }catch(Exception e) {
        loger.error("查询失败", e);
        cashoutResponseVo.setStatus(CashoutResponseVo.CAST_OUT_SYSTEM_STATUS_FAILURE);
        cashoutResponseVo.setErrorMsg("系统异常");
    }
    return cashoutResponseVo;
    
    
}
   /**
    * 查询接口
    * @param chinaPayQueryRequestVo
    * @return
    * @throws Exception
    */
 public Map<String,String> singleQuery(ChinaPayQueryRequestVo chinaPayQueryRequestVo) throws Exception{
     Map <String,String>resp=new HashMap<String,String>();
     String message=chinaPayQueryRequestVo.buildSingMessage();
     String merId=ChinaPayConfig.CHINAPAY_MERID;
     String chkValue="";
     String reqURL=ChinaPayConfig.CHINAPAY_REQUEST_QUERY_URL; 
     chkValue=ChinaPaySign.sign(message, merId);
     chinaPayQueryRequestVo.setChkValue(chkValue);
     NameValuePair []formparams = HttpReqRespUtil.buildRequestParams(chinaPayQueryRequestVo);
     HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
     HttpRequest request = new HttpRequest(HttpResultType.BYTES);
     //设置编码集
     request.setCharset(ChinaPayConfig.CHINAPAY_INPUT_CHARSET);
     request.setUrl(reqURL);
     request.setParameters(formparams);
     HttpResponse response = (HttpResponse) httpProtocolHandler.execute(request,"","");
     if (response == null) {
         return null;
     }
     String strResult = new String(response.getByteResult(),ChinaPayConfig.CHINAPAY_INPUT_CHARSET);
     resp= processQueryResponse(strResult);
     
     return resp;
 }
 
 /**
  * 处理查询返回的结果
  * @param response
  * @throws Exception
  */
   public Map<String,String> processQueryResponse(String response) throws Exception{
       loger.info("input param :"+response);
       Map <String,String>resp=new HashMap<String,String>();
       ChinaPayQueryResponseVo chinaPayQueryResponseVo=null;
       if(!StringUtils.isBlank(response))
         {
            String[]result=response.split("\\|");
            if(result.length==17){
                chinaPayQueryResponseVo =new ChinaPayQueryResponseVo(result[0],
                        result[1],result[2],result[3],result[4],result[5],
                        result[6],result[7],result[8],result[9],result[10],
                        result[11],result[12],result[13],result[14],result[15],
                        result[16]);
            }
            boolean verify=ChinaPaySign.verifySign(chinaPayQueryResponseVo.buildSingMessage(), chinaPayQueryResponseVo.getChkValue(), ChinaPayConfig.CHINAPAY_VERIFY_MERID);
             System.out.println("verify:"+verify);
             if(verify){
                    resp=processReponseCode(chinaPayQueryResponseVo.getCode(),
                            chinaPayQueryResponseVo.getStat(),
                            chinaPayQueryResponseVo.getCpSeqId(),"000");
                  }else{
                      loger.error("verify failed");
                      resp.put("status", CashoutResponseVo.CAST_OUT_STATUS_FAILURE);
                      resp.put("msg", "验签失败");
                  }
         }else{
             loger.error("response is null");
             resp.put("status", CashoutResponseVo.CAST_OUT_STATUS_REQUEST);
             resp.put("msg", "待查询");
             loger.error("response is null");
         }
    return resp;
   }
public static void  main(String[] agrs) throws Exception{
    ChinaPayHandler chinaPayHandler=new ChinaPayHandler();
    ChinaPayRequestVo chinaPayRequestVo=
            new ChinaPayRequestVo("808080211302039","20150910","0808021130303937","6214850210260755",
                    "梁立荣","招商银行","上海","上海","000000000001","提现","","00","20150304","08");
   System.out.println("result:"+chinaPayHandler.singlePay(chinaPayRequestVo));

}
}
