package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserPaypalStore{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private Long paypalAccountId;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Integer uploadTrackingInfo;
	/**
	 * 
	 */
    private Integer notifyBuyer;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 0=未上传，1=上传中，2=上传完成
	 */
    private Integer uploadState;
	/**
	 * 
	 */
    private Date lastUploadTime;

}
