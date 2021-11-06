package com.upedge.pms.modules.quote.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class QuoteApply{

	public static int STATE_INIT = 0;
	public static int STATE_PROCESSING = 1;
	public static int STATE_FINISHED = 2;

	public static int NOT_QUOTED = 0;
	public static int PART_QUOTED = 1;
	public static int ALL_QUOTED = 2;


	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long applyUserId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long handleUserId;
	/**
	 * 
	 */
    private Long relateOrderId;
	/**
	 * 0=待认领，1=处理中，2=已完成
	 */
    private Integer quoteState;
	/**
	 * 0=未报价，1=全部报价，2=部分报价
	 */
    private Integer quoteType;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * 
	 */
    private String feedback;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
