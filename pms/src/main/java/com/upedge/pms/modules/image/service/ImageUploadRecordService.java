package com.upedge.pms.modules.image.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.image.entity.ImageUploadRecord;
import com.upedge.thirdparty.ali1688.vo.ProductImgVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gx
 */
public interface ImageUploadRecordService{

    ImageUploadRecord selectByImageId(String imageId);

    ImageUploadRecord uploadStoreImage(ShopifyImage shopifyImage);

    BaseResponse uploadImageByFile(MultipartFile file);

    ImageUploadRecord uploadAlibabaImage(ProductImgVo productImgVo);

    ImageUploadRecord uploadImageByUrl(String image,String imageId);

    ImageUploadRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(ImageUploadRecord record);

    int updateByPrimaryKeySelective(ImageUploadRecord record);

    int insert(ImageUploadRecord record);

    int insertSelective(ImageUploadRecord record);

    List<ImageUploadRecord> select(Page<ImageUploadRecord> record);

    long count(Page<ImageUploadRecord> record);
}

