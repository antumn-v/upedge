package com.upedge.pms.modules.image.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ImageUploadRecord{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String oldImage;
	/**
	 * 
	 */
    private String newImage;
	/**
	 * 0=店铺  1=1688
	 */
    private Integer imageSource;
	/**
	 * 
	 */
    private String imageSourceId;
	/**
	 * 
	 */
    private Date createTime;

}
