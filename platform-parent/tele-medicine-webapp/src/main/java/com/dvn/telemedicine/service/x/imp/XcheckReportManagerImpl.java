package com.dvn.telemedicine.service.x.imp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.core.TeleMember;
import com.dvn.telemedicine.core.TelemedicineAnalyzer;
import com.dvn.telemedicine.core.blood.BloodCategory;
import com.dvn.telemedicine.core.blood.BloodExecuteHandler;
import com.dvn.telemedicine.core.blood.BloodIndex;
import com.dvn.telemedicine.core.blood.BloodModel;
import com.dvn.telemedicine.core.blood.entity.BloodContextEntity;
import com.dvn.telemedicine.core.blood.entity.BloodRequestData;
import com.dvn.telemedicine.core.blood.entity.BloodResult;
import com.dvn.telemedicine.core.blood.entity.MonthReportData;
import com.dvn.telemedicine.core.blood.entity.WeekReportData;
import com.dvn.telemedicine.dao.x.XCheckReportCategoryScoreDao;
import com.dvn.telemedicine.dao.x.XCheckReportJsonResultDao;
import com.dvn.telemedicine.dao.x.XCheckReportModelScoreDao;
import com.dvn.telemedicine.dao.x.XcheckReportDao;
import com.dvn.telemedicine.dao.x.XcheckReportRefItemDao;
import com.dvn.telemedicine.entity.x.XCheckReportCategoryScore;
import com.dvn.telemedicine.entity.x.XCheckReportJsonResult;
import com.dvn.telemedicine.entity.x.XCheckReportModelScore;
import com.dvn.telemedicine.entity.x.XcheckItem;
import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;
import com.dvn.telemedicine.entity.x.XcheckReportWaveshape;
import com.dvn.telemedicine.entity.x.Xmember;
import com.dvn.telemedicine.entity.x.XxcheckReportCrowd;
import com.dvn.telemedicine.service.x.XcheckItemManager;
import com.dvn.telemedicine.service.x.XcheckReportManager;
import com.dvn.telemedicine.service.x.XcheckReportRefItemManager;
import com.dvn.telemedicine.service.x.XcheckReportWaveshapeManager;
import com.dvn.telemedicine.service.x.XxcheckReportCrowdManager;
import com.dvn.telemedicine.util.AppKeys;
import com.dvn.telemedicine.util.GetDValue;
import com.dvn.telemedicine.util.RequestData;

@Component
@Transactional
public class XcheckReportManagerImpl implements XcheckReportManager {
	private static Logger logger = LoggerFactory.getLogger(XcheckReportManagerImpl.class);
	@Autowired
	private  XcheckReportDao xcheckReportDao;
	@Autowired
	private XcheckReportRefItemDao xcheckReportRefItemDao;
	@Autowired
	private XcheckReportRefItemManager xcheckReportRefItemManager;
	@Autowired
	private XcheckItemManager xcheckItemManager;
	@Autowired
	private XxcheckReportCrowdManager xxcheckReportCrowdManager;
	@Autowired
	private XcheckReportWaveshapeManager xcheckReportWaveshapeManager;
	@Autowired
	private TaskExecutor taskExecutor;
	@Autowired
	private TelemedicineAnalyzer telemedicineAnalyzer;
	@Autowired
	private XCheckReportCategoryScoreDao xcheckReportCategoryScoreDao;
	@Autowired
	private XCheckReportModelScoreDao xCheckReportModelScoreDao;
	@Autowired
	private XCheckReportJsonResultDao xCheckReportJsonResultDao;
	
	@Override
	public Page<XcheckReport> getList(String memberId, Integer mark,Integer pager,Integer pagerSize) {
		return xcheckReportDao.getListByPage(memberId, mark, pager, pagerSize);
	}

	@Override
	public XcheckReport getById(Long id) {
		return xcheckReportDao.findUniqueBy("id", id);
	}

	

