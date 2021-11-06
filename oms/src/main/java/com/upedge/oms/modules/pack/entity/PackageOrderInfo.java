package com.upedge.oms.modules.pack.entity;

import com.upedge.common.utils.DateTools;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiOrderSource;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageOrderInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageOrderInfo{

	/**
	 * 
	 */
    private Integer packageId;
	/**
	 * 
	 */
    private String orderCode;
	/**
	 * 
	 */
    private String clientOrderCode;
	/**
	 * 
	 */
    private Date payTime;
	/**
	 * 
	 */
    private String firstName;
	/**
	 * 
	 */
    private String lastName;
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
    private Integer orderSourceType;
	/**
	 * 
	 */
    private Date shipDate;
	/**
	 * 支付到发货的处理时长
	 */
    private Long handleTime;
	/**
	 * 
	 */
    private Long customerId;

    private String managerCode;
	/**
	 * 从包裹生成到发货的处理时长
	 */
    private Long packageShipDuration;
	/**
	 * 从支付到包裹生成的处理时长
	 */
    private Long payPackageDuration;


	private String trackNumber;


	/**
	 * 开始时间 搜索使用
	 */
	private String shipDateStart;

	/**
	 * 结束时间   搜索使用
	 */
	private String shipDateEnd;


	public static PackageOrderInfo saihePackageOrderToAppPackageOrder(ApiPackageOrderInfo apiPackageOrderInfo, PackageInfo packageInfo, SaiheOrderRecord saiheOrderRecord){
		PackageOrderInfo packageOrderInfo=new PackageOrderInfo();
		packageOrderInfo.setPackageId(packageInfo.getPackageId());
		packageOrderInfo.setOrderCode(apiPackageOrderInfo.getOrderCode());
		packageOrderInfo.setClientOrderCode(apiPackageOrderInfo.getClientOrderCode());

		packageOrderInfo.setPayTime(apiPackageOrderInfo.getPayTime());
		packageOrderInfo.setFirstName(apiPackageOrderInfo.getFirstName());
		packageOrderInfo.setLastName(apiPackageOrderInfo.getLastName());
		packageOrderInfo.setShipDate(packageInfo.getShipDate());
		packageOrderInfo.setTrackNumber(packageInfo.getTrackNumber());
		ApiOrderSource apiOrderSource=apiPackageOrderInfo.getOrderSource();
		if(apiOrderSource!=null){
			packageOrderInfo.setOrderSourceId(apiOrderSource.getID());
			packageOrderInfo.setOrderSourceName(apiOrderSource.getOrderSourceName());
			packageOrderInfo.setOrderSourceType(apiOrderSource.getOrderSourceType());

		}
		//设置从支付发货的处理时长
		if(apiPackageOrderInfo.getPayTime()!=null) {
			packageOrderInfo.setHandleTime(DateTools.getDistanceOfTwoDate(apiPackageOrderInfo.getPayTime(), packageInfo.getShipTime()));
		}
		//从包裹生成到发货的处理时长
		if(packageInfo.getPackageAddTime()!=null){
			packageOrderInfo.setPackageShipDuration(DateTools.getDistanceOfTwoDate(packageInfo.getPackageAddTime(),packageInfo.getShipTime()));
		}
		//从支付到包裹生成的处理时长
		if(apiPackageOrderInfo.getPayTime()!=null&&packageInfo.getPackageAddTime()!=null){
			packageOrderInfo.setPayPackageDuration(DateTools.getDistanceOfTwoDate(apiPackageOrderInfo.getPayTime(),packageInfo.getPackageAddTime()));
		}
		if(null != saiheOrderRecord){
			packageOrderInfo.setCustomerId(saiheOrderRecord.getCustomerId());
			packageOrderInfo.setManagerCode(saiheOrderRecord.getManagerCode());
		}
		return packageOrderInfo;
	}

}
