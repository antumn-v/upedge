package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ProductLog;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductLogAddRequest{

    /**
    * 操作类型 1:修改实重 2:修改体积重 3:修改运输模板 4:修改价格
    */
    private Integer optType;
    /**
    * 
    */
    private String oldInfo;
    /**
    * 
    */
    private String newInfo;
    /**
    * 
    */
    private Long productId;
    /**
    * 
    */
    private String sku;
    /**
    * 
    */
    private String adminUser;
    /**
    * 
    */
    private Date createTime;

    public ProductLog toProductLog(){
        ProductLog productLog=new ProductLog();
        productLog.setOptType(optType);
        productLog.setOldInfo(oldInfo);
        productLog.setNewInfo(newInfo);
        productLog.setProductId(productId);
        productLog.setSku(sku);
        productLog.setAdminUser(adminUser);
        productLog.setCreateTime(createTime);
        return productLog;
    }

}
