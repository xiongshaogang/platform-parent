package com.dvn.telemedicine.common.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.common.api.dto.CallBackModelDTO;
import com.dvn.telemedicine.core.OcsException;
import com.dvn.telemedicine.core.WriterNotFoundException;
import com.dvn.telemedicine.core.avgbp.AvgBpKeyGenerator;
import com.dvn.telemedicine.core.blood.BloodIndex;
import com.dvn.telemedicine.core.blood.BloodWriterKeyGenerator;
import com.dvn.telemedicine.core.bloodoxygen.BloodOxygenKeyGenerator;
import com.dvn.telemedicine.core.cache.WriterManager;
import com.dvn.telemedicine.core.ecg.EcgWriterKeyGenerator;
import com.dvn.telemedicine.entity.x.XcheckItem;
import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XcheckReportBloodPressure;
import com.dvn.telemedicine.entity.x.XcheckReportItemAverageDay;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;
import com.dvn.telemedicine.entity.x.Xmember;
import com.dvn.telemedicine.entity.x.XxcheckReportApiverage;
import com.dvn.telemedicine.entity.x.XxcheckReportApiverageGroup;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekAvg;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekData;
import com.dvn.telemedicine.entity.xd.XdcheckItem;
import com.dvn.telemedicine.entity.xd.XdcheckReport;
import com.dvn.telemedicine.entity.xy.XycheckReport;
import com.dvn.telemedicine.service.x.XCheckReportJsonResultManager;
import com.dvn.telemedicine.service.x.XcheckItemManager;
import com.dvn.telemedicine.service.x.XcheckReportBloodPressureManager;
import com.dvn.telemedicine.service.x.XcheckReportItemAverageDayManager;
import com.dvn.telemedicine.service.x.XcheckReportManager;
import com.dvn.telemedicine.service.x.XcheckReportRefItemManager;
import com.dvn.telemedicine.service.x.XxcheckReportApiverageGroupManager;
import com.dvn.telemedicine.service.x.XxcheckReportApiverageManager;
import com.dvn.telemedicine.service.x.XxcheckReportBloodWeekAvgManager;
import com.dvn.telemedicine.service.x.XxcheckReportBloodWeekDataManager;
import com.dvn.telemedicine.service.xd.XdcheckItemManager;
import com.dvn.telemedicine.service.xd.XdcheckReportManager;
import com.dvn.telemedicine.service.xd.XdcheckReportWaveshapeManager;
import com.dvn.telemedicine.service.xy.XycheckReportManager;
import com.dvn.telemedicine.util.AppKeys;
import com.dvn.telemedicine.util.RequestData;
import com.dvn.uc.client.plugin.AccessPointCut;

/**<br>
 * &nbsp;&nbsp;标题：小云网API<br>
 * &nbsp;&nbsp;描述：主要用来小云网的业务接口<br>
 * &nbsp;--------------------------------------------------------------------------<br>
 * &nbsp;&nbsp;接口入口：/api/xiaoyun<br>
 * &nbsp;--------------------------------------------------------------------------<br>
 * &nbsp;&nbsp;版权所有 上海天柏宽带有限公司，并保留所有权利。<br>
 * &nbsp;----------------------------------------------------------------------------<br>
 * &nbsp;&nbsp;提示：在未取得DVN商业授权之前，您不能将本软件应用于商业用途，否则DVN将保留追究的权力<br>
 */
@Component
@Path("/xiaoyun")  
public class XiaoyunService {
	private static Logger logger = LoggerFactory.getLogger(XiaoyunService.class);
	
	private ObjectMapper maper = new ObjectMapper();
	
	@Autowired
	private XcheckReportManager xcheckReportManager;
	@Autowired
	private XcheckItemManager xcheckItemManager;
	@Autowired
	private XcheckReportItemAverageDayManager xcheckReportItemAverageDayManager;
	@Autowired
	private XcheckReportRefItemManager xcheckReportRefItemManager;
	@Autowired
	private XxcheckReportApiverageManager xxcheckReportApiverageManager;
	@Autowired
	private XxcheckReportApiverageGroupManager xxcheckReportApiverageGroupManager;
	@Autowired
	private XxcheckReportBloodWeekAvgManager xxcheckReportBloodWeekAvgManager;
	@Autowired
	private XxcheckReportBloodWeekDataManager xxcheckReportBloodWeekDataManager;
	@Autowired
	private XcheckReportBloodPressureManager xcheckReportBloodPressureManager;
	@Autowired
	private XdcheckReportManager xdcheckReportManager;
	@Autowired
	private XdcheckItemManager xdcheckItemManager;
	@Autowired
	private XdcheckReportWaveshapeManager xdcheckReportWaveshapeManager;
	@Autowired
	private XycheckReportManager xycheckReportManager;
	@Autowired
	private WriterManager writerManager;
	@Autowired
	private XCheckReportJsonResultManager xCheckReportJsonResultManager;
	//--------------------------------------------------------------------------
	
	/**
	 * 查询成员报告列表
	 */
	@POST
	@Path("/getMemberReportList")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Page<XcheckReport>> getMemberReportList(@FormParam("memberId") String memberId,@FormParam("mark") Integer mark,
			@FormParam("pager") Integer pager,@FormParam("pagerSize") Integer pagerSize,@FormParam("isTemporary") Integer isTemporary,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			if(isTemporary!=null && !("").equals(isTemporary)){
				if(isTemporary==1){
					return new CallBackModelDTO<Page<XcheckReport>>("访客用户，没有数据",null,"500");
				}
			}
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    String otherMemberId=request.getHeader(AppKeys.OTHER_MEMBERID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("memberId不能为空",null,"500");
		    }
		    if(otherMemberId!=null && !("").equals(otherMemberId)){
		    	memberId=otherMemberId;
		    }
		    if(mark == null || "".equals(mark)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("mark不能为空",null,"500");
		    }
		    if(pager == null || "".equals(pager)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("pager不能为空",null,"500");
		    }
		    if(pagerSize == null || "".equals(pagerSize)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("pagerSize不能为空",null,"500");
		    }
		    Page<XcheckReport> page=xcheckReportManager.getList(memberId, mark, pager, pagerSize);
		    if(page!=null){
		    	List<XcheckReport> list=page.getResult();
		    	List<XcheckReport> rlist=new ArrayList<XcheckReport>();
			    for(XcheckReport re:list){
			    	if(re.getScore()!=null && !("").equals(re.getScore())){
				    	Double a=Double.valueOf(re.getScore());
				    	XcheckReport rep=new XcheckReport();
				    	rep.setId(re.getId());
				    	rep.setMemberId(re.getMemberId());
				    	rep.setSex(re.getSex());
				    	rep.setPulse(re.getPulse());
				    	rep.setBloodPressure(re.getBloodPressure());
				    	rep.setWaveShape("");
				    	rep.setScore(re.getScore());
				    	rep.setMark(re.getMark());
				    	rep.setCreateDate(re.getCreateDate());
				    	rep.setItemFeature(re.getItemFeature());
				    	rep.setMemberName(re.getMemberName());
				    	if(a<60){
				    		rep.setLevel(AppKeys.SCORE_INFERIOR);
				    	}else if(a>=60 && a<80){
				    		rep.setLevel(AppKeys.SCORE_MEDIUM);
				    	}else if(a>=80 && a<101){
				    		rep.setLevel(AppKeys.SCORE_GOOD);
				    	}
				    	rlist.add(rep);
			    	}
			    }
			    page.setResult(rlist);
		    }
    		logger.info("------------------------调用成功 end---------------------------");
			return new CallBackModelDTO<Page<XcheckReport>>("操作成功",page,"200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Page<XcheckReport>>("程序异常",null,"500");
		}
	}
	

