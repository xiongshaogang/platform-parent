package com.dvn.telemedicine.util;
public class AppKeys {
	static {  
	    System.setProperty("org.apache.commons.logging.Log",  
	            "org.apache.commons.logging.impl.SimpleLog");  
	    System.setProperty("org.apache.commons.logging.simplelog.showdatetime",  
	            "true");  
	    System.setProperty("org.apache.commons.logging"  
	            + ".simplelog.log.org.apache.commons.httpclient", "error");  
	}  
	// 任务状态
	public static final int TASK_STATUS_PUBLISH = 1;
	// 手机 登录标示
	public static final String MOBILE_LOGIN_KEY = "renzheng_code";
	public static final String MOBILE_LOGIN_VALUE = "mobile_yjw";// @加设备码

	public static final String MOBILE_LOGIN_EQUIPMENT_CODE_KEY = "equipment_code";

	public static final String MOBILE_LOGIN_MARK_KEY = "openid";
	public static final String TELEMEDICINE_STATE_YES = "已计算";
	public static final String TELEMEDICINE_STATE_NO = "未计算";

	// 视频类别默认值
	public static final String VIDEOTYPE_TYPE_DEF = "video_type";
	public static final long VIDEOTYPE_PARENTID_DEF = 1;

	// 归档操作
	public static final String VIDEO_OPTION_INSERT = "i";
	public static final String VIDEO_OPTION_UPDATE = "u";

	// 发送ID、STATUS
	public static final String VIDEO_SEND_DOWNLOAD_URL = ReadPropertiesUtil.get("download_api_url");
	public static final String VIDEO_SEND_DOWNLOAD_DLCALLBACK_URL = ReadPropertiesUtil.get("download_api_dlcallback_url");
	public static final String HTTP_BACKSLASH = "/";
	public static final String STBOC_CODE = "stboxcode";
	public static final String USER_ID = "userId";
	public static final String OTHER_MEMBERID = "otherMemberId";
	public static final String MEMBER_ID = "memberId";
	public static final String  TICKET_ID = "ticketId";
	public static final String  DEVICE_MARK = "deviceMark";
	public static final String  LICENSE_ID = "licenseId";
	
	//数据源
	public static final String DATASOURCE1 = "telemedicine";
	public static final String DATASOURCE2 = "telemedicine2";
	//memcachedClient
	public static final String MEMCACHED_HOST = ReadPropertiesUtil.get("cache_ip");
	public static final Integer MEMCACHED_PORT = Integer.valueOf(ReadPropertiesUtil.get("cache_host"));
	public static final String MEMCACHED_DBMARK = "dbmark4telemedicine";
	
	//功能区分
	public static final String FUNCODE_DISEASE="disease";
	public static final String FUNCODE_VIDEOMARK="video_mark";
	
	//图片服务器地址（API）
	public static final String API_IMAGE_HOST = ReadPropertiesUtil.get("api_image_host");
	
	//视频显示标志
	public static final String VIDEO_SHOW="yes";
	public static final String VIDEO_HIDDEN="no";
	
	public static final String IS_USING="isUsing";//检测uc端是否可用的key
	public static final String IS_USING_YES="yes";//uc端可用
	public static final String IS_USING_NO="no";//uc端不可用
	
	//数据库同步
	public static final String REVERSEDB_SHELL_COMMAND=ReadPropertiesUtil.get("shell_command");
	public static final String REVERSEDB_DB_SERVER=ReadPropertiesUtil.get("db_service_url");
	
	//图片下载配置
	public static final String DOWNLOAD_SHELL_COMMAND=ReadPropertiesUtil.get("shell_command_downloadpic");//shell路径
	public static final String DOWNLOAD_PIC_SERVER=ReadPropertiesUtil.get("pic_service_url");//图片服务器地址
	public static final String DOWNLOAD_PIC_DIR=ReadPropertiesUtil.get("pic_base_dir");//本地图片保存路径
	
	//检测时 是否是空腹
	public static final Integer IS_LIMOSIS_YES=0;//空腹
	public static final Integer IS_LIMOSIS_NO=1;//非空腹
	
	//病的标识
	public static final String BLOOD_SUGAR="blood_sugar";//	血糖
	public static final String BLOOD_PRESSURE="blood_pressure";//血压
	public static final String IS_HIGH_BLOOD_PRESSURE="is_high_blood_pressure";//是否是高血压
	public static final String IS_DIABETES="is_diabetes";//是否是糖尿病
	public static final String HEIGHT="height";//身高
	public static final String WEIGHT="weight";//体重
	public static final String AGE="age";//年龄
	public static final String SEX="sex";//性别
	//性别
	public static final int SEX_MAN=1;//男
	public static final int SEX_WOMAN=0;//女
	
	//题目序号
	public static final Integer HEIGHT_NUMBER=0;//身高题目的序号
	
	//PDF报表文件生成路径
	public static final String REPORT_FILE_DIR = "src/main/webapp/healthReport/report";
	
	//排名项
	public static final String PRESS_RANK="press";//血压
	public static final String ITEM_RANK="item";//心脉指数
	public static final String HEALTH_RANK="health";//健康指数
	//血压排名等级
	public static final String PRESS_RANK_GOOD="健康";//
	public static final String PRESS_RANK_MEDIUM="一般";//
	public static final String PRESS_RANK_INFERIOR="不佳";//
	//心脉指数，健康指数排名等级
	public static final String ITEM_HEALTH_RANK_GOOD="优秀";//
	public static final String ITEM_HEALTH_RANK_MEDIUM="良好";//
	public static final String ITEM_HEALTH_RANK_INFERIOR="一般";//
	
	public static final String PRESSURE_VALUE="血压";//
	public static final String LOW_PRESSURE_VALUE="舒张压";//
	public static final String HIGH_PRESSURE_VALUE="收缩压";//
	public static final String PLUSE="脉搏";//
	public static final String ITEM_SCORE="心脉指数";//
	
	//心脉总值的等级
	public static final String SCORE_GOOD="0";//
	public static final String SCORE_MEDIUM="1";//
	public static final String SCORE_INFERIOR="2";//
	
	public static final String PR_WRITE_GOOD="脉率未见明显异常";//
	public static final String PR_WRITE_INFERIOR="<60且>=40心动过缓；>100且=<150心动过速";//
	public static final String PR_COPYWRITER_GOOD="我们在安静时60～100次/分钟的心率都属正常范围，但最佳心率是在60次/分钟左右。有不少研究提示，心跳较慢的人较长寿。经常参加运动，不要过于肥胖，并且戒烟戒酒会帮助您减慢心率，健康长寿。";//
	public static final String PR_COPYWRITER_INFERIOR="健康人在安静状态下的心率为60～100次/分钟，心率异常有生理性和病理性的各种原因，病理性是需要治疗的。";//
	public static final String PR_DEFINE="每分钟脉搏的次数，正常情况下与心率一致";//
	
	//
	public static final String TIME_DAY="day";//日间
	public static final String TIME_NIGHT="night";//夜间
	public static final String TIME_MORNING="morning";//清晨
	//清晨，晚间，日间血压文案
	public static final String TIME_DAY_WRITER="您的测量次数过低，建议坚持每日血压测量";//
	public static final String TIME_NIGHT_WRITER="本周测量次数过低，建议参考动态血压测量指南坚持晚间血压测量";//
	public static final String TIME_MORNING_WRITER="本周测量次数过低，建议参考动态血压测量指南坚持晨间血压测量";//
	public static final String XUEYA_WRITER="您的血压控制良好，要保持哦！";//
	
	public static final String WRITER_GOOD="未见明显异常";//
}
