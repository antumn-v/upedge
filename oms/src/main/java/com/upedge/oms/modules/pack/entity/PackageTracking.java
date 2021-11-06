package com.upedge.oms.modules.pack.entity;

import com.upedge.common.utils.DateUtils;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageTracking{

	/**
	 * 
	 */
    private String trackNumber;
	/**
	 * 
	 */
    private Integer transportId;

    private String transportName;
	/**
	 * 
	 */
    private String trackingMoreCode;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date onlineTime;
	/**
	 * 
	 */
    private Date signedTime;
	/**
	 * 
	 */
    private Date shipTime;
	/**
	 * 
	 */
    private String trackStatus;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Integer orderSourceId;
	/**
	 * 
	 */
    private String orderSourceName;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String clientOrderCode;
	/**
	 *  目的地
	 */
    private String destination;


	/**
	 * 开始时间 搜索使用  不存在数据库映射
	 */
	private String shipDateStart;


	/**
	 * 结束时间   搜索使用  不存在数据库映射
	 */
	private String shipDateEnd;

	public void setShipDateEnd(String shipDateEnd) {
		if (StringUtils.isNotBlank(shipDateEnd)){
			shipDateEnd = DateUtils.formatDateTime(DateUtils.getOfDayLast(DateUtils.parseDate(shipDateEnd)));
		}
		this.shipDateEnd = shipDateEnd;
	}

	public static PackageTracking saiheTrackingToPackageTracking(ApiPackageInfo apiPackageInfo,PackageInfo packageInfo){
		PackageTracking packageTracking = new PackageTracking();
		packageTracking.setDestination(apiPackageInfo.getPackageCountry());
		packageTracking.setTrackNumber(packageInfo.getTrackNumber());
		packageTracking.setTransportId(packageInfo.getTransportId());
		packageTracking.setTransportName(packageInfo.getTransportName());
		packageTracking.setState(0);
		packageTracking.setShipTime(packageInfo.getShipTime());
		packageTracking.setUpdateTime(new Date());
		return packageTracking;
	}


}
