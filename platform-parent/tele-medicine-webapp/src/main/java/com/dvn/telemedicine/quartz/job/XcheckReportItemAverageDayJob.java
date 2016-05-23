package com.dvn.telemedicine.quartz.job;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.service.XcheckReportItemAverageDayService;

@Component
public class XcheckReportItemAverageDayJob extends TransactionalQuartzTask {
	@Autowired
	private XcheckReportItemAverageDayService xcheckReportItemAverageDayService;
	
	public XcheckReportItemAverageDayService getXcheckReportItemAverageDayService() {
		return xcheckReportItemAverageDayService;
	}

	public void setXcheckReportItemAverageDayService(
			XcheckReportItemAverageDayService xcheckReportItemAverageDayService) {
		this.xcheckReportItemAverageDayService = xcheckReportItemAverageDayService;
	}


	@Override
	protected void executeTransactional(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("###----XcheckReportItemAverageDay start----###");
		try {
			xcheckReportItemAverageDayService.doItemAverageDay();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("###----XcheckReportItemAverageDay end----###");
		
	}
}