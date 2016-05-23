package cn.com.tcxy.model;

import java.io.Serializable;

import cn.com.tcxy.model.base.BaseMailLog;

public class MailLog extends BaseMailLog implements Serializable {

    private static final long serialVersionUID = 3667433151362226827L;
    
    public static final int		MAIL_SEND_SUCCESS	= 0;

	/**
	 * 1
	 */
	public static final int		MAIL_GENE_FAILED	= 1;

	/**
	 * 3
	 */
	public static final int		MAIL_SEND_ERROR		= 3;

}