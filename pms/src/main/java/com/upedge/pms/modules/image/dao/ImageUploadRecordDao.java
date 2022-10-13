package com.upedge.pms.modules.image.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.image.entity.ImageUploadRecord;

import java.util.List;

/**
 * @author gx
 */
public interface ImageUploadRecordDao{

    ImageUploadRecord selectByImageId(String imageId);

    ImageUploadRecord selectByOldImage(String imageUrl);

    ImageUploadRecord selectByPrimaryKey(ImageUploadRecord record);

    int deleteByPrimaryKey(ImageUploadRecord record);

    int updateByPrimaryKey(ImageUploadRecord record);

    int updateByPrimaryKeySelective(ImageUploadRecord record);

    int insert(ImageUploadRecord record);

    int insertSelective(ImageUploadRecord record);

    int insertByBatch(List<ImageUploadRecord> list);

    List<ImageUploadRecord> select(Page<ImageUploadRecord> record);

    long count(Page<ImageUploadRecord> record);

}
