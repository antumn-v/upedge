package com.upedge.oms.modules.pack.entity;

import com.upedge.thirdparty.saihe.entity.getPackages.ApiPackageInfo;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiTransport;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageInfo{

	/**
	 * id
	 */
	private Long id;

	/**
	 * 
	 */
    private Integer packageId;
	/**
	 * 
	 */
    private String packageCountry;
	/**
	 * 
	 */
    private Date shipTime;
	/**
	 * 
	 */
    private Integer transportId;
	/**
	 * 
	 */
    private String transportName;
	/**
	 * 
	 */
    private Integer packageWeight;
	/**
	 * 
	 */
    private BigDecimal finalShippingFee;
	/**
	 * 
	 */
    private String logisticsId;
	/**
	 * 
	 */
    private String trackNumber;
	/**
	 * 
	 */
    private BigDecimal orderAmount;
	/**
	 * 
	 */
    private BigDecimal purchaseAmount;
	/**
	 * 
	 */
    private String clientSku;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private String operationUsername;
	/**
	 * 
	 */
    private Integer orderCount;
	/**
	 * 
	 */
    private Integer infoCount;
	/**
	 * 
	 */
    private Date shipDate;
	/**
	 * 
	 */
    private String updateToken;
	/**
	 * 
	 */
    private Long handleTime;
	/**
	 * 包裹生成时间
	 */
    private Date packageAddTime;

    public static PackageInfo toPackageInfo(ApiPackageInfo apiPackageInfo){
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.setPackageId(apiPackageInfo.getPackageID());
		packageInfo.setPackageCountry(apiPackageInfo.getPackageCountry());
		packageInfo.setShipTime(apiPackageInfo.getShipTime());
		packageInfo.setShipDate(apiPackageInfo.getShipTime());
		ApiTransport apiTransport = apiPackageInfo.getTransport();
		if (apiTransport != null) {
			packageInfo.setTransportId(apiTransport.getID());
			packageInfo.setTransportName(apiTransport.getTransportName());
		}
		packageInfo.setPackageWeight(apiPackageInfo.getPackageWeight());
		packageInfo.setFinalShippingFee(apiPackageInfo.getFinalShippingFee());
		packageInfo.setTrackNumber(apiPackageInfo.getTrackNumber()==null?"":apiPackageInfo.getTrackNumber());
		packageInfo.setLogisticsId(apiPackageInfo.getLogisticsID()==null?"":apiPackageInfo.getLogisticsID());
		packageInfo.setOrderAmount(apiPackageInfo.getOrderAmount());
		packageInfo.setPurchaseAmount(apiPackageInfo.getPurchaseAmount());
		if(!StringUtils.isBlank(apiPackageInfo.getClientSKU())&&apiPackageInfo.getClientSKU().length()>1000){
			apiPackageInfo.setClientSKU("");
		}
		packageInfo.setClientSku(apiPackageInfo.getClientSKU());
		packageInfo.setQuantity(apiPackageInfo.getQuantity());
		packageInfo.setOperationUsername(apiPackageInfo.getOperationUserName());
		//包裹生成时间
		packageInfo.setPackageAddTime(apiPackageInfo.getPackageAddTime());
		return packageInfo;
	}

}
