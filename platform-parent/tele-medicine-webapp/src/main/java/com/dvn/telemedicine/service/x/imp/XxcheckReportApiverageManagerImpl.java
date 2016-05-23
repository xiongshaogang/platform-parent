package com.dvn.telemedicine.service.x.imp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.core.OcsException;
import com.dvn.telemedicine.core.TelemedicineAnalyzer;
import com.dvn.telemedicine.core.WriterNotFoundException;
import com.dvn.telemedicine.core.avgbp.entity.AvgBpEntity;
import com.dvn.telemedicine.core.avgbp.entity.AvgBpResult;
import com.dvn.telemedicine.dao.x.XxcheckReportApiverageDao;
import com.dvn.telemedicine.dao.x.XxcheckReportApiverageGroupDao;
import com.dvn.telemedicine.dao.x.XxcheckReportBloodWeekAvgDao;
import com.dvn.telemedicine.dao.x.XxcheckReportBloodWeekDataDao;
import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XxcheckReportApiverage;
import com.dvn.telemedicine.entity.x.XxcheckReportApiverageGroup;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekAvg;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekData;
import com.dvn.telemedicine.service.x.XcheckReportManager;
import com.dvn.telemedicine.service.x.XxcheckReportApiverageManager;
import com.dvn.telemedicine.util.AppKeys;

@Component
@Transactional
public class XxcheckReportApiverageManagerImpl implements XxcheckReportApiverageManager {
	
	private static Logger logger = LoggerFactory.getLogger(XxcheckReportApiverageManagerImpl.class);

	@Autowired
	private XxcheckReportApiverageDao xxcheckReportApiverageDao;
	@Autowired
	private XxcheckReportApiverageGroupDao xxcheckReportApiverageGroupDao;
	@Autowired
	private XcheckReportManager xcheckReportManager;
	@Autowired
	private XxcheckReportBloodWeekAvgDao xxcheckReportBloodWeekAvgDao;
	@Autowired
	private XxcheckReportBloodWeekDataDao xxcheckReportBloodWeekDataDao;
	@Autowired
	private TelemedicineAnalyzer telemedicineAnalyzer;
	
	private ObjectMapper maper = new ObjectMapper();
	