	/**
	 * 根据报告id获取25项
	 */
	@POST
	@Path("/getItemByReportId")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getItemByReportId(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("reportId不能为空",null,"500");
		    }
		    XcheckReport rep=xcheckReportManager.getById(reportId);
		    if(rep!=null){
			    List<XcheckReportRefItem> list=xcheckReportRefItemManager.getListByReportId(reportId);
			    if(list!=null && list.size()>0){
			    	Map<String, Object> map=new HashMap<String, Object>();
//				    map.put("report", new  XcheckReport(rep.getId(), rep.getMemberId(), rep.getSex(), list.get(0).getTestValue(),
//				    		rep.getBloodPressure(),rep.getScore(), rep.getMark(), rep.getCreateDate(),rep.getMemberName()));
			    	rep.setItemFeature(null);//该值不需要传回前台
			    	map.put("report", rep);
				    map.put("itemList", list);
			    	logger.info("------------------------查询成功 end---------------------------");
					return new CallBackModelDTO<Map<String, Object>>("查询成功",map,"200");
			    }else{
			    	logger.info("------------------------无此报告的24项记录 end---------------------------");
					return new CallBackModelDTO<Map<String, Object>>("无此报告的24项记录",null,"500");
			    }
		    }else{
		    	logger.info("------------------------无此报告记录 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("无此报告记录",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	

	
	/**
	 * save检测结果（血压，脉搏，波形，24项） 新接口
	 */
	@POST
	@Path("/newSaveReportResult")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> newSaveReportResult(@FormParam("memberId") String memberId,@FormParam("resultjson") String resultjson,@Context HttpServletRequest request) {
			return new CallBackModelDTO<Map<String, Object>>("版本过时,请更新版本",null,"500");
	}
	
	/**
	 * 新获取单个报告记录   新接口
	 */
	@POST
	@Path("/getNewReportInfo")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getNewReportInfo(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
			return new CallBackModelDTO<Map<String, Object>>("版本过时,请更新版本",null,"500");
	}
	
	/**
	 * 获取成员报告记录的总个数
	 * @param memberId
	 * @param request
	 * @return
	 */
	@POST
	@Path("/getMemberReportCount")
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<String> getMemberReportCount(@FormParam("memberId") String memberId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		if(memberId == null || "".equals(memberId)){
	    	 return new CallBackModelDTO<String>("memberId不能为空",null,"500");
	    }
		Integer count=xcheckReportManager.getMemberReportCount(memberId);
		logger.info("------------------------程序正常 end---------------------------");
		return new CallBackModelDTO<String>("程序正常",count+"","200");
	}
	
	/**
	 * save检测结果（血压，脉搏，波形，24项） 新接口 1.7
	 */
	@POST
	@Path("/newSaveReportResult2")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> newSaveReportResult2(@FormParam("memberId") String memberId,@FormParam("resultjson") String resultjson,@Context HttpServletRequest request) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("------------------------检测结果 开始调用 start"+sim.format(new Date())+"---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
		    }
		    if(resultjson == null || "".equals(resultjson)){
		    	 return new CallBackModelDTO<Map<String, Object>>("resultjson不能为空",null,"500");
		    }
		    
		    Xmember xm=RequestData.getPayUserId(memberId);
		    Map<String,Object> result=xcheckReportManager.saveReport(xm, resultjson, "1.7");
		    return new CallBackModelDTO<Map<String, Object>>("操作成功",result,"200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("系统繁忙",null,"500");
		}
	}
	
	/**
	 * 新获取单个报告记录   新接口 1.7
	 */
	@POST
	@Path("/getNewReportInfo2")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getNewReportInfo2(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("reportId不能为空",null,"500");
		    }
		    String jsonResult=xCheckReportJsonResultManager.getResultById(reportId);
		    if(jsonResult!=null){
		    	Map<String, Object> map=maper.readValue(jsonResult,Map.class);
	    		logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	logger.info("------------------------无此报告记录 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("无此报告记录",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	
	/**
	 * save检测结果（血压，脉搏，波形，24项） 新接口 2.0
	 */
	@POST
	@Path("/newSaveReportResult3")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> newSaveReportResult3(@FormParam("memberId") String memberId,@FormParam("resultjson") String resultjson,@Context HttpServletRequest request) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("------------------------检测结果 开始调用 start"+sim.format(new Date())+"---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
		    }
		    if(resultjson == null || "".equals(resultjson)){
		    	 return new CallBackModelDTO<Map<String, Object>>("resultjson不能为空",null,"500");
		    }
		    Xmember xm=RequestData.getPayUserId(memberId);
		    Map<String,Object> result=xcheckReportManager.saveReport(xm, resultjson, "2.0");
			return new CallBackModelDTO<Map<String, Object>>("操作成功",result,"200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("系统繁忙",null,"500");
		}
	}
	/**
	 * 新获取单个报告记录   2.0接口
	 */
	@POST
	@Path("/getNewReportInfo3")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getNewReportInfo3(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("reportId不能为空",null,"500");
		    }
		    String jsonResult=xCheckReportJsonResultManager.getResultById(reportId);
		    if(jsonResult!=null){
		    	Map<String, Object> map=maper.readValue(jsonResult,Map.class);
	    		logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	logger.info("------------------------无此报告记录 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("无此报告记录",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
//	
//	private Map<String, Object> baozhuang(Xmember xm,XcheckReport rep,String version){
//		Map<String, Object> rap=new HashMap<String, Object>();
//	    rap.put("reportId", rep.getId()+"");
//	    rap.put("bloodPressure", rep.getBloodPressure());
//	    //rap.put("pulse", rep.getPulse());
//	    rap.put("score", rep.getScore());
//	    rap.put("memberId", xm.getId());
//	    rap.put("createDate", rep.getCreateDate());
//	    Double a=Double.valueOf(rep.getScore());
//    	if(a<=59){
//    		rap.put("level",AppKeys.SCORE_INFERIOR);
//    	}else if(a>=60 && a<=79){
//    		rap.put("level",AppKeys.SCORE_MEDIUM);
//    	}else if(a>=80 && a<=100){
//    		rap.put("level",AppKeys.SCORE_GOOD);
//    	}
//	    
//    	rap.put("content", "");
//    	
//	    //3大模块的数据
//	    List<XxcheckReportRefWriter> mlist=xxcheckReportRefWriterManager.getListByReportId(rep.getId());
//	    List<XxcheckReportModelVO> vlist=new ArrayList<XxcheckReportModelVO>();
//	    Map<String, Map<String, String>> xiang=new LinkedHashMap<String, Map<String, String>>();
//	    Double p=0.0;
//	    if(mlist!=null && mlist.size()>0){
//	    	//首先去PR的测量值 算心跳节律的等级
//		    XcheckItem prit=xcheckItemManager.getByAbbreviate("PR");
//			XcheckReportRefItem pr=xcheckReportRefItemManager.getByItemId(rep.getId(), prit.getId());
//			p=Double.valueOf(pr.getTestValue());
//			rap.put("pulse", new BigDecimal(p).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
//		    for(XxcheckReportRefWriter wr:mlist){
//		    	XxcheckReportModelVO vo=new XxcheckReportModelVO();
//		    	XxcheckReportModel model=xxcheckReportModelManager.getById(wr.getModelId());
//		    	vo.setModelId(wr.getModelId()+"");
//		    	vo.setLevel(wr.getLevel()+"");
//		    	vo.setModelName(model.getName());
//		    	//取7个大类
//		    	String[] cas=wr.getCateLevelIds().split(",");
////		    	String maibo="";
////		    	String xueya="";
//		    	for(String cate:cas){
//		    		String[] cc=cate.split("@");
//		    		XxcheckReportCategory go=xxcheckReportCategoryManager.getById(Long.valueOf(cc[0]));
//		    		if(go!=null){
//		    			//int it=new BigDecimal(cc[1]).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入取整
//		    			int it=Integer.parseInt(cc[1]);
//		    			Map<String, String> wf=new LinkedHashMap<String, String>();
//		    			
//		    			//计算此成员属于哪个人群
//		    			Double height=new BigDecimal(Double.valueOf(xm.getHeight())/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//		    			Double bmiValue=Double.valueOf(xm.getWeight())/(height*height);
//		    			int bmi=new BigDecimal(bmiValue).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//		    			XxcheckReportCrowd crowd=xxcheckReportCrowdManager.getByInfo(xm.getAge(), xm.getSex(), bmi);
//		    			wf.put("level", it+"");
//		    			if(go.getName().equals("动脉血压")){
//		    				XcheckItem mapit=xcheckItemManager.getByAbbreviate("MAP");
//		    				XcheckReportRefItem map=xcheckReportRefItemManager.getByItemId(rep.getId(), mapit.getId());
//		    				Double m=Double.valueOf(map.getTestValue());
//		    				int er=getErrerCount(m, mapit);
//		    				if(er!=0){
//		    					XxcheckReportCrowdEvaluationWriter ww=xxcheckReportCrowdEvaluationWriterManager.getByCateIdAndlevel(go.getId(), er, crowd.getId());
//				    			if(ww!=null){
//				    				wf.put("write", ww.getContent());
//				    			}else{
//				    				wf.put("write", AppKeys.WRITER_GOOD);
//				    			}
//		    				}else{
//		    					wf.put("write", AppKeys.WRITER_GOOD);
//		    				}
//		    			}else{
//		    				XxcheckReportCrowdEvaluationWriter ww=xxcheckReportCrowdEvaluationWriterManager.getByCateIdAndlevel(go.getId(), it, crowd.getId());
//			    			if(ww!=null){
//			    				wf.put("write", ww.getContent());
//			    			}else{
//			    				wf.put("write", AppKeys.WRITER_GOOD);
//			    			}
//		    			}
//		    			XxcheckReportCategoryWriter cw=xxcheckReportCategoryWriterManager.getXxcheckReportCategoryWriter(go.getId(), it);
//		    			wf.put("copywriter", "");
////		    			wf.put("copywriter", cw.getCopywriter());
////		    			if(go.getName().equals("脉搏节律")){
////		    				if(it==0){
////		    					wf.put("write", cw.getContent());
////		    				}else{
////			    				maibo=getContent(p, go.getName(), cw.getLevel(), rep.getId());
////			    				if(maibo!=null && !("").equals(maibo)){
////			    					wf.put("write", maibo.substring(0, maibo.length()-1));
////			    				}else{
////			    					wf.put("write", "");
////			    				}
////		    				}
////		    			}else if(go.getName().equals("动脉血压")){
////		    				if(it==0){
////		    					wf.put("write", cw.getContent());
////		    				}else{
////			    				xueya=getContent(p, go.getName(), cw.getLevel(), rep.getId());
////			    				if(xueya!=null && !("").equals(xueya)){
////			    					wf.put("write", xueya.substring(0, xueya.length()-1));
////			    					if(xueya.indexOf("低")>-1){
////			    						wf.put("copywriter", "");
////			    					}
////			    				}else{
////			    					wf.put("write", "");
////			    				}
////		    				}
////		    			}else{
////		    				wf.put("write", cw.getContent());
////		    			}
//		    			wf.put("define", cw.getDefine());
//		    			if(version.equals("1.7")){
//		    				if(go.getName().equals("心肌功能")){
//			    				xiang.put("冠脉供耗", wf);
//			    			}else if(go.getName().equals("动脉弹性")){
//			    				xiang.put("血管弹性", wf);
//			    			}else{
//			    				xiang.put(go.getName(), wf);
//			    			}
//		    			}else{
//		    				xiang.put(go.getName(), wf);
//		    			}
//		    		}
//		    	}
//		    	//取三个模块
//		    	if(wr.getWriterIds()!=null && !("").equals(wr.getWriterIds()) && !("0").equals(wr.getWriterIds())){
//		    		String[] ids=wr.getWriterIds().split(",");
//		    		String content="";
//		    		for(String writerId:ids){
//				    	XxcheckReportCategoryWriter writer=xxcheckReportCategoryWriterManager.getById(Long.valueOf(writerId));
//				    	if(writer!=null){
//				    		XxcheckReportCategory cat=xxcheckReportCategoryManager.getById(writer.getCateId());
////				    		if(cat.getName().equals("脉搏节律")){
////				    			content+=maibo;
////				    		}else if(cat.getName().equals("动脉血压")){
////				    			content+=xueya;
////				    		}else{
////				    			content+=writer.getContent()+"，";
////				    		}
//				    		if(cat.getName().equals("脉搏节律")){
//				    			content+=getContent(p, cat.getName(), writer.getLevel(), rep.getId());
//				    		}else if(cat.getName().equals("动脉血压")){
//				    			content+=getContent(p, cat.getName(), writer.getLevel(), rep.getId());
//				    		}else{
//				    			content+=writer.getContent()+"，";
//				    		}
//				    	}
//		    		}
//		    		if(content!=null && !("").equals(content)){
//		    			content=content.substring(0, content.length()-1);
//		    		}
//		    		vo.setContent(content);
//		    	}else{
//		    		vo.setContent(AppKeys.WRITER_GOOD);
//		    	}
//		    	vlist.add(vo);
//		    }
//	    }
//	    //12项（目前）的数据
//	    //文案
//	    Map<String,String> wap=evaluateSuggestWriterManager.searchWriter(xm,rep.getId(),p);
//	    Map<String, Object> map=new LinkedHashMap<String, Object>();
//	    map.put("report", rap);
//	    map.put("model", vlist);
//	    map.put("feature", xiang);
//	    map.put("copyWriter", wap);
//		return map;
//	}
//	private String getContent(Double p,String catName,int level,Long reportId){
//		String content="";
//		if(catName.equals("脉搏节律")){
//			if(p>=40 && p<60){
//				content="心动过缓，";
//			}else if(p>100 && p<=150){
//				content="心动过速，";
//			}
//			XcheckItem prvit=xcheckItemManager.getByAbbreviate("PRV");
//			XcheckReportRefItem prv=xcheckReportRefItemManager.getByItemId(reportId, prvit.getId());
//			Double pv=Double.valueOf(prv.getTestValue());
//			if(pv>0.1){
//				content="脉搏波动欠规则，可能心律不齐，";
//			}
//		}else if(catName.equals("动脉血压")){
//			XcheckItem mapit=xcheckItemManager.getByAbbreviate("MAP");
//			XcheckReportRefItem map=xcheckReportRefItemManager.getByItemId(reportId, mapit.getId());
//			Double m=Double.valueOf(map.getTestValue());
//			int er=getErrerCount(m, mapit);
//			if(level==1){
//				if(er==1){
//					content="动脉血压偏高，";
//				}else if(er==-1){
//					content="动脉血压偏低，";
//				}
//			}if(level==2){
//				if(er==2){
//					content="动脉血压较高，易发高血压或可能家族高血压史，";
//				}else if(er==-2){
//					content="动脉血压过低，易发贫血，";
//				}
//			}
//		}
//		return content;
//	}
//	private int getErrerCount(Double testValue,XcheckItem item){
//		int count=0;
//		Double gmax=new BigDecimal(Double.valueOf(item.getConsultAvgValue())+Double.valueOf(item.getConsultDeviation())).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();//正常范围内 最大
//		Double gmin=new BigDecimal(Double.valueOf(item.getConsultAvgValue())-Double.valueOf(item.getConsultDeviation())).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();//正常范围内 最小
//		Double ymax=new BigDecimal(gmax+Double.valueOf(item.getConsultDeviation())).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();//亚健康范围内 最大
//		Double ymin=new BigDecimal(gmin-Double.valueOf(item.getConsultDeviation())).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();//亚健康范围内 最小
//		if(testValue>=gmin && testValue<=gmax){//正常
//			count=0;
//		}else if(testValue>gmax && testValue<=ymax){
//			count=1;
//		}else if(testValue<gmin && testValue>=ymin){
//			count=-1;
//		}else if(testValue>ymax){
//			count=2;
//		}else if(testValue<ymin){
//			count=-2;
//		}
//		return count;
//	}
	
	/**
	 * 查询成员50天检测项健康档案   新接口
	 */
	@POST
	@Path("/newGetMemberItemRecord")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String,Object>> newGetMemberItemRecord(@FormParam("memberId") String memberId,@FormParam("itemName") String itemName,@FormParam("testMark")Long testMark,
			@FormParam("pager")Integer pager,@FormParam("pagerSize")Integer pagerSize,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String,Object>>("memberId不能为空",null,"500");
		    }
		    if(itemName == null || "".equals(itemName)){
		    	 return new CallBackModelDTO<Map<String,Object>>("检测项名称不能为空",null,"500");
		    }
		    if(pager == null || "".equals(pager) || pager<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("分页数不能为空",null,"500");
		    }
		    if(pager<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("页数不能小于1",null,"500");
		    }
		    if(pagerSize == null || "".equals(pagerSize)){
		    	 return new CallBackModelDTO<Map<String,Object>>("每页数量不能为空",null,"500");
		    }
		    if(pagerSize<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("每页数不能小于1",null,"500");
		    }
		    Map<String,Object> mapScore=new LinkedHashMap<String, Object>();
		    List listScore =new ArrayList();
		    List listBloodPressure =new ArrayList();
		    List listHeartRecord=new ArrayList();
		    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		    if(testMark !=0){
		    	int start=(pager-1)*pagerSize+1;
		    	if(pager==1){
	    			String itemAvg=xcheckReportManager.getItemAvg(memberId, itemName);
	    			listScore.add(itemAvg);
	    		}else if(pager>1){
	    			start=start-1;
	    		}
		    	int end=pager*pagerSize-1;
		    	XcheckReport xm=xcheckReportManager.getXcheckReportOrderByDate(memberId);
		    	int index=0;
		    	for(int i=start;i<=end;i++){
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -i);    //得到前一天
					String checkDate = ft.format(calendar.getTime());
					
					if(itemName.equals(AppKeys.PRESSURE_VALUE)){//血压
						XcheckReportItemAverageDay lowAvg=xcheckReportItemAverageDayManager.getByCondition(memberId, AppKeys.LOW_PRESSURE_VALUE, checkDate);
						XcheckReportItemAverageDay highAvg=xcheckReportItemAverageDayManager.getByCondition(memberId, AppKeys.HIGH_PRESSURE_VALUE, checkDate);
						if(lowAvg!=null && highAvg!=null){
							listScore.add(lowAvg.getScore()+","+highAvg.getScore());
						}else{
							listScore.add(null);
						}
					}else{
						XcheckReportItemAverageDay averageDay=xcheckReportItemAverageDayManager.getByCondition(memberId, itemName, checkDate);
						if(averageDay !=null){
							listScore.add(averageDay.getScore());
						}else{
							listScore.add(null);
						}
					}
					mapScore.put("score", listScore);
					if(xm!=null){
						if(ft.format(xm.getCreateDate()).equals(checkDate)){//到了最早检测的一天
							index=1;
						}
					}else{
						index=1;
					}
	    		}
		    	mapScore.put("sum", index);
		    }else{
				List<XcheckReportItemAverageDay> averageDay=new ArrayList<XcheckReportItemAverageDay>();
				averageDay=xcheckReportItemAverageDayManager.getConditionList(memberId, itemName, null);
				for(int i=0;i<50;i++){
					if(i<averageDay.size()){
						listScore.add(averageDay.get(i).getScore());
					}else{
						listScore.add(null);
					}
				}		
				mapScore.put("score", listScore);
				mapScore.put("sum", 0);
		    }
	    	logger.info("------------------------调用成功 end---------------------------");
			return new CallBackModelDTO<Map<String,Object>>("操作成功",mapScore,"200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String,Object>>("程序异常",null,"500");
		}
	}
	
	
	
	/**
	 * 查询成员报告列表通过时间
	 */
	@POST
	@Path("/getMemberReportListByDate")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Page<XcheckReport>> getMemberReportListByDate(@FormParam("memberId") String memberId,@FormParam("mark") Integer mark,@FormParam("year") String year,@FormParam("month") String month,
			@FormParam("pager") Integer pager,@FormParam("pagerSize") Integer pagerSize,@FormParam("isTemporary") Integer isTemporary,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			if(isTemporary!=null && !("").equals(isTemporary)){
				if(isTemporary==1){
					return new CallBackModelDTO<Page<XcheckReport>>("访客用户，没有数据",null,"500");
				}
			}
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("memberId不能为空",null,"500");
		    }
		    if(mark == null || "".equals(mark)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("mark不能为空",null,"500");
		    }
		    if(pager == null || "".equals(pager)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("pager不能为空",null,"500");
		    }
		    if(pagerSize == null || "".equals(pagerSize)){
		    	 return new CallBackModelDTO<Page<XcheckReport>>("pagerSize不能为空",null,"500");
		    }
		    Page<XcheckReport> page=xcheckReportManager.getListByDate(memberId, mark,year,month,pager, pagerSize);
		    if(page!=null){
		    	List<XcheckReport> list=page.getResult();
		    	List<XcheckReport> rlist=new ArrayList<XcheckReport>();
			    for(XcheckReport re:list){
			    	if(re.getScore()!=null && !("").equals(re.getScore())){
				    	Double a=Double.valueOf(re.getScore());
				    	XcheckReport rep=new XcheckReport();
				    	rep.setId(re.getId());
				    	rep.setMemberId(re.getMemberId());
				    	rep.setSex(re.getSex());
//				    	rep.setPulse(re.getPulse());
				     	String pluse= re.getPulse();
				     	int newPluse=new BigDecimal(Double.valueOf(pluse)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入
//						XcheckReportRefItem pr=xcheckReportRefItemManager.getByItemId(re.getId(), prit.getId());
				    	rep.setPulse(newPluse+"");
				    	rep.setBloodPressure(re.getBloodPressure());
				    	rep.setWaveShape("");
				    	
				    	int newScore=new BigDecimal(Double.valueOf(re.getScore())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入
				    	rep.setScore(newScore+"");
				    	rep.setMark(re.getMark());
				    	rep.setCreateDate(re.getCreateDate());
				    	rep.setItemFeature(re.getItemFeature());
				    	rep.setMemberName(re.getMemberName());
				    	if(a<60){
				    		rep.setLevel(AppKeys.SCORE_INFERIOR);
				    	}else if(a>=60 && a<80){
				    		rep.setLevel(AppKeys.SCORE_MEDIUM);
				    	}else if(a>=80 && a<101){
				    		rep.setLevel(AppKeys.SCORE_GOOD);
				    	}
				    	rlist.add(rep);
			    	}
			    }
			    page.setResult(rlist);
		    }
    		logger.info("------------------------调用成功 end---------------------------");
			return new CallBackModelDTO<Page<XcheckReport>>("操作成功",page,"200");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Page<XcheckReport>>("程序异常",null,"500");
		}
	}
	
	/**
	 * 查询会员固定数单位检测项健康档案 （按次）  新接口
	 * 检测项对应定义itemMark
	 * 1 脉率，2 左室顺应性，3 心舒末容量，4 心肌收缩力，5 心缩时间间期 ，6 射血分数 ，7 每博血量
	 * 8 心输出量，9 心脏指数，10 心缩末血量，11 心肌耗氧量，12 左室射血压，13 心内膜供血率，14 脉搏速度
	 * 15 外周动脉顺应性，16 平均脉压，17 平均动脉压，18 左室射血阻力，19 总外周阻力，20 血液粘滞阻力
	 * 21 动脉血容量， 22 血容量指数，23 平均循环时间，24 综合反射系数，25 脉率指数
	 * 26 舒张压， 收缩压，27心脉指数 （代码质量差,建议之后修改）
	 */
	@POST
	@Path("/newGetMemberItemRecordByTime")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String,Object>> newGetMemberItemRecordByTime(@FormParam("memberId") String memberId,@FormParam("itemMark") Long itemMark,@FormParam("testMark") Integer testMark,@FormParam("pager")Integer pager,@FormParam("pagerSize")Integer pagerSize,@Context HttpServletRequest request) {
			logger.info("------------------------开始调用 start---------------------------");
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String,Object>>("memberId不能为空",null,"500");
		    }
		    if(itemMark == null){
		    	 return new CallBackModelDTO<Map<String,Object>>("检测项标识不能为空",null,"500");
		    }
		    if(pager == null || "".equals(pager) || pager<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("分页数不能为空",null,"500");
		    }
		    if(pager<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("页数不能小于1",null,"500");
		    }
		    if(pagerSize == null || "".equals(pagerSize)){
		    	 return new CallBackModelDTO<Map<String,Object>>("每页数量不能为空",null,"500");
		    }
		    if(pagerSize<=0){
		    	 return new CallBackModelDTO<Map<String,Object>>("每页数不能小于1",null,"500");
		    }
		    Page page = null;
		    Map<String,Object> result = null; 
		    if(itemMark==27){//心脉指数
		    	page = xcheckReportManager.getListByPageByTime(memberId,testMark,"score", pager, pagerSize);
		    	List<Object> list=page.getResult();
		    	List<Object> cc=new ArrayList<Object>();
		    	for(int i=0;i<list.size();i++){
		    		Object[] ob=(Object[]) list.get(i);
//		    		//取计算出的脉率
		    		String score=(String)ob[1];
		    		ob[1] =new BigDecimal(Double.valueOf(score)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
//					XcheckReportRefItem pr=xcheckReportRefItemManager.getByItemId(Long.valueOf(ob[0]+""), 1L);
//					int ss=new BigDecimal(pr.getTestValue()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入
		    		ob[3]=new BigDecimal(Double.valueOf((String)ob[3])).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
					Object[] con=new Object[ob.length-1];
					for(int j=0;j<ob.length-1;j++){
						con[j]=ob[j+1];
					}
					cc.add(con);
		    	}
		    	page.setResult(cc);
		    }else if(itemMark==26){//血压
		    	page = xcheckReportManager.getListByPageByTime(memberId,testMark,"bloodPressure", pager, pagerSize);
		    }else{//其他项
		    	page = xcheckReportManager.newGetMemberItemRecordByTime(memberId,itemMark,testMark,pager,pagerSize);
		    }
		    
		    result = new HashMap<String,Object>();
	    	result.put("score",page.getResult());
	    	result.put("sum", page.getPageNo() < page.getTotalPages()?0:1);
	    	
	    	return new CallBackModelDTO<Map<String,Object>>("血压数据",result,"200");

	}
	
	
	/**
	 * 平均脉压（血压，脉搏)
	 */
	@POST
	@Path("/saveReportApiverage")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> saveReportApiverage(@FormParam("memberId") String memberId,@FormParam("resultjson") String resultjson,@Context HttpServletRequest request) {
		String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
	    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
	    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
	    if(memberId == null || "".equals(memberId)){
	    	 return new CallBackModelDTO<Map<String, Object>>("成员ID不能为空",null,"500");
	    }
	    if(resultjson == null || "".equals(resultjson)){
	    	 return new CallBackModelDTO<Map<String, Object>>("数据参数不能为空",null,"500");
	    }
	    try {
			List<LinkedHashMap<String, Object>> readValue = maper.readValue(resultjson, List.class);
			if(readValue!=null && readValue.size()>0){
				LinkedHashMap<String, Object> item=readValue.get(readValue.size()-1);
				String date=item.get("createDate").toString();
				String[] s=date.split("\\ ");
				XxcheckReportApiverageGroup gr=xxcheckReportApiverageGroupManager.getByMemberIdAndDate(memberId, s[0]);
				if(gr!=null){
					return new CallBackModelDTO<Map<String, Object>>("该成员该日期已上传数据",null,"500");
				}
				Long groupId=xxcheckReportApiverageManager.saveReportApiverage(memberId, resultjson);
			    if(groupId!=null && groupId>0){
			    	XxcheckReportApiverageGroup group=xxcheckReportApiverageGroupManager.getById(groupId);
			    	if(group!=null){
			    		Map<String, Object> map=new HashMap<String, Object>();
			    		List<XxcheckReportApiverage> list=xxcheckReportApiverageManager.getListByGroupId(groupId);
			    		map.put("group", group);
			    		map.put("content",getAvgBloodPressureWirter(group.getContent()));
			    		map.put("groupList", list);
			    		return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200"); 
			    	}else{
			    		return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500"); 
			    	}
			    }else{
			    	return new CallBackModelDTO<Map<String, Object>>("系统繁忙",null,"500");
			    }
			}else{
				return new CallBackModelDTO<Map<String, Object>>("没有上传数据",null,"500");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500"); 
		}
	}
	
	/**
	 * 查询检测评均脉压的日期
	 */
	@POST
	@Path("/getReportApiverageDate")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<List<String>> getReportApiverageDate(@FormParam("memberId") String memberId,@FormParam("date") String date, @Context HttpServletRequest request) {
		String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
	    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
	    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
	    if(memberId == null || "".equals(memberId)){
	    	 return new CallBackModelDTO<List<String>>("成员ID不能为空",null,"500");
	    }
	    if(date == null || "".equals(date)){
	    	 return new CallBackModelDTO<List<String>>("日期不能为空",null,"500");
	    }
	    List<String> list=xxcheckReportApiverageGroupManager.getListDate(memberId, date);
	    List<String> dlist=new ArrayList<String>();
	    for(Object ss:list){
	    	String[] s=ss.toString().split("\\ ");
	    	dlist.add(s[0]);
	    }
		return new CallBackModelDTO<List<String>>("成功调用！",dlist,"200");
	}
	/**
	 * 根据日期获取平均脉压的详情
	 */
	@POST
	@Path("/getReportApiverage")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getReportApiverage(@FormParam("memberId") String memberId,@FormParam("date") String date, @Context HttpServletRequest request) {
		String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
	    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
	    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
	    if(memberId == null || "".equals(memberId)){
	    	 return new CallBackModelDTO<Map<String, Object>>("成员ID不能为空",null,"500");
	    }
	    if(date == null || "".equals(date)){
	    	 return new CallBackModelDTO<Map<String, Object>>("日期不能为空",null,"500");
	    }
	    XxcheckReportApiverageGroup group=xxcheckReportApiverageGroupManager.getByMemberIdAndDate(memberId, date);
    	try{
    		if(group!=null){
        		Map<String, Object> map=new HashMap<String, Object>();
        		List<XxcheckReportApiverage> list=xxcheckReportApiverageManager.getListByGroupId(group.getId());
        		map.put("group", group);
        		map.put("content",getAvgBloodPressureWirter(group.getContent()));
        		map.put("groupList", list);
        		return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200"); 
        	}else{
        		return new CallBackModelDTO<Map<String, Object>>("无此日期记录",null,"500"); 
        	}
    	}catch (Exception e) {
    		logger.error(e.getMessage(),e);
    		return new CallBackModelDTO<Map<String,Object>>("系统繁忙", null,"500");
		}
	    
	
	}
	
	/**
	 * 根据检测项和等级查询文案
	 */
	@POST
	@Path("/getWriterByItem")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<String> getWriterByItem(@FormParam("itemName") String itemName,@FormParam("level") Integer level,@Context HttpServletRequest request) {
		String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
	    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
	    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
	    if(itemName == null || "".equals(itemName)){
	    	 return new CallBackModelDTO<String>("检测项不能为空",null,"500");
	    }
	    if(level == null || "".equals(level)){
	    	 return new CallBackModelDTO<String>("评分不能为空",null,"500");
	    }
	    if(level==0){
	    	 return new CallBackModelDTO<String>("评分正常没有文案","未见明显异常","200");
	    }
	    XcheckItem item=xcheckItemManager.getByAbbreviate(itemName);
	    try{
	    	if(item!=null){
		    	String  content=getItemWriter(item.getId().intValue(), level);//获取检测项文案
		    	if(content!=null){
		    		return new CallBackModelDTO<String>("成功调用！",content,"200");
		    	}else{
		    		return new CallBackModelDTO<String>("该检测项没有文案",null,"500");
		    	}
		    }else{
		    	return new CallBackModelDTO<String>("无此检测项纪录",null,"500");
		    }
	    }catch (Exception e) {
	    	logger.error(e.getMessage(),e);
	    	return new CallBackModelDTO<String>("系统繁忙",null,"500");
		}
	    
	}
	
	/**
	 * 
	 * 通过writerManager获取文案
	 * @param itemId
	 * @param level
	 * @return
	 * @throws OcsException
	 */
	private String getItemWriter(Integer itemId,Integer level) throws OcsException{
		String key=BloodWriterKeyGenerator.getItemKey(BloodIndex.getBloodIndexById(itemId), level);
		String content= (String)writerManager.get(key);
		return content;
	}
	
	/**
	 * 
	 * 用于获取和组装平均脉压的文案
	 * 
	 * @param content
	 * @return
	 * @throws OcsException
	 * @throws WriterNotFoundException
	 */
	public String getAvgBloodPressureWirter(String content) throws OcsException, WriterNotFoundException{
		String[] marks=content.split(",");
		StringBuffer buf=new StringBuffer(128);
		for (int i = 0; i < marks.length; i++) {
			String key=AvgBpKeyGenerator.getAvgBpKey(marks[i]);
			String writer= writerManager.getOneWriter(key);
			buf.append(writer);
			if(i!=marks.length-1){
				buf.append(",");
			}
		}
		return buf.toString();
	}
	
	
	/**
	 * 获取自然周的脉压详情 date(2014-05&2),avgName(day,night,morning)
	 */
	@POST
	@Path("/getWeekBloodAvg")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getWeekBloodAvg(@FormParam("memberId") String memberId,@FormParam("date") String date,
			@FormParam("avgName") String avgName,@Context HttpServletRequest request) {
		String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
	    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
	    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
	    if(memberId == null || "".equals(memberId)){
	    	 return new CallBackModelDTO<Map<String, Object>>("成员Id不能为空",null,"500");
	    }
	    if(date == null || "".equals(date)){
	    	 return new CallBackModelDTO<Map<String, Object>>("时间不能为空",null,"500");
	    }
	    if(avgName == null || "".equals(avgName)){
	    	 return new CallBackModelDTO<Map<String, Object>>("时段不能为空",null,"500");
	    }
	    try {
	    	String str=getDate(date);
	    	String[] ss=str.split(",");
	    	String sdate=ss[0];
	    	String edate=ss[1];
	    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date tt=sim.parse(edate);
	    	Long st=tt.getTime();
	    	Long et=System.currentTimeMillis();
	    	if(et<st){
	   	    	 return new CallBackModelDTO<Map<String, Object>>("该周数据暂时不能统计",null,"500");
	    	}
	    	
			XxcheckReportBloodWeekAvg avg=xxcheckReportBloodWeekAvgManager.getByMemberId(memberId, date, avgName);
			Map<String, Object> map=new HashMap<String, Object>();
			List<XcheckReport> rlist=xcheckReportManager.getListGroupByDate(memberId, sdate, edate);
			if(avg!=null){
				map.put("avg", avg);
				List<XxcheckReportBloodWeekData> list=xxcheckReportBloodWeekDataManager.getListByWeek(avg.getId());
				map.put("avgList", list);
				List<String> blist=new ArrayList<String>();
				if(avgName.equals(AppKeys.TIME_DAY)){
					for(XcheckReport rep:rlist){
						blist.add(rep.getBloodPressure());
					}
	           	}else if(avgName.equals(AppKeys.TIME_NIGHT)){
	           		for(XcheckReport rep:rlist){
	           			int h=rep.getCreateDate().getHours();
	            	    if(h>=18 && h<21){//夜间
	            	    	blist.add(rep.getBloodPressure());
	            	    }
					}
	           	}else if(avgName.equals(AppKeys.TIME_MORNING)){
	           		for(XcheckReport rep:rlist){
	           			int h=rep.getCreateDate().getHours();
	            	    if(h>=6 && h<9){//日间
	            	    	blist.add(rep.getBloodPressure());
	            	    }
					}
	           	}
				String content=getCopyWriter(blist.size(), avgName, avg);
				map.put("copywriter", content);
				map.put("booldList", blist);
				String[] sd=sdate.split("\\ ");
				String[] ed=edate.split("\\ ");
				map.put("dates", sd[0]+","+ed[0]);
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String[] sd=sdate.split("\\ ");
				String[] ed=edate.split("\\ ");
				Calendar cal =Calendar.getInstance();
				List<String> slist=new ArrayList<String>();
				for(int i=0;i<7;i++){
					cal.setTime(df.parse(ed[0]));
					cal.add(Calendar.DAY_OF_WEEK, -i);
					String time=df.format(cal.getTime());
					slist.add(time);
				}
				Long avgId=xxcheckReportApiverageManager.getReportApiverageDate(memberId, slist,date,avgName);
				if(avgId!=null){
					XxcheckReportBloodWeekAvg week=xxcheckReportBloodWeekAvgManager.getById(avgId);
					if(week!=null){
						map.put("avg", week);
				    	List<XxcheckReportBloodWeekData> list=xxcheckReportBloodWeekDataManager.getListByWeek(week.getId());
				    	map.put("avgList", list);
				    	List<String> blist=new ArrayList<String>();
				    	if(avgName.equals(AppKeys.TIME_DAY)){
							for(XcheckReport rep:rlist){
								blist.add(rep.getBloodPressure());
							}
			           	}else if(avgName.equals(AppKeys.TIME_NIGHT)){
			           		for(XcheckReport rep:rlist){
			           			int h=rep.getCreateDate().getHours();
			            	    if(h>=18 && h<21){//夜间
			            	    	blist.add(rep.getBloodPressure());
			            	    }
							}
			           	}else if(avgName.equals(AppKeys.TIME_MORNING)){
			           		for(XcheckReport rep:rlist){
			           			int h=rep.getCreateDate().getHours();
			            	    if(h>=6 && h<9){//日间
			            	    	blist.add(rep.getBloodPressure());
			            	    }
							}
			           	}
				    	String content=getCopyWriter(blist.size(), avgName, week);
				    	map.put("copywriter", content);
						map.put("booldList", blist);
						map.put("dates", sd[0]+","+ed[0]);
					}else{
						map.put("avg", null);
						map.put("avgList", null);
						map.put("copywriter", "");
						map.put("booldList", null);
						map.put("dates", "");
					}
				}else{
					return new CallBackModelDTO<Map<String, Object>>("该时间内无此成员的检测记录",null,"200");
				}
			}
			return new CallBackModelDTO<Map<String, Object>>("查询成功",map,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	public String getCopyWriter(int count,String name,XxcheckReportBloodWeekAvg week){
		//Integer count=xcheckReportManager.getCountGroupByDate(memberId, startDate, endDate);
		String content="";
		if(count>=3){
			if(week!=null && week.getBloodAvg()!=null && !("").equals(week.getBloodAvg())){
				String[] bs=week.getBloodAvg().split(",");
				if(Integer.parseInt(bs[0])>85 && Integer.parseInt(bs[1])>135){
					content="您的血压过高，平均血压达"+bs[0]+"/"+bs[1]+"mmHg,请就诊或咨询专业医生，坚持治疗及血压监测，确保血压平稳。";
				}else{
					content=AppKeys.XUEYA_WRITER;
				}
			}
		}else{
			if(name.equals(AppKeys.TIME_DAY)){
				content=AppKeys.TIME_DAY_WRITER;
			}else if(name.equals(AppKeys.TIME_MORNING)){
				content=AppKeys.TIME_MORNING_WRITER;
			}else if(name.equals(AppKeys.TIME_NIGHT)){
				content=AppKeys.TIME_NIGHT_WRITER;
			}
		}
		return content;
	}
	private String getDate(String date) throws Exception{
		String[] ds=date.split("\\&");
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM"); // 设置时间格式
//		cal.setTime(sim.parse(ds[0]));
//		cal.set(Calendar.WEEK_OF_MONTH, Integer.parseInt(ds[1]));
//		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
//		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
//		if(1 == dayWeek) {
//			cal.add(Calendar.DAY_OF_MONTH, -1);
//		}
//		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
//		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
//		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
//		String sdate = sdf.format(cal.getTime());
//		cal.add(Calendar.DATE, 6);
//		String edate = sdf.format(cal.getTime());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(ds[1]));
		//cal.add(Calendar.WEEK_OF_MONTH, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
		String sdate=df.format(cal.getTime());
		//这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		String edate=df.format(cal.getTime());
		String content=sdate+" 00:00:00"+","+edate+" 24:00:00";
		return content;
	}
	
	
	/***************************************************血压简化版********************************************************/
	
	/**
	 * 保存血压，脉搏
	 */
	@POST
	@Path("/saveBloodPressure")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<String> saveBloodPressure(@FormParam("memberId") String memberId,@FormParam("bloodPressure") String bloodPressure,
			@FormParam("pr") Long pr,@Context HttpServletRequest request) {
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
			String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
			String userId=request.getHeader(AppKeys.USER_ID);//用户ID
			if(memberId == null || "".equals(memberId)){
				 return new CallBackModelDTO<String>("memberId不能为空",null,"500");
			}
			if(bloodPressure == null || "".equals(bloodPressure)){
				 return new CallBackModelDTO<String>("血压不能为空",null,"500");
			}
			if(pr == null || "".equals(pr)){
				 return new CallBackModelDTO<String>("脉搏不能为空",null,"500");
			}
			XcheckReportBloodPressure bp=new XcheckReportBloodPressure();
			bp.setMemberId(memberId);
			bp.setBloodPressure(bloodPressure);
			bp.setPr(pr);
			bp.setCreateDate(new Date());
			xcheckReportBloodPressureManager.save(bp);
			return new CallBackModelDTO<String>("操作成功!",null,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<String>("程序异常!",null,"500");
		}
	}
	
	/**
	 * 批量保存血压，脉搏
	 */
	@POST
	@Path("/saveBloodPressureList")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<String> saveBloodPressureList(@FormParam("memberId") String memberId,@FormParam("resultjson") String resultjson,
			@Context HttpServletRequest request) {
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
			String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
			String userId=request.getHeader(AppKeys.USER_ID);//用户ID
			if(memberId == null || "".equals(memberId)){
				 return new CallBackModelDTO<String>("memberId不能为空",null,"500");
			}
			if(resultjson == null || "".equals(resultjson)){
				 return new CallBackModelDTO<String>("resultjson不能为空",null,"500");
			}
			List<LinkedHashMap<String, Object>> readValue = maper.readValue(resultjson, List.class);
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<XcheckReportBloodPressure> list=new ArrayList<XcheckReportBloodPressure>();
			for(LinkedHashMap<String, Object> item:readValue){
				XcheckReportBloodPressure bp=new XcheckReportBloodPressure();
				bp.setMemberId(memberId);
				bp.setBloodPressure(item.get("bloodPressure").toString());
				bp.setPr(Long.valueOf(item.get("pr")+""));
				Date date=sim.parse(item.get("createDate").toString());
				if(date.getTime()<System.currentTimeMillis()){
					bp.setCreateDate(date);
					list.add(bp);
				}
			}
			if(list!=null && list.size()>0){
				xcheckReportBloodPressureManager.save(list);
				return new CallBackModelDTO<String>("操作成功!",null,"200");
			}else{
				return new CallBackModelDTO<String>("上传的数据都超过了当前时间!",null,"500");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<String>("程序异常!",null,"500");
		}
	}
	/**
	 * 查询历史记录
	 */
	@POST
	@Path("/getBloodPressureList")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Page<XcheckReportBloodPressure>> getBloodPressureList(@FormParam("memberId") String memberId,@FormParam("pager") Integer pager,
			@FormParam("pagerSize") Integer pagerSize,@Context HttpServletRequest request) {
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
			String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
			String userId=request.getHeader(AppKeys.USER_ID);//用户ID
			if(memberId == null || "".equals(memberId)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("memberId不能为空",null,"500");
			}
			if(pager == null || "".equals(pager)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("pager不能为空",null,"500");
			}
			if(pagerSize == null || "".equals(pagerSize)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("pagerSize不能为空",null,"500");
			}
			Page<XcheckReportBloodPressure> page=xcheckReportBloodPressureManager.getListByMemberId(memberId, pager, pagerSize);
			return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("操作成功!",page,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("程序异常!",null,"500");
		}
	}
	/**
	 * 按月查询历史记录
	 */
	@POST
	@Path("/getBloodPressureListByDate")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Page<XcheckReportBloodPressure>> getBloodPressureListByDate(@FormParam("memberId") String memberId,@FormParam("date") String date,
			@FormParam("pager") Integer pager,@FormParam("pagerSize") Integer pagerSize,@Context HttpServletRequest request) {
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
			String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
			String userId=request.getHeader(AppKeys.USER_ID);//用户ID
			if(memberId == null || "".equals(memberId)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("memberId不能为空",null,"500");
			}
			if(date == null || "".equals(date)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("date不能为空",null,"500");
			}
			if(pager == null || "".equals(pager)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("pager不能为空",null,"500");
			}
			if(pagerSize == null || "".equals(pagerSize)){
				 return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("pagerSize不能为空",null,"500");
			}
			Page<XcheckReportBloodPressure> page=xcheckReportBloodPressureManager.getBloodPressureListByDate(memberId, date, pager, pagerSize);
			return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("操作成功!",page,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<Page<XcheckReportBloodPressure>>("程序异常!",null,"500");
		}
	}
	
	
//------------------------------------------------------心电------------------------------------------------------------------
	
	/**
	 * 心电检测结果入库
	 */
	@POST
	@Path("/saveEcgCheckReport")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> saveEcgCheckReport(@FormParam("memberId") String memberId,@FormParam("wh") String wh,
			@FormParam("pulse") Long pulse,@FormParam("isSports") Integer isSports,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
		    }
		    if(wh == null || "".equals(wh)){
		    	 return new CallBackModelDTO<Map<String, Object>>("wh不能为空",null,"500");
		    }
		    if(isSports==null){
		    	isSports=0;
		    }
		    XdcheckReport rep=xdcheckReportManager.saveXdcheckReport(memberId, wh, pulse,isSports);
		    if(rep!=null){
		    	
		    	List<XdcheckItem> iliet=xdcheckItemManager.getAll(rep.getId());
		    	Map<String, Object> map=new HashMap<String, Object>();
		    	map.put("reportId", rep.getId());
		    	map.put("itemFeature", rep.getItemFeature());
		    	map.put("writer", getEcgWriters(rep.getWriterIds()));
		    	map.put("itemValue", iliet);
		    	logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	logger.info("------------------------程序异常 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	/**
	 * 根据报告ID查询单个报告
	 */
	@POST
	@Path("/getEcgReportByReportId")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getEcgReportByReportId(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("reportId不能为空",null,"500");
		    }
		    XdcheckReport rep=xdcheckReportManager.getByReportId(reportId);
		    if(rep!=null){
		    	List<XdcheckItem> iliet=xdcheckItemManager.getAll(rep.getId());
		    	Map<String, Object> map=new HashMap<String, Object>();
		    	map.put("reportId", rep.getId());
		    	map.put("itemFeature", rep.getItemFeature());
		    	map.put("writer", getEcgWriters(rep.getWriterIds()));
		    	map.put("itemValue", iliet);
		    	logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	logger.info("------------------------程序异常1 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	/**
	 * 查询历史记录
	 */
	@POST
	@Path("/getEcgReportListByDate")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Page<XdcheckReport>> getEcgReportListByDate(@FormParam("memberId") String memberId,@FormParam("year") String year,
			@FormParam("month") String month,@FormParam("pager") Integer pager,@FormParam("pagerSize") Integer pagerSize,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Page<XdcheckReport>>("memberId不能为空",null,"500");
		    }
		    if(year == null || "".equals(year)){
		    	 return new CallBackModelDTO<Page<XdcheckReport>>("year不能为空",null,"500");
		    }
		    if(month == null || "".equals(month)){
		    	 return new CallBackModelDTO<Page<XdcheckReport>>("month不能为空",null,"500");
		    }
		    if(pager == null || "".equals(pager)){
		    	 return new CallBackModelDTO<Page<XdcheckReport>>("pager不能为空",null,"500");
		    }
		    if(pagerSize == null || "".equals(pagerSize)){
		    	 return new CallBackModelDTO<Page<XdcheckReport>>("pagerSize不能为空",null,"500");
		    }
		    Page<XdcheckReport> page=xdcheckReportManager.getListByMemberId(memberId, year,month,pager, pagerSize);
		    if(page!=null && page.getResult()!=null && page.getResult().size()>0){
		    	logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Page<XdcheckReport>>("操作成功",page,"200");
		    }else{
		    	logger.info("------------------------无此成员记录 end---------------------------");
				return new CallBackModelDTO<Page<XdcheckReport>>("该月无此成员记录",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Page<XdcheckReport>>("程序异常",null,"500");
		}
	}
	/**
	 * 根据报告ID查询波形
	 */
	@POST
	@Path("/getEcgWaveshape")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<String> getEcgWaveshape(@FormParam("reportId") Long reportId,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<String>("reportId不能为空",null,"500");
		    }
		    String wh=xdcheckReportWaveshapeManager.getWaveshapeByReportId(reportId);
		    if(wh!=null && !("").equals(wh)){
		    	logger.info("------------------------查询成功 end---------------------------");
				return new CallBackModelDTO<String>("查询成功",wh,"200");
		    }else{
		    	logger.info("------------------------该记录没有波形 end---------------------------");
				return new CallBackModelDTO<String>("该记录没有波形",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<String>("程序异常",null,"500");
		}
	}
	
	/**
	 * 
	 * 用于组装心电的文案
	 * 
	 * @param writerIds
	 * @return
	 * @throws OcsException
	 * @throws WriterNotFoundException
	 */
	public List<Map<String,Object>> getEcgWriters(String writerIds) throws OcsException, WriterNotFoundException{
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		String[] array=writerIds.split(",");
		for(String writerId:array){
			String key= EcgWriterKeyGenerator.getEcgKey(writerId);
			Map<String,Object> writer=(Map<String, Object>) writerManager.get(key);
			result.add(writer);
		}
		return result;
		
	}
	
//------------------------------------------------------------------血压------------------------------------------------------------
	/**
	 * 保存血氧
	 */
	@POST
	@Path("/saveBloodOxygen")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> saveBloodOxygen(@FormParam("memberId") String memberId,
			@FormParam("testValue") String testValue,@FormParam("wh") String wh,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
		    }
		    if(testValue == null || "".equals(testValue)){
		    	 return new CallBackModelDTO<Map<String, Object>>("testValue不能为空",null,"500");
		    }
		    if(wh == null || "".equals(wh)){
		    	 return new CallBackModelDTO<Map<String, Object>>("wh不能为空",null,"500");
		    }
		    XycheckReport rep=xycheckReportManager.save(memberId, testValue, wh);
		    if(rep!=null){
		    	//通过文案管理类来获取文案
		    	String key=BloodOxygenKeyGenerator.getBloodOxygenWriterKey(rep.getLevel()+"");
		    	Map<String,Object> writer= (Map<String, Object>) writerManager.get(key);
		    	Map<String, Object> map=new HashMap<String, Object>();
		    	map.put("reportId", rep.getId());
		    	map.put("testValue", testValue);
		    	map.put("level", rep.getLevel());
		    	map.put("evaluation", writer.get("evaluation"));
		    	map.put("suggest", writer.get("suggest"));
		    	map.put("createDate", rep.getCreateDate());
		    	return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	return new CallBackModelDTO<Map<String, Object>>("无匹配记录",null,"500");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("------------------------程序异常 end---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
	
	/**
	 * 查询历史记录
	 */
	@POST
	@Path("/getBloodOxygenList")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getBloodOxygenList(@FormParam("memberId") String memberId,@FormParam("year") String year,
			@FormParam("month") String month,@FormParam("pager") Integer pager,@FormParam("pagerSize") Integer pagerSize,@Context HttpServletRequest request) {
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
			String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
			String userId=request.getHeader(AppKeys.USER_ID);//用户ID
			if(memberId == null || "".equals(memberId)){
				 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
			}
			if(pager == null || "".equals(pager)){
				 return new CallBackModelDTO<Map<String, Object>>("pager不能为空",null,"500");
			}
			if(pagerSize == null || "".equals(pagerSize)){
				 return new CallBackModelDTO<Map<String, Object>>("pagerSize不能为空",null,"500");
			}
			Page<XycheckReport> page=xycheckReportManager.getListByMemberId(memberId, year,month,pager, pagerSize);
			List<Map<String, Object>> mlist=new ArrayList<Map<String,Object>>();
			if(page!=null && page.getResult()!=null && page.getResult().size()>0){
				List<XycheckReport> list=page.getResult();
				for(XycheckReport rep:list){
					Map<String, Object> xmap=new LinkedHashMap<String, Object>();
					//通过文案管理类来获取文案
					String key=BloodOxygenKeyGenerator.getBloodOxygenWriterKey(rep.getLevel()+"");
			    	Map<String,Object> writer= (Map<String, Object>) writerManager.get(key);
					xmap.put("reportId", rep.getId());
			    	xmap.put("testValue", rep.getTestValue());
			    	xmap.put("level", rep.getLevel());
			    	xmap.put("evaluation", writer.get("evaluation"));
			    	xmap.put("suggest", writer.get("suggest"));
			    	xmap.put("createDate", rep.getCreateDate());
			    	mlist.add(xmap);
				}
			}
			Map<String, Object> omap=new HashMap<String, Object>();
			omap.put("result", mlist);
			omap.put("totalPages", page.getTotalPages());
			return new CallBackModelDTO<Map<String, Object>>("操作成功!",omap,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new CallBackModelDTO<Map<String, Object>>("程序异常!",null,"500");
		}
	}
	
	
	
	
	
	
	
	
	
///////////----------------------------------平台调用接口-------------------------------//////////////////////////////
	
	
	/**
	 * 平台 保存检测
	 */
	@POST
	@Path("/newSaveReportResultPlat")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> newSaveReportResultPlat(@FormParam("memberId") String memberId,@FormParam("height")Long height,
			@FormParam("weight")Long weight,@FormParam("age")Long age,@FormParam("sex")Long sex,@FormParam("resultjson") String resultjson,@Context HttpServletRequest request) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("------------------------检测结果 开始调用 start"+sim.format(new Date())+"---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(memberId == null || "".equals(memberId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("memberId不能为空",null,"500");
		    }
		    if(height == null){
		    	 return new CallBackModelDTO<Map<String, Object>>("height不能为空",null,"500");
		    }
		    if(weight == null){
		    	 return new CallBackModelDTO<Map<String, Object>>("weight不能为空",null,"500");
		    }
		    if(age == null){
		    	 return new CallBackModelDTO<Map<String, Object>>("age不能为空",null,"500");
		    }
		    if(sex == null){
		    	 return new CallBackModelDTO<Map<String, Object>>("sex不能为空",null,"500");
		    }
		    if(resultjson == null || "".equals(resultjson)){
		    	 return new CallBackModelDTO<Map<String, Object>>("resultjson不能为空",null,"500");
		    }
		    Xmember xm=new Xmember();
		    xm.setId(memberId);
		    xm.setHeight(height);
		    xm.setWeight(weight);
		    xm.setAge(age);
		    xm.setSex(sex);
		    Map<String,Object> result=xcheckReportManager.saveReport(xm, resultjson, "2.0");
		    Map<String,Object> reportData= (Map<String,Object>)result.get("report");
		    Long reportId=(Long)reportData.get("reportId");
	    	//报告的数据
	    	List<XcheckReportRefItem> list=xcheckReportRefItemManager.getListByReportId(reportId);
	    	result.put("itemList", list);//将25项放到返回值中
    		logger.info("------------------------检测结果 调用成功 end"+sim.format(new Date())+"---------------------------");
			return new CallBackModelDTO<Map<String, Object>>("调用成功",result,"200");
		} catch (Exception e) {
			logger.info("------------------------程序异常 end---------------------------",e);
			return new CallBackModelDTO<Map<String, Object>>("系统繁忙",null,"500");
		}
	}
	
	
	/**
	 * 平台 查询单个报告
	 */
	@POST
	@Path("/getNewReportInfoPlat")
	@AccessPointCut(accessRead = true)
	@Produces( { MediaType.APPLICATION_JSON })
	public CallBackModelDTO<Map<String, Object>> getNewReportInfoPlat(@FormParam("reportId") Long reportId,@FormParam("memberId") String memberId,
			@FormParam("height")Long height,@FormParam("weight")Long weight,@FormParam("age")Long age,@FormParam("sex")Long sex,@Context HttpServletRequest request) {
		logger.info("------------------------开始调用 start---------------------------");
		try {
			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
		    if(reportId == null || "".equals(reportId)){
		    	 return new CallBackModelDTO<Map<String, Object>>("reportId不能为空",null,"500");
		    }
		    String jsonResult=xCheckReportJsonResultManager.getResultById(reportId);
		    if(jsonResult!=null){
		    	Map<String, Object> map=maper.readValue(jsonResult,Map.class);
	    		logger.info("------------------------调用成功 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("操作成功",map,"200");
		    }else{
		    	logger.info("------------------------无此报告记录 end---------------------------");
				return new CallBackModelDTO<Map<String, Object>>("无此报告记录",null,"500");
		    }
		} catch (Exception e) {
			logger.info("------------------------程序异常 end---------------------------",e);
			return new CallBackModelDTO<Map<String, Object>>("程序异常",null,"500");
		}
	}
}
