package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class UserPrivateProduct{

	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 用户ID
	 */
    private Long appUserId;
	/**
	 * 店铺id
	 */
    private Long appStoreId;

}