	@Override
	public Map<String, Object> saveReport(Xmember member, String resultJson,String version)
			throws Exception {
		final XcheckReportDao reportDao=xcheckReportDao;
		final XxcheckReportCrowdManager crowdManager=xxcheckReportCrowdManager;
		final XCheckReportCategoryScoreDao cateScoreDao=xcheckReportCategoryScoreDao;
		TeleMember teleMember=new TeleMember(member.getId(),member.getAge().intValue(),member.getHeight().intValue()
				,member.getWeight().intValue(),member.getSex().intValue());
		BloodResult result=null;
		BloodRequestData requestData=new BloodRequestData();
		requestData.setMember(teleMember);//放入成员数据
		requestData.setJsonData(resultJson);//放入jsonData
		//回调函数
		BloodExecuteHandler handler=new BloodExecuteHandler() {
			@Override
			public Serializable initReportId(BloodContextEntity context) {
				XcheckReport report=new XcheckReport();
				report.setBloodPressure(context.getDownBloodPressure()+","+context.getUpBloodPressure());//放入血压
				report.setCreateDate(new Date());//放入时间
				report.setItemFeature(context.getItemFeature());//放入特征值
				report.setScore(context.getScore()+"");//放入分数
				report.setMark(context.getMark());//是否治疗
				report.setSex(context.getMember().getSex().longValue());
				report.setPulse(context.getPulse());//放入脉率
				report.setMemberId(context.getMember().getId());
				reportDao.save(report);
				return report.getId();
			}
			
			@Override
			public Double getRateOfTest(String memberId) {
				Calendar calendar= Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY,0);
				calendar.set(Calendar.MINUTE,0);
				calendar.set(Calendar.SECOND,0);
				Date endTime=calendar.getTime();
				calendar.add(Calendar.DAY_OF_YEAR,-7);
				Date startTime=calendar.getTime();
				int count= reportDao.getCountOfTestTimes(memberId, startTime, endTime);
				System.out.println(((double)count)/7);
				return ((double)count)/7;
			}
			
			@Override
			public WeekReportData getPreviousWeekData(String memberId) {
				WeekReportData data=new WeekReportData();
				Calendar calendar= Calendar.getInstance();
				calendar.setFirstDayOfWeek(Calendar.MONDAY);//定义礼拜一是一周开始的日子
				int weekNum=calendar.get(Calendar.WEEK_OF_YEAR);
				if(weekNum==1){//表示目前是一年的第一周,所以没有上一个自然周了,如此返回就可以取消周报
					data.setTestTimes(0);
					return data;
				}
				calendar.set(Calendar.WEEK_OF_YEAR,weekNum-1);//获得上一个自然周的礼拜一
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				calendar.set(Calendar.HOUR_OF_DAY,0);
				calendar.set(Calendar.MINUTE,0);
				calendar.set(Calendar.SECOND,0);
				Date startTime=calendar.getTime();
				calendar.add(Calendar.DAY_OF_YEAR,7);
				Date endTime=calendar.getTime();
				int testTimes= reportDao.getCountOfTestTimes(memberId, startTime, endTime);
				data.setTestTimes(testTimes);//表示测量次数为0,直接返回
				if(testTimes==0){
					return data;
				}
				int testDays=reportDao.getTestDay(memberId, startTime, endTime);
				data.setTestDays(testDays);
				data.setWeekDays(7);
				data.setWeeksOfYear(weekNum-1);
				List<Map<String,Integer>> errorList= cateScoreDao.queryErrorTimesByDate(memberId, startTime, endTime);
				data.setErrorMap(constructErrorTimes(errorList));
				return data;
			}
			
			@Override
			public MonthReportData getPreviousMonthData(String memberId) {
				 MonthReportData data=new MonthReportData();
				 Calendar calendar=Calendar.getInstance();
				 calendar.setTime(new Date());
				 calendar.add(Calendar.MONTH,-1);
				 int month=calendar.get(Calendar.MONTH)+1;//0-11范围 补1
				 int monthDays= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				 calendar.set(Calendar.DAY_OF_MONTH,1);
			     calendar.set(Calendar.HOUR_OF_DAY,0);
			     calendar.set(Calendar.MINUTE,0);
				 calendar.set(Calendar.SECOND,0);
				 Date startTime=calendar.getTime();
				 calendar.add(Calendar.MONTH,1);
				 Date endTime=calendar.getTime();
				 int testTimes= reportDao.getCountOfTestTimes(memberId, startTime, endTime);
				 data.setTestTimes(testTimes);
				 if(testTimes==0){
						return data;
					}
				 int testDays=reportDao.getTestDay(memberId, startTime, endTime);
				 data.setTestDays(testDays);
				 data.setMonthOfYear(month);
				 data.setMonthDays(monthDays);
				 List<Map<String,Integer>> errorList= cateScoreDao.queryErrorTimesByDate(memberId, startTime, endTime);
				 data.setErrorMap(constructErrorTimes(errorList));
				return data;
			}
			
			@Override
			public Map<BloodCategory, Integer> getPreviousCategoryScore(
					Serializable repId, String memberId) {
				Long reportId=reportDao.getPreviousReportId(memberId, (Long)repId);
				if(reportId==null){
					return null;
				}else{
					List<XCheckReportCategoryScore> scores= xcheckReportCategoryScoreDao.queryCategoryScoresById(reportId);
					if(scores.isEmpty()){//功能项数据遗失了 建议抛异常
						return null;
					}
					Map<BloodCategory, Integer> map=new LinkedHashMap<BloodCategory, Integer>();
					for(XCheckReportCategoryScore score:scores){
						map.put(BloodCategory.getById(score.getCategoryId()), score.getScore());
					}
					return map;
				}
			}
			
			@Override
			public Integer getCrowdedId(TeleMember member) {
				//计算的出人群
				Double height=new BigDecimal(Double.valueOf(member.getHeight())/100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    			Double bmiValue=Double.valueOf(member.getWeight())/(height*height);
    			int bmi=new BigDecimal(bmiValue).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    			XxcheckReportCrowd crowd=crowdManager.getByInfo(member.getAge().longValue(), member.getSex().longValue(), bmi);
				return crowd.getId().intValue();
				
				
			}
		};
		
		if("1.7".equals(version)){
			result= telemedicineAnalyzer.analyzeBloodScore_1_7(requestData, handler);
		}else{
			result= telemedicineAnalyzer.analyzeBloodScore_2_0(requestData, handler);
		}
		final Long reportId=(Long)result.getReportId();
		//存入25项
		Map<BloodIndex,Double> itemValues= result.getItemValues();
		 for(BloodIndex index:BloodIndex.values()){
		    	XcheckReportRefItem rf=new XcheckReportRefItem();
		    	rf.setId(reportId+"_"+index.id);
		    	rf.setReportId(reportId);
		    	rf.setItemId((long)index.id);
		    	rf.setTestValue(itemValues.get(index)+"");
		    	rf.setCreateDate(new Date());
		    	xcheckReportRefItemDao.save(rf);
		    }
		
		Map<BloodCategory,Integer> cateScores= result.getCateScores();//存入功能项评分
		
		for(BloodCategory cate:BloodCategory.values()){
			XCheckReportCategoryScore entity=new XCheckReportCategoryScore();
			entity.setReportId(reportId);
			entity.setCategoryId(cate.id);
			entity.setScore(cateScores.get(cate));
			xcheckReportCategoryScoreDao.save(entity);
		}
		
		Map<BloodModel,Integer> modelScores=result.getModelScores();//存入模块评分
		for(BloodModel model:BloodModel.values()){
			XCheckReportModelScore entity=new XCheckReportModelScore();
			entity.setReportId(reportId);
			entity.setModelId(model.id);
			entity.setScore(modelScores.get(model));
			xCheckReportModelScoreDao.save(entity);
		}
		//存入json数据
		XCheckReportJsonResult jsonResult=new XCheckReportJsonResult();
		jsonResult.setReportId(reportId);
		jsonResult.setJsonData(result.getResultAsString());
		xCheckReportJsonResultDao.save(jsonResult);
		
		//波形异步入库
		final String wh=result.getWh();
		taskExecutor.execute(new Runnable() {
			public void run(){
				try {
					XcheckReportWaveshape xc=new XcheckReportWaveshape();
				    xc.setReportId(reportId);
				    xc.setWaveShape(wh);
				    xc.setCreateDate(new Date());
				    xcheckReportWaveshapeManager.save(xc);
				    logger.info("------------------------------------波形入库--------------------------------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return result.getMapResult();
	}

	@Override
	public List<XcheckReport> getList(String memberId, Integer pager,
			Integer pagerSize) {
		return xcheckReportDao.getList(memberId, pager, pagerSize);
	}

	@Override
	public List<XcheckReport> getList(Long sex) {
		String hql="from XcheckReport where sex="+sex+" group by memberId";
		return xcheckReportDao.find(hql);
	}

	@Override
	public XcheckReport getXcheckReport(String memberId) {
		String hql="from XcheckReport where memberId='"+memberId+"' order by createDate desc";
		List<XcheckReport> list=xcheckReportDao.find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public List<XcheckReport> getListByDate(String memberId, String date) {
		String hql="from XcheckReport where 1=1";
		if(memberId !=null){
			hql+=" and memberId='"+memberId+"'";
		}
		if(date !=null){
			hql+=" and createDate like '%"+date+"%' order by createDate asc";
		}
		if(date ==null){
			hql+=" order by createDate desc";
		}
		return xcheckReportDao.find(hql);
	}

	@Override
	public List<XcheckReport> getListByDate(String memberId, String startDate,
			String endDate) {
		String hql="from XcheckReport where memberId='"+memberId+"' and createDate>='"+startDate+"' and createDate<='"+endDate+"'";
		return xcheckReportDao.find(hql);
	}
	@Override
	public String getSex(String memberId){
//		XTestPaperBaseAnwserResult res=xTestPaperBaseAnwserResultManager.getMemberPaperBaseAswser(memberId, AppKeys.SEX);
//		if(res!=null && res.getResult()!=null && !("").equals(res.getResult())){
//			return res.getResult();
//		}else{
			Xmember xm=RequestData.getPayUserId(memberId);
			if(xm!=null){
				String ss=null;
				if(xm.getSex()!=null && !("").equals(xm.getSex())){
					return xm.getSex()+"";
				}else if(xm.getIdCard()!=null && !("").equals(xm.getIdCard()) && xm.getIdCard().length()==18){
					ss=GetDValue.getMemberSex(xm.getIdCard())+"";
				}
				return ss;
			}else{
				return null;
			}
//		}
	}

	@Override
	public void save(XcheckReport rep) {
		xcheckReportDao.save(rep);		
	}

	@Override
	public String getItemAvg(String memberId,String itemName) {
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String avg="";
		List<XcheckReport> list=getListByDate(memberId, sim.format(new Date()));
		if(itemName.equals(AppKeys.PRESSURE_VALUE) || itemName.equals(AppKeys.PLUSE) || itemName.equals(AppKeys.ITEM_SCORE)){//血压,脉搏，心脉
			if(itemName.equals(AppKeys.PRESSURE_VALUE)){
				Double low=0.0;
				Double high=0.0;
				for(XcheckReport rep:list){
					String[] ss=rep.getBloodPressure().split(",");
					low+=Double.valueOf(ss[0]);
					high+=Double.valueOf(ss[1]);
				}
				if(low>0){
					Double avgLow=new BigDecimal(low/list.size()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					Double highLow=new BigDecimal(high/list.size()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					avg=avgLow+","+highLow;
				}
				
			}else if(itemName.equals(AppKeys.PLUSE)){
				Double pul=0.0;
				for(XcheckReport rep:list){
					pul+=Double.valueOf(rep.getPulse());
				}
				if(pul>0){
					Double avgPul=new BigDecimal(pul/list.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					avg=avgPul+"";
				}
			}else{
				Double sc=0.0;
				for(XcheckReport rep:list){
					sc+=Double.valueOf(rep.getScore());
				}
				if(sc>0){
					Double avgSc=new BigDecimal(sc/list.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					avg=avgSc+"";
				}
			}
		}else{//24项
			if(list!=null && list.size()>0){
				XcheckItem item=xcheckItemManager.getByItemName(itemName);
				if(item!=null){
					List<Long> reportIds=new ArrayList<Long>();
					for(XcheckReport rep:list){
						reportIds.add(rep.getId());
					}
					List<XcheckReportRefItem> itList=xcheckReportRefItemManager.getListByDate(item.getId(),reportIds);
					Double sf=0.0;
					for(XcheckReportRefItem it:itList){
						sf+=Double.valueOf(it.getTestValue());
					}
					if(sf>0){
						Double avgSf=new BigDecimal(sf/itList.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						avg=avgSf+"";
					}
				}
			}
		}
		return avg;
	}

	@Override
	public int getCountByDate(String memberId, String date) {
		String hql="select count(*) from XcheckReport where memberId='"+memberId+"' and createDate like '%"+date+"%'";
		List<Object> list=xcheckReportDao.find(hql);
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0)+"");
		}
		return 0;
	}

	@Override
	public XcheckReport getXcheckReport(String memberId, Long reportId) {
		String hql="from XcheckReport where memberId='"+memberId+"' and  id<"+reportId+" order by id desc";
		Page<XcheckReport> page=new Page<XcheckReport>(1);
		page.setPageNo(1);
		page=xcheckReportDao.findPage(page, hql);
		List<XcheckReport> list=page.getResult();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public XcheckReport getXcheckReportByDate(String memberId, String date) {
		String hql="from XcheckReport where memberId='"+memberId+"' and createDate like '%"+date+"%' order by createDate desc";
		Page<XcheckReport> page=new Page<XcheckReport>(1);
		page.setPageNo(1);
		page=xcheckReportDao.findPage(page, hql);
		List<XcheckReport> list=page.getResult();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getMemberReportCount(String memberId) {
		String hql="select count(*) from XcheckReport where memberId='"+memberId+"'";
		List<Object> list=xcheckReportDao.find(hql);
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0)+"");
		}
		return 0;
	}

	@Override
	public XcheckReport getXcheckReportOrderByDate(String memberId) {
		String hql="from XcheckReport where memberId='"+memberId+"' order by createDate asc";
		Page<XcheckReport> page=new Page<XcheckReport>(1);
		page.setPageNo(1);
		page=xcheckReportDao.findPage(page, hql);
		List<XcheckReport> list=page.getResult();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	
	
	@Override
	public Page getListByPageByTime(String memberId, Integer mark,String itemName, Integer pager, Integer pagerSize) {
		return xcheckReportDao.getListByPageByTime(memberId,mark,itemName,pager,pagerSize);
	}

	@Override
	public Page newGetMemberItemRecordByTime(String memberId,Long itemMark,Integer testMark,Integer pager, Integer pagerSize) {
//		Page<XcheckReportRefItem> page=new Page<XcheckReportRefItem>(pagerSize);
//		page.setPageNo(pager);
		Page pages=getListByPageByTime(memberId,testMark,"id",pager,pagerSize);
		String ids=pages.getResult().toString();
		ids=ids.substring(1, ids.length()-1);
			if(ids!=null && !("").equals(ids)){
			String hql="select testValue,createDate from XcheckReportRefItem where reportId in ("+ids+") and itemId="+itemMark+" order by createDate desc";
			List<Object> lits=xcheckReportRefItemDao.find(hql);
			if(itemMark.intValue()==1){//脉率进行四舍五入
				for(Object obj:lits){
					Object[] array=(Object[])obj;
					String pulse= (String)array[0];//取出脉率
					array[0]=Math.round(Double.valueOf(pulse));
				}
			}
			pages.setResult(lits);
		}
		return pages;
	}

	@Override
	public Page<XcheckReport> getListByDate(String memberId, Integer mark,String year,String month,Integer pager, Integer pagerSize) {
		return xcheckReportDao.getListByPageByDate(memberId, mark, year,month,pager, pagerSize);
	}
	@Override
	public List<XcheckReport> getListGroupByDate(String memberId, String startDate,
			String endDate) {
		String hql="from XcheckReport where memberId='"+memberId+"' and createDate>='"+startDate+"' and createDate<='"+endDate+"' order by createDate asc";
		return xcheckReportDao.find(hql);
	}

	@Override
	public Integer getCountGroupByDate(String memberId, String startDate,
			String endDate) {
		String hql="select count(*) from XcheckReport where memberId='"+memberId+"' and createDate>='"+startDate+"' and createDate<='"+endDate+"'";
		List<Object> list=xcheckReportDao.find(hql);
		if(list!=null && list.size()>0){
			return Integer.parseInt(list.get(0)+"");
		}
		return 0;
	}
	
	/**
	 * 
	 * 组装功能项异常出现次数据
	 * 
	 * @return
	 */
	private Map<BloodCategory,Integer> constructErrorTimes(List<Map<String,Integer>> errorList){
		Map<BloodCategory,Integer> errorMap=new HashMap<BloodCategory, Integer>();
		for(Map<String,Integer> map:errorList){//数据库的alias是这个
			Integer cateId=map.get("cateId");
			Integer errorTimes=map.get("times");
			errorMap.put(BloodCategory.getById(cateId),errorTimes);
		}
		return errorMap;
	}
	
}
