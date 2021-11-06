package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreProductAttributeAddRequest{

                                    /**
             * woocommerce产品ID
             */
            private String platProductId;
                                /**
             * 商品店铺标题
             */
            private String title;
                                /**
             * 商品店铺图片
             */
            private String image;
                                /**
             * 商品店铺供应商
             */
            private String vendor;
                                /**
             * 店铺ID
             */
            private Long storeId;
                                /**
             * 
             */
            private Long adminProductId;
                                /**
             * 0:SIB ,1:Ali, 2:其他
             */
            private Integer source;
                                /**
             * 0:未关联 1 已关联
             */
            private Integer relateState;
                                /**
             * 店铺名称
             */
            private String storeName;
                                /**
             * 商品价格
             */
            private String price;
                                /**
             * 重名链接
             */
            private String handle;
                                /**
             * 0,从本地删除，1：shopift和本地都删除，2：在shopif中进行的删除操作
             */
            private Integer state;
                                /**
             * 
             */
            private Date createAt;
                                /**
             * 
             */
            private Date updateAt;
                                /**
             * 导入到系统的时间
             */
            private Date importTime;
            
    public StoreProductAttribute toStoreProductAttribute(){
        StoreProductAttribute storeProductAttribute=new StoreProductAttribute();
        storeProductAttribute.setPlatProductId(platProductId);
        storeProductAttribute.setTitle(title);
        storeProductAttribute.setImage(image);
        storeProductAttribute.setVendor(vendor);
        storeProductAttribute.setStoreId(storeId);
        storeProductAttribute.setAdminProductId(adminProductId);
        storeProductAttribute.setSource(source);
        storeProductAttribute.setRelateState(relateState);
        storeProductAttribute.setStoreName(storeName);
        storeProductAttribute.setPrice(price);
        storeProductAttribute.setHandle(handle);
        storeProductAttribute.setState(state);
        storeProductAttribute.setCreateAt(createAt);
        storeProductAttribute.setUpdateAt(updateAt);
        storeProductAttribute.setImportTime(importTime);
        return storeProductAttribute;
    }

}
