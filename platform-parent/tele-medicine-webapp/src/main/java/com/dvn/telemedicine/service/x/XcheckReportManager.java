package com.dvn.telemedicine.service.x;

import java.util.List;
import java.util.Map;

import org.springside.modules.orm.Page;

import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.Xmember;

public interface XcheckReportManager {
	/**
	 * 查询成员的报告记录
	 * @param memberId
	 * @param pager
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XcheckReport> getList(String memberId,Integer mark,Integer pager,Integer pagerSize);
	
	
	/**
	 * 查询成员的报告记录
	 * @param memberId
	 * @param pager
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XcheckReport> getListByDate(String memberId,Integer mark,String year,String month,Integer pager,Integer pagerSize);
	
	
	/**
	 * 查询成员30分钟内的记录
	 * @param memberId
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public List<XcheckReport> getList(String memberId,Integer pager,Integer pagerSize);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public XcheckReport getById(Long id);
	/**
	 * 入库检测结果
	 * @param resultJson
	 * @return
	 */
	//public XcheckReport saveReport(String memberId,String resultJson) throws Exception;
	
	public Map<String,Object> saveReport(Xmember member,String resultJson,String version) throws Exception;
	/**
	 * 根据性别分组获取所有的成员报告
	 * @param sex
	 * @return
	 */
	public List<XcheckReport> getList(Long sex);
	/**
	 * 获取成员的最新一天记录
	 * @param memberId
	 * @return
	 */
	public XcheckReport getXcheckReport(String memberId);
	/**
	 * 根据时间获取成员的报告记录
	 * @param memberId
	 * @param date
	 * @return
	 */
	public List<XcheckReport> getListByDate(String memberId,String date);
	/**
	 * 根据时间段获取成员的报告记录
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<XcheckReport> getListByDate(String memberId,String startDate,String endDate);
	/**
	 * 获取成员性别
	 * @param memberId
	 * @return
	 */
	public String getSex(String memberId);
	/**
	 * 更新报告记录
	 * @param rep
	 */
	public void save(XcheckReport rep);
	/**
	 * 获取成员今天（到当前时间）的项平均值
	 * @param memberId
	 * @param itemName
	 * @return
	 */
	public String getItemAvg(String memberId,String itemName);
	/**
	 * 根据日期查询成员的检测次数
	 */
	public int getCountByDate(String memberId,String date);
	/**
	 * 获取成员本次报告的前一次记录
	 * @param memberId
	 * @param reportId
	 * @return
	 */
	public XcheckReport getXcheckReport(String memberId,Long reportId);
	/**
	 * 根据日期获取成员最后一条记录
	 * @param memberId
	 * @param date
	 * @return
	 */
	public XcheckReport getXcheckReportByDate(String memberId,String date);
	/**
	 * 获取成员的报告总个数
	 * @param memberId
	 * @return
	 */
	public int getMemberReportCount(String memberId);
	/**
	 * 获取成员最早一条记录
	 * @param memberId
	 * @return
	 */
	public XcheckReport getXcheckReportOrderByDate(String memberId);
	
	
	/**
	 * 查询会员固定数单位检测项健康档案 （按次）  新接口
	 * 检测项对应定义itemMark
	 * 1 脉率，2 左室顺应性，3 心舒末容量，4 心肌收缩力，5 心缩时间间期 ，6 射血分数 ，7 每博血量
	 * 8 心输出量，9 心脏指数，10 心缩末血量，11 心肌耗氧量，12 左室射血压，13 心内膜供血率，14 脉搏速度
	 * 15 外周动脉顺应性，16 平均脉压，17 平均动脉压，18 左室射血阻力，19 总外周阻力，20 血液粘滞阻力
	 * 21 动脉血容量， 22 血容量指数，23 平均循环时间，24 综合反射系数，25 脉率指数
	 * 26 舒张压，27 收缩压
	 */
	public Page<Object> newGetMemberItemRecordByTime(String memberId,Long itemMark,Integer testMark,Integer pager,Integer pagerSize);
	/**
	 * 查询报告具体字段
	 * @param memberId
	 * @param mark
	 * @param itemName
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<Object> getListByPageByTime(String memberId,Integer testMark,String itemName,Integer pager,Integer pagerSize);
	/**
	 * 根据时间段获取成员的报告记录每天的日期
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<XcheckReport> getListGroupByDate(String memberId,String startDate,String endDate);
	/**
	 * 查询时间段内成员检测的数量
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getCountGroupByDate(String memberId,String startDate,String endDate);
}
