package com.dvn.telemedicine.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XcheckItemDao;
import com.dvn.telemedicine.dao.x.XcheckReportDao;
import com.dvn.telemedicine.dao.x.XcheckReportItemAverageDayDao;
import com.dvn.telemedicine.dao.x.XcheckReportRefItemDao;
import com.dvn.telemedicine.entity.x.XcheckItem;
import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XcheckReportItemAverageDay;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;
import com.dvn.telemedicine.util.AppKeys;

@Component
@Transactional
public class XcheckReportItemAverageDayService {

	private static Logger logger = Logger.getLogger(XcheckReportItemAverageDayService.class);
	@Autowired
	private XcheckReportItemAverageDayDao xcheckReportItemAverageDayDao;;
	@Autowired
	private XcheckReportRefItemDao xcheckReportRefItemDao;;
	@Autowired
	private XcheckReportDao xcheckReportDao;
	@Autowired
	private XcheckItemDao xcheckItemDao;

	public void doItemAverageDay() throws HttpException, IOException {
		try {
			saveItemAverageDay();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 增加成员每天各项检测健康记录
	 */
	public void saveItemAverageDay() throws Exception{
		logger.info("------------------------增加成员每天各项检测健康记录  begin----------------------");
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //得到前一天
		String date = ft.format(calendar.getTime());
		//去报告表用分组查看 得到做检测的所有成员
		String hql="from XcheckReport where createDate like '%"+date+"%' group by memberId";
		List<XcheckReport> list=xcheckReportDao.find(hql);
		List<XcheckItem> itlist=xcheckItemDao.getAll();
		for(XcheckReport rep:list){
			String eql="from XcheckReportItemAverageDay where memberId='"+rep.getMemberId()+"' and createDate like '%"+date+"%'";
			List<XcheckReportItemAverageDay> elist=xcheckReportItemAverageDayDao.find(eql);
			if(elist==null || elist.size()<=0){//查看库里面是否已经存在这天的数据了
				String sql="from XcheckReport where memberId='"+rep.getMemberId()+"' and createDate like '%"+date+"%'";
				List<XcheckReport> mlist=xcheckReportDao.find(sql);
				//取脉搏,取心脉
				Double psum=0.0;
				Double hsum=0.0;
				for(XcheckReport report:mlist){
					//先累加再计算脉搏，心脉平均值
					if(report.getPulse()!=null && !("").equals(report.getPulse()) && !("undefined").equals(report.getPulse())){
						psum+=Double.valueOf(report.getPulse());	
					}
					if(report.getScore()!=null && !("").equals(report.getScore()) && !("undefined").equals(report.getScore())){
						hsum+=Double.valueOf(report.getScore());
					}
				}
				if(psum>0){
					Double pavg =new BigDecimal(psum/mlist.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double havg =new BigDecimal(hsum/mlist.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					//脉搏平均值
					XcheckReportItemAverageDay pul=new XcheckReportItemAverageDay();
					pul.setItemName(AppKeys.PLUSE);
					pul.setMemberId(rep.getMemberId());
					pul.setScore(pavg+"");
					pul.setCreateDate(ft.parse(date));
					xcheckReportItemAverageDayDao.save(pul);
					//心脉平均值
					XcheckReportItemAverageDay score=new XcheckReportItemAverageDay();
					score.setItemName(AppKeys.ITEM_SCORE);
					score.setMemberId(rep.getMemberId());
					score.setScore(havg+"");
					score.setCreateDate(ft.parse(date));
					xcheckReportItemAverageDayDao.save(score);
				}
				//取血压
				List<XcheckReport> bloodlist=xcheckReportDao.find("from XcheckReport where memberId ='"+rep.getMemberId()+"' and createDate like '%"+date+"%'");
				Double lbsum=0.0;
				Double hbsum=0.0;
				for(XcheckReport cp:bloodlist){
					//先累加再计算血压平均值
					String[] ss=cp.getBloodPressure().split(",");
					lbsum+=Double.valueOf(ss[0]);	
					hbsum+=Double.valueOf(ss[1]);		
				}
				//血压平均值
				if(lbsum>0){
					Double lbavg =new BigDecimal(lbsum/bloodlist.size()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double hbavg =new BigDecimal(hbsum/bloodlist.size()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					//舒张压
					XcheckReportItemAverageDay lboold=new XcheckReportItemAverageDay();
					lboold.setItemName(AppKeys.LOW_PRESSURE_VALUE);
					lboold.setMemberId(rep.getMemberId());
					lboold.setScore(lbavg+"");
					lboold.setCreateDate(ft.parse(date));
					xcheckReportItemAverageDayDao.save(lboold);
					//收缩压
					XcheckReportItemAverageDay hboold=new XcheckReportItemAverageDay();
					hboold.setItemName(AppKeys.HIGH_PRESSURE_VALUE);
					hboold.setMemberId(rep.getMemberId());
					hboold.setScore(hbavg+"");
					hboold.setCreateDate(ft.parse(date));
					xcheckReportItemAverageDayDao.save(hboold);
				}
				
				//取24项
				for(XcheckItem item:itlist){
					String src="from XcheckReportRefItem where reportId in (";
					for(int i=0;i<mlist.size();i++){
						if(i==mlist.size()-1){
							src+=mlist.get(i).getId()+")";
						}else{
							src+=mlist.get(i).getId()+",";
						}
					}
					src+=" and itemId="+item.getId()+" and createDate like '%"+date+"%'";
					List<XcheckReportRefItem> repList=xcheckReportRefItemDao.find(src);
					Double itemsum=0.0;
					for(XcheckReportRefItem ref:repList){
						//先累加再计算单项平均值
						if(ref.getTestValue()!=null && !("").equals(ref.getTestValue()) && !("undefined").equals(ref.getTestValue())){
							itemsum+=Double.valueOf(ref.getTestValue());	
						}
					}
					//每项平均值
					if(itemsum>0){
						XcheckReportItemAverageDay ss=new XcheckReportItemAverageDay();
						ss.setItemName(item.getItemName());
						ss.setMemberId(rep.getMemberId());
						Double itemavg =new BigDecimal(itemsum/repList.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						ss.setScore(itemavg+"");
						ss.setCreateDate(ft.parse(date));
						xcheckReportItemAverageDayDao.save(ss);
					}
				}
			}
		}
		delOldReport();//删除两个月以前的数据
		logger.info("------------------------增加成员每天各项检测健康记录    end----------------------");
	}
	private void delOldReport(){
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -6);
		String date=sim.format(c.getTime());
		String hql="from XcheckReportItemAverageDay where createDate like '%"+date+"%'";
		List<XcheckReportItemAverageDay> list=xcheckReportItemAverageDayDao.find(hql);
		for(XcheckReportItemAverageDay day:list){
			xcheckReportItemAverageDayDao.delete(day.getId());
		}
	}
//	private void test(){
//		logger.info("------------------------增加成员每天各项检测健康记录  begin----------------------");
//		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DATE, -1);    //得到前一天
//		String date = ft.format(calendar.getTime());
//		String date = "2013-09-28";
//		List<XcheckReportRefItem> rfi= xcheckReportRefItemDao.getRefItemList(date);
//		if(rfi.size() >0){
//			XcheckReportItemAverageDay itemBlood=new XcheckReportItemAverageDay();
//			for(XcheckReportRefItem r:rfi){
//				XcheckReportItemAverageDay itemAverage=new XcheckReportItemAverageDay();
//				
//				XcheckReport xr= xcheckReportDao.findUniqueBy("id", r.getReportId());
//				String hql="from XcheckReportRefItem where reportId ='"+r.getReportId()+"' and itemId ='"+r.getItemId()+"' and createDate like '%"+date+"%'";
//				XcheckReportRefItem xrf=xcheckReportRefItemDao.findUnique(hql);
//				XcheckItem item=xcheckItemDao.findUniqueBy("id", r.getItemId());
//				List<XtestBloodPressureReport> bloodlist=xtestBloodPressureReportDao.find("from XtestBloodPressureReport where memberId ='"+xr.getMemberId()+"' and createDate like '%"+date+"%'");
//				if(xr != null ){
//					if(xrf != null){
//						if(item != null){
//							if(bloodlist.size() >0){
//								int highTotal=0;
//								int lowTotal=0;
//								for(XtestBloodPressureReport blood:bloodlist){
//									highTotal+=(int)Math.round(Double.valueOf(blood.getHighHandedValue()));
//									lowTotal+=(int)Math.round(Double.valueOf(blood.getLowPressureValue()));
//								}
//								String averageHigh=String.valueOf(highTotal/bloodlist.size());
//								String averageLow=String.valueOf(lowTotal/bloodlist.size());
//								String averageBlood=averageLow+","+averageHigh;
//								itemBlood.setScore(averageBlood);
//							}							
////							itemAverage.setPluse(xr.getPulse());
//							itemAverage.setMemberId(xr.getMemberId());
//							itemAverage.setScore(xrf.getTestValue());
//							itemAverage.setCreateDate(xrf.getCreateDate());
//							itemAverage.setItemName(item.getItemName());
//							//判断是否重复
//							String checkDate=String.valueOf(xrf.getCreateDate()).substring(0, 10);
//							XcheckReportItemAverageDay checkRe=xcheckReportItemAverageDayDao.findUnique("from XcheckReportItemAverageDay where memberId ='"+xr.getMemberId()+"' and itemName ='"+item.getItemName()+"' and createDate like '%"+checkDate+"%'");
//							if(checkRe ==null){
//								xcheckReportItemAverageDayDao.save(itemAverage);
//							}
//						}
//					}
//				}
//			}
//			
//			String checkDate=String.valueOf(xrf.getCreateDate()).substring(0, 10);
//			XcheckReportItemAverageDay checkRe=xcheckReportItemAverageDayDao.findUnique("from XcheckReportItemAverageDay where memberId ='"+AppKeys.PRESSURE_VALUE+"' and itemName ='"+itemBlood.getItemName()+"' and createDate like '%"+checkDate+"%'");
//			if(checkRe ==null){
//				itemBlood.setMemberId(xr.getMemberId());
//				itemBlood.setCreateDate(xrf.getCreateDate());
//				itemBlood.setItemName(AppKeys.PRESSURE_VALUE);
//				xcheckReportItemAverageDayDao.save(itemBlood);
//			}
//			
////			String checkDate=String.valueOf(xrf.getCreateDate()).substring(0, 10);
////			XcheckReportItemAverageDay checkRe=xcheckReportItemAverageDayDao.findUnique("from XcheckReportItemAverageDay where memberId ='"+AppKeys.PRESSURE_VALUE+"' and itemName ='"+itemBlood.getItemName()+"' and createDate like '%"+checkDate+"%'");
////			if(checkRe ==null){
////				itemBlood.setMemberId(xr.getMemberId());
////				itemBlood.setCreateDate(xrf.getCreateDate());
////				itemBlood.setItemName(AppKeys.PRESSURE_VALUE);
////				xcheckReportItemAverageDayDao.save(itemBlood);
////			}
//		}
//		logger.info("------------------------增加成员每天各项检测健康记录    end----------------------");
//	}
	
//	/**
//	 * 查询成员50天检测项健康档案
//	 */
//	@POST
//	@Path("/getMemberItemRecord")
//	@AccessPointCut(accessRead = true)
//	@Produces( { MediaType.APPLICATION_JSON })
//	public CallBackModelDTO<Map<String,List>> getMemberItemRecord(@FormParam("memberId") String memberId,@FormParam("itemName") String itemName,@FormParam("testMark")Long testMark,@Context HttpServletRequest request) {
//		logger.info("------------------------开始调用 start---------------------------");
//		try {
//			String stboxcode = request.getHeader(AppKeys.STBOC_CODE); // 机顶盒硬件ID
//		    String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
//		    String userId=request.getHeader(AppKeys.USER_ID);//用户ID
//		    if(memberId == null || "".equals(memberId)){
//		    	 return new CallBackModelDTO<Map<String,List>>("memberId不能为空",null,"500");
//		    }
//		    if(itemName == null || "".equals(itemName)){
//		    	 return new CallBackModelDTO<Map<String,List>>("检测项名称不能为空",null,"500");
//		    }
//		    Map<String,List> mapScore=new LinkedHashMap<String, List>();
//		    List listScore =new ArrayList();
//		    List listBloodPressure =new ArrayList();
//		    List listHeartRecord=new ArrayList();
//		    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//		    if(testMark !=0){
//		    	for(int i=51;i>0;i--){
//					XcheckReportItemAverageDay averageDay=new XcheckReportItemAverageDay();
//					Calendar calendar = Calendar.getInstance();
//					calendar.add(Calendar.DATE, -i);    //得到前一天
//					String checkDate = ft.format(calendar.getTime());
//					if("血压".equals(itemName) && !"".equals(itemName)){
//						List<XtestBloodPressureReport> bloodlist=xtestBloodPressureReportManager.getListByDate(memberId, checkDate);
//						if(bloodlist.size() >0){
//							int highTotal=0;
//							int lowTotal=0;
//							for(XtestBloodPressureReport blood:bloodlist){
//								highTotal+=(int)Math.round(Double.valueOf(blood.getHighHandedValue()));
//								lowTotal+=(int)Math.round(Double.valueOf(blood.getLowPressureValue()));
//							}
//							String averageHigh=String.valueOf(highTotal/bloodlist.size());
//							String averageLow=String.valueOf(lowTotal/bloodlist.size());
//							String averageBlood=averageLow+","+averageHigh;
//							listBloodPressure.add(averageBlood);
//						}else{
//							listBloodPressure.add(null);
//						}
//						mapScore.put("bloodPressure", listBloodPressure);
//					}
//					else if("心脉指数".equals(itemName) && !"".equals(itemName)){
//						List<XcheckReport>  heartList=xcheckReportManager.getListByDate(memberId, checkDate);
//						if(heartList.size() >0){
//							Float totalScore=0f;
//							for(XcheckReport record:heartList){
//								totalScore+=Float.valueOf(record.getScore());							
//							}
//							String averageRecord=String.valueOf(totalScore/heartList.size());
//							listHeartRecord.add(averageRecord);
//						}else{
//							listHeartRecord.add(null);
//						}
//						mapScore.put("heartRecord", listHeartRecord);
//					}
//					else{
//						averageDay=xcheckReportItemAverageDayManager.getByCondition(memberId, itemName, checkDate);
//						if(averageDay !=null){
//							listScore.add(averageDay.getScore());
//						}else{
//							listScore.add(null);
//						}
//						mapScore.put("score", listScore);
//					}
//				}
//		    }else{
//		    	if("血压".equals(itemName) && !"".equals(itemName)){
//		    		List<XtestBloodPressureReport> bloodlist=xtestBloodPressureReportManager.getListByDate(memberId, null);
//		    		Float highTotal=0.000f;
//		    		Float lowTotal=0.000f;
//		    		for(int i=0;i<51;i++){
//		    			if(i< bloodlist.size()){
//		    				highTotal=Float.parseFloat(bloodlist.get(i).getHighHandedValue());
//		    				lowTotal=Float.parseFloat(bloodlist.get(i).getLowPressureValue());
//		    				String averageHigh=String.valueOf(highTotal);
//							String averageLow=String.valueOf(lowTotal);
//							String averageBlood=averageLow+","+averageHigh;						
//							listBloodPressure.add(averageBlood);
//		    			}else{
//		    				listBloodPressure.add(null);
//		    			}
//					}
//					mapScore.put("bloodPressure", listBloodPressure);
//				}
//				else if("心脉指数".equals(itemName) && !"".equals(itemName)){
//					List<XcheckReport>  heartList=xcheckReportManager.getListByDate(memberId, null);
//					for(int i=0;i<51;i++){
//						if(i< heartList.size()){
//							listHeartRecord.add(heartList.get(i).getScore());
//						}else{
//							listHeartRecord.add(null);
//						}
//					}
//					mapScore.put("heartRecord", listHeartRecord);
//				}
//				else{
//					List<XcheckReportItemAverageDay> averageDay=new ArrayList<XcheckReportItemAverageDay>();
//					averageDay=xcheckReportItemAverageDayManager.getConditionList(memberId, itemName, null);
//					for(int i=0;i<51;i++){
//						if(i<averageDay.size()){
//							listScore.add(averageDay.get(i).getScore());
//						}else{
//							listScore.add(null);
//						}
//					}		
//					mapScore.put("score", listScore);
//				}
//		    }
//	    	logger.info("------------------------调用成功 end---------------------------");
//			return new CallBackModelDTO<Map<String,List>>("操作成功",mapScore,"200");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("------------------------程序异常 end---------------------------");
//			return new CallBackModelDTO<Map<String,List>>("程序异常",null,"500");
//		}
//	}
}