	/**
	 * [{\"memberId\":\"12_1\",\"pulse\":\"23\",\"bloodPressure\":\"12,45\",\"createDate\":\"2014-12-13 12:34:01\"},{\"memberId\":\"12_2\",\"pulse\":\"23\",\"bloodPressure\":\"12,45\",\"createDate\":\"2014-12-13 12:34:01\"}]
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Long saveReportApiverage(String memberId, String resultJson){
		XxcheckReportApiverageGroup group=new XxcheckReportApiverageGroup();
		try {
			AvgBpResult result=telemedicineAnalyzer.analyzeAverageBloodPressure(resultJson);
			group.setMemberId(memberId);
			group.setApiverage24hBloodPressure(result.getApiverage24hBloodPressure());
			group.setApiverageDaytimeBloodPressure(result.getApiverageDaytimeBloodPressure());
			group.setApiverageNightBloodPressure(result.getApiverageNightBloodPressure());
			group.setBloodPressureDifferentCoefficient(result.getBloodPressureDifferentCoefficient());
			group.setContent(result.getContent());
			group.setCreateDate(result.getCreateDate());
			group.setDaytimeBloodPressureDifferentCoefficient(result.getDaytimeBloodPressureDifferentCoefficient());
			group.setDaytimeBloodPressureLoadValue(result.getDaytimeBloodPressureLoadValue());
			group.setMorningBloodPressure(result.getMorningBloodPressure());
			group.setNightBloodPressureDeclineRate(result.getNightBloodPressureDeclineRate());
			group.setNightBloodPressureDifferentCoefficient(result.getNightBloodPressureDifferentCoefficient());
			group.setNightBloodPressureLoadValue(result.getNightBloodPressureLoadValue());
			xxcheckReportApiverageGroupDao.save(group);//放入平均脉压主表数据
			Long id=group.getId();
			List<AvgBpEntity> bpRecords= result.getBpRecords();
			for(AvgBpEntity record:bpRecords){
				XxcheckReportApiverage info=new XxcheckReportApiverage();
				info.setBloodPressure(record.getBloodPressure());
				info.setCreateDate(record.getCreateDate());
				info.setGroupId(id);
				info.setMemberId(record.getMemberId());
				info.setPulse(record.getPulse());
				xxcheckReportApiverageDao.save(info);
			}
			return id;
			
		} catch (OcsException e) {
			logger.error("memcache访问异常",e);
		} catch (WriterNotFoundException e) {
			logger.error("文案读取异常",e);
		} catch (Exception e){
			logger.error("内部错误",e);
		}
		return null;
		
	}
	//获取系数     参数（list数据集合，动脉压均值，舒张压均值，收缩压均值）
	private String getData(List<XxcheckReportApiverage> list,Double avg,Double lowAvg,Double highAvg){
		//首先获取整天的变异系数，然后是舒张压，收缩压的变异系数
		//第一步先算出整组数据的平均动脉压和舒张压收缩压的标准差
		List<Double> dlist=new ArrayList<Double>();
		Double lowbz=0.0;
		Double highbz=0.0;
		for(XxcheckReportApiverage ge:list){
			String[] bp=ge.getBloodPressure().split(",");
			Double low=Double.valueOf(bp[0]);
       	 	Double high=Double.valueOf(bp[1]);
			Double map=((high+2*low)/3);
			dlist.add(map);
			
			lowbz+=Math.pow((low-lowAvg),2);
			highbz+=Math.pow((high-highAvg),2);
		}
		//计算舒张压和收缩压的标准差
		Double lowsd=Math.sqrt(lowbz/list.size());
		Double highsd=Math.sqrt(highbz/list.size());
		Double lowxs=lowsd/lowAvg;
		Double highxs=highsd/highAvg;
		//计算平均动脉压的标准差SD
		Double bz=0.0;
		for(Double map:dlist){
			bz+=Math.pow((map-avg),2);
		}
		Double sd=Math.sqrt(bz/dlist.size());
		Double xs=sd/avg;
		return getValue(lowxs,3)+","+getValue(highxs,3)+","+getValue(xs,3);
	}
	private Double getValue(Double value,int index){
		Double ss=new BigDecimal(value).setScale(index, BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入取整
		return ss;
	}
	private int getValue(Double value){
		int ss=new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入取整
		return ss;
	}
	@Override
	public Page<XxcheckReportApiverage> getReportApiveragePage(String memberId,String startDate, String endDate, Integer pager, Integer pagerSize) {
		return xxcheckReportApiverageDao.getListByPage(memberId,startDate,endDate,pager,pagerSize);
	}
    
	public static void main(String[] args){
		int a=-2;
		int b=-2;
		System.out.println(a*b);
	}
	@Override
	public List<XxcheckReportApiverage> getListByGroupId(Long groupId) {
		String hql="from XxcheckReportApiverage where groupId="+groupId+" order by createDate asc";
		return xxcheckReportApiverageDao.find(hql);
	}
	@Override
	public Long getReportApiverageDate(String memberId,List<String> list,String date,String name){
        try {
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
             if(list!=null && list.size()>0){
            	 //日间
            	 XxcheckReportBloodWeekAvg w=new XxcheckReportBloodWeekAvg();
            	 w.setDate(date);
            	 w.setMemberId(memberId);
            	 w.setTime(AppKeys.TIME_DAY);
            	 w.setCreateDate(new Date());
            	 xxcheckReportBloodWeekAvgDao.save(w);
            	 //夜间
            	 XxcheckReportBloodWeekAvg wn=new XxcheckReportBloodWeekAvg();
            	 wn.setDate(date);
            	 wn.setMemberId(memberId);
            	 wn.setTime(AppKeys.TIME_NIGHT);
            	 wn.setCreateDate(new Date());
            	 xxcheckReportBloodWeekAvgDao.save(wn);
            	 //清晨
            	 XxcheckReportBloodWeekAvg wd=new XxcheckReportBloodWeekAvg();
            	 wd.setDate(date);
            	 wd.setMemberId(memberId);
            	 wd.setTime(AppKeys.TIME_MORNING);
            	 wd.setCreateDate(new Date());
            	 xxcheckReportBloodWeekAvgDao.save(wd);
            	 
            	 Double wlsum=0.0;
            	 Double wdsum=0.0;
            	 Double wdlsum=0.0;
            	 Double wdhsum=0.0;
            	 Double wnlsum=0.0;
            	 Double wnhsum=0.0;
            	 int wdindex=0;
            	 int wnindex=0;
            	 int wsize=0;
	             for(String rr:list){
	            	 List<XcheckReport> rlist=xcheckReportManager.getListByDate(memberId, rr);
		             if(rlist!=null && rlist.size()>0){
		            	 Double lsum=0.0;
		            	 Double hsum=0.0;
		            	 Double dlsum=0.0;
		            	 Double dhsum=0.0;
		            	 Double nlsum=0.0;
		            	 Double nhsum=0.0;
		            	 int dindex=0;
		            	 int nindex=0;
		            	 int size=rlist.size();
		            	 for(XcheckReport rep:rlist){
		            		 String[] bp=rep.getBloodPressure().split(",");
			            	 Double low=Double.valueOf(bp[0]);
			            	 Double high=Double.valueOf(bp[1]);
			            	 lsum+=low;
			            	 hsum+=high;
		            	     int h=rep.getCreateDate().getHours();
		            	     if(h>=6 && h<9){//日间
		            	    	 dlsum+=low;
		            	    	 dhsum+=high;
		            	    	 dindex++;
		            	    	 
		            	    	 wdlsum+=low;
				            	 wdhsum+=high;
				            	 wdindex++;
		            	     }else if(h>=18 && h<21){//夜间
		            	    	 nlsum+=low;
		            	    	 nhsum+=high;
		            	    	 nindex++;
		            	    	 
		            	    	 wnlsum+=low;
				            	 wnhsum+=high;
				            	 wnindex++;
		            	     }
		            	     wlsum+=low;
			            	 wdsum+=high;
		            	     wsize++;
		            	 }
		            	 //天平均值
		            	 Double lavg=lsum/size;//这一天舒张压的平均值
		            	 Double havg=hsum/size;//这一天收缩压的平均值
		            	 Double dlavg=0.0;
		            	 Double dhavg=0.0;
		            	 if(dindex>0){
		            		 dlavg=dlsum/dindex;
		            		 dhavg=dhsum/dindex;
		            		 
			            	 XxcheckReportBloodWeekData data1=new XxcheckReportBloodWeekData();
			            	 data1.setWeekId(wd.getId());
			            	 data1.setDate(sdf.parse(rr));
			            	 data1.setBloodAvg(getValue(dlavg)+","+getValue(dhavg));
			            	 data1.setCreateDate(new Date());
			            	 xxcheckReportBloodWeekDataDao.save(data1);
		            	 }
		            	 Double nlavg=0.0;
		            	 Double nhavg=0.0;
		            	 if(nindex>0){
		            		 nlavg=nlsum/nindex;
			            	 nhavg=nhsum/nindex;
			            	 
			            	 XxcheckReportBloodWeekData data2=new XxcheckReportBloodWeekData();
			            	 data2.setWeekId(wn.getId());
			            	 data2.setDate(sdf.parse(rr));
			            	 data2.setBloodAvg(getValue(nlavg)+","+getValue(nhavg));
			            	 data2.setCreateDate(new Date());
			            	 xxcheckReportBloodWeekDataDao.save(data2);
		            	 }
		            	 
		            	 XxcheckReportBloodWeekData data=new XxcheckReportBloodWeekData();
		            	 data.setWeekId(w.getId());
		            	 data.setDate(sdf.parse(rr));
		            	 data.setBloodAvg(getValue(lavg)+","+getValue(havg));
		            	 data.setCreateDate(new Date());
		            	 xxcheckReportBloodWeekDataDao.save(data);
		             }
	             }
	             //周平均值
	             Double wlavg=0.0;
	             Double wdavg=0.0;
	             if(wsize>0){
	            	 wlavg=wlsum/wsize;
		             wdavg=wdsum/wsize;
	             }
	             Double wdlavg=0.0;
	             Double wdhavg=0.0;
	             if(wdindex>0){
	            	 wdlavg=wdlsum/wdindex;
	            	 wdhavg=wdhsum/wdindex;
	             }
	             Double wnlavg=0.0;
            	 Double wnhavg=0.0;
            	 if(wnindex>0){
	            	 wnlavg=wnlsum/wnindex;
	            	 wnhavg=wnhsum/wnindex;
            	 }
            	 
            	 w.setBloodAvg(getValue(wlavg)+","+getValue(wdavg));
            	 xxcheckReportBloodWeekAvgDao.save(w);
            	 wd.setBloodAvg(getValue(wdlavg)+","+getValue(wdhavg));
            	 xxcheckReportBloodWeekAvgDao.save(wd);
            	 wn.setBloodAvg(getValue(wnlavg)+","+getValue(wnhavg));
            	 xxcheckReportBloodWeekAvgDao.save(wn);
            	 if(name.equals(AppKeys.TIME_DAY)){
            		 return w.getId();
            	 }else if(name.equals(AppKeys.TIME_NIGHT)){
            		 return wn.getId();
            	 }else if(name.equals(AppKeys.TIME_MORNING)){
            		 return wd.getId();
            	 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         } 
         return null;
	}	
}
