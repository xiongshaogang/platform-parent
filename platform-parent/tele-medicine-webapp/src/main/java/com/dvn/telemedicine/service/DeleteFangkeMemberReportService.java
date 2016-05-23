package com.dvn.telemedicine.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.dao.x.XCheckReportJsonResultDao;
import com.dvn.telemedicine.dao.x.XcheckReportDao;
import com.dvn.telemedicine.dao.x.XcheckReportRefItemDao;
import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;
import com.dvn.telemedicine.entity.x.Xmember;
import com.dvn.telemedicine.util.RequestData;

@Component
public class DeleteFangkeMemberReportService {

	private static Logger logger = Logger.getLogger(DeleteFangkeMemberReportService.class);
	@Autowired
	private XcheckReportDao xcheckReportDao;
	@Autowired
	private XcheckReportRefItemDao xcheckReportRefItemDao; 
	@Autowired
	private XCheckReportJsonResultDao xCheckReportJsonResultDao;
	
	public void deleteFangkeReport() throws HttpException, IOException {
		try {
			handlerDownLoadVideos();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void handlerDownLoadVideos() {
		logger.info("------------------------删除访客成员的报告   开始----------------------");
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //得到前一天
		String date = ft.format(calendar.getTime());
		//去报告表用分组查看 得到做检测的所有成员
		String hql="from XcheckReport where createDate like '%"+date+"%' group by memberId";
		List<XcheckReport> list=xcheckReportDao.find(hql);
		for(XcheckReport rep:list){
			Xmember xm=RequestData.getPayUserId(rep.getMemberId());//获取成员详细信息
			if(xm.getIsTemporary()==1){//访客用户 删除数据
				String reps="from XcheckReport where memberId='"+rep.getMemberId()+"'";
				List<XcheckReport> rlist=xcheckReportDao.find(reps);
				for(XcheckReport report:rlist){
					//开始删除报告表及其他表里面的数据
					//删除报告记录
					xcheckReportDao.delete(report.getId());
					//删除24项
					String items="from XcheckReportRefItem where reportId="+report.getId();
					List<XcheckReportRefItem> ilist=xcheckReportRefItemDao.find(items);
					for(XcheckReportRefItem item:ilist){
						xcheckReportRefItemDao.delete(item.getId());
					}
					//删除json结果数据
					
					//删除模块分数数据
					
					//删除功能项分数数据
					
				}
			}
		}
		logger.info("------------------------删除访客成员的报告   结束----------------------");
	}
	
}
