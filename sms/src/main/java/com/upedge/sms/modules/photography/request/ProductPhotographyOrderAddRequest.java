package com.upedge.sms.modules.photography.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ProductPhotographyOrderAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 参考链接
    */
    private String referenceLink;
    /**
    * 参考图片
    */
    private String referenceImage;
    /**
    * 
    */
    private String photographyLink;
    /**
    * 0=图片，1=视频
    */
    private Integer photographyType;
    /**
    * 
    */
    private BigDecimal payAmount;
    /**
    * 
    */
    private Integer payState;
    /**
    * 
    */
    private Integer refundState;
    /**
    * 
    */
    private Date payTime;
    /**
    * 
    */
    private Long paymentId;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;



}
