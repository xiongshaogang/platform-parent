package com.dvn.telemedicine.service.xd.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.core.OcsException;
import com.dvn.telemedicine.core.TeleMember;
import com.dvn.telemedicine.core.TelemedicineAnalyzer;
import com.dvn.telemedicine.core.WriterNotFoundException;
import com.dvn.telemedicine.core.ecg.ECGRequestData;
import com.dvn.telemedicine.core.ecg.ECGResult;
import com.dvn.telemedicine.core.ecg.EcgIndex;
import com.dvn.telemedicine.dao.xd.XdcheckReportDao;
import com.dvn.telemedicine.dao.xd.XdcheckReportRefItemDao;
import com.dvn.telemedicine.dao.xd.XdcheckReportWaveshapeDao;
import com.dvn.telemedicine.entity.x.Xmember;
import com.dvn.telemedicine.entity.xd.XdcheckReport;
import com.dvn.telemedicine.entity.xd.XdcheckReportRefItem;
import com.dvn.telemedicine.entity.xd.XdcheckReportWaveshape;
import com.dvn.telemedicine.service.xd.XdcheckReportManager;
import com.dvn.telemedicine.util.RequestData;

@Component
@Transactional
public class XdcheckReportManagerImpl implements XdcheckReportManager {
    
	@Autowired
	private XdcheckReportDao xdcheckReportDao;
	@Autowired
	private XdcheckReportRefItemDao xdcheckReportRefItemDao;
	@Autowired
	private XdcheckReportWaveshapeDao xdcheckReportWaveshapeDao;
	@Autowired
	private TelemedicineAnalyzer telemedicineAnalyzer;

	@Override
	public XdcheckReport saveXdcheckReport(String memberId, String wh,Long pulse,Integer isSports) throws OcsException, WriterNotFoundException {
		Xmember member=RequestData.getPayUserId(memberId);
		TeleMember teleMember=new TeleMember(member.getId(),member.getAge().intValue(),member.getHeight().intValue()
				,member.getWeight().intValue(),member.getSex().intValue());
		ECGRequestData requestData=new ECGRequestData();//组装请求数据
		requestData.setPulse(pulse);
		requestData.setWh(wh);
		requestData.setMember(teleMember);
		ECGResult result=telemedicineAnalyzer.analyzeECGScore(requestData);
		XdcheckReport report=new XdcheckReport();//放入主表
		report.setCreateDate(new Date());
		report.setErrorCodes(result.getErrorCodes());
		report.setIsSports(isSports);
		report.setItemFeature(result.getItemFeature());
		//report.setPulse(pulse);
		report.setPulse(result.getPulse().longValue());
		report.setWriterIds(result.getWriterIds());
		report.setMemberId(memberId);
		xdcheckReportDao.save(report);//存入数据库
		Long reportId=report.getId();
		Map<EcgIndex,Double> itemValues= result.getItemValues();
		for(EcgIndex item:EcgIndex.values()){
			XdcheckReportRefItem info=new XdcheckReportRefItem();
			info.setReportId(reportId);
			info.setItemId((long)item.id);
			info.setCreateDate(new Date());
			info.setTestValue(itemValues.get(item)+"");
			xdcheckReportRefItemDao.save(info);//存入心电检测项表
		}
		//存入波形数据
		XdcheckReportWaveshape waveshap=new XdcheckReportWaveshape();
		waveshap.setInWh(wh);
		waveshap.setOutWh(result.getOutWh());
		waveshap.setReportId(reportId);
		waveshap.setCreateDate(new Date());
		xdcheckReportWaveshapeDao.save(waveshap);
		
		return report; 
	}

	@Override
	public XdcheckReport getByReportId(Long reportId) {
		return xdcheckReportDao.findUniqueBy("id", reportId);
	}
	@Override
	public Page<XdcheckReport> getListByMemberId(String memberId, String year,
			String month, int pager, int pagerSize) {
		return xdcheckReportDao.getListByPage(memberId, year, month, pager, pagerSize);
	}
}
