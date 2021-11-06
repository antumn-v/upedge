package com.upedge.thirdparty.shoplazza.moudles.product.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ShoplazzaProduct {


    /**
     * id : 0510dace-34da-4555-a896-495e0cb1ed6e
     * title : title1
     * brief : title2
     * description : 商品描述
     * vendor : 供应商
     * vendor_url : http://www.express.com
     * has_only_default_variant : true
     * requires_shipping : true
     * taxable : true
     * inventory_tracking : true
     * inventory_policy : 库存追踪方案
     * inventory_quantity : 100
     * published : true
     * published_at : 2019-10-01 10:00:00 xxxx
     * created_at : 2019-10-01 10:00:00 xxxx
     * updated_at : 2019-10-01 10:00:00 xxxx
     * note : xxx
     * meta_title : xxx
     * meta_description : xxx
     * meta_keyword : tag1,tag2,tag3
     * handle : title1
     * need_variant_image : true
     * spu : spu
     * image : {"src":"https://oss.ali.com/abc.jpg","width":100,"height":100,"alt":""}
     * images : [{"id":"0510dace-34da-4555-a896-495e0cb1ed6e","product_id":"df63c505-074f-47a7-ba4d-b624a281258e","position":1,"src":"https://cdn.shoplazza.com/example.png","width":480,"height":360,"alt":"Image","created_at":"2018-10-31T13:02:19-04:00","updated_at":"2018-10-31T13:02:19-04:00"}]
     * options : [{"id":"0510dace-34da-4555-a896-495e0cb1ed6e","product_id":"df63c505-074f-47a7-ba4d-b624a281258e","name":"color","position":1,"values":["red","black"]},{"id":"0510dace-34da-4555-a896-495e0cb1kkd8","product_id":"df63c505-074f-47a7-ba4d-b624a281258e","name":"size","position":2,"values":["32","33"]}]
     * variants : [{"id":"0510dace-34da-4555-a896-495e0cb1ed6e","product_id":"df63c505-074f-47a7-ba4d-b624a281258e","position":1,"title":"variants title","image":{"src":"https://oss.ali.com/abc.jpg","width":100,"height":100,"alt":""},"price":32,"compare_at_price":45,"weight":0.1,"weight_unit":"kg","inventory_quantity":33,"sku":"20182002154567873574","barcode":"201213112","note":"xxx","option1":"red","option2":"M","option3":"","created_at":"2018-10-31T13:02:19-04:00","updated_at":"2018-10-31T13:02:19-04:00"}]
     */

    private String id;
    private String title;
    private String brief;
    private String description;
    private String vendor;
    private String vendor_url;
    private Boolean has_only_default_variant = false;
    private Boolean requires_shipping;
    private Boolean taxable;
    private Boolean inventory_tracking = true;
    private String inventory_policy = "continue";
    private Integer inventory_quantity;
    private Boolean published = true;
    private String published_at;
    private Date created_at;
    private Date updated_at;
    private String note;
    private String meta_title;
    private String meta_description;
    private String meta_keyword;
    private String handle;
    private Boolean need_variant_image = true;
    private String spu;
    private ShoplazzaImage image;
    private List<ShoplazzaImage> images;
    private List<ShoplazzaOption> options;
    private List<ShoplazzaVariant> variants;

}
