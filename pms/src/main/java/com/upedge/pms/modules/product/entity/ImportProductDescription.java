package com.upedge.pms.modules.product.entity;


import lombok.Data;

/**
 * @author author
 */
@Data
public class ImportProductDescription {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long productId;
    /**
     * 产品描述
     */
    private String description;



	public ImportProductDescription() {
	}
}
