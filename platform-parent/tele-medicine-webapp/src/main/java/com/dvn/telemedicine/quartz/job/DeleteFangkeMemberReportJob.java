package com.dvn.telemedicine.quartz.job;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.service.DeleteFangkeMemberReportService;

@Component
public class DeleteFangkeMemberReportJob extends TransactionalQuartzTask {
	@Autowired
	private DeleteFangkeMemberReportService deleteFangkeMemberReportService;

	public DeleteFangkeMemberReportService getDeleteFangkeMemberReportService() {
		return deleteFangkeMemberReportService;
	}
	public void setDeleteFangkeMemberReportService(
			DeleteFangkeMemberReportService deleteFangkeMemberReportService) {
		this.deleteFangkeMemberReportService = deleteFangkeMemberReportService;
	}
	@Override
	protected void executeTransactional(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("###----DeleteFangkeMemberReport start----###");
		try {
			deleteFangkeMemberReportService.deleteFangkeReport();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("###----DeleteFangkeMemberReport end----###");
		
	}
}