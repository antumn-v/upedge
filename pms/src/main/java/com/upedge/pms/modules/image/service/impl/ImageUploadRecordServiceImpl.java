package com.upedge.pms.modules.image.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.upedge.common.base.Page;
import com.upedge.common.utils.IdGenerate;
import com.upedge.pms.modules.image.dao.ImageUploadRecordDao;
import com.upedge.pms.modules.image.entity.ImageUploadRecord;
import com.upedge.pms.modules.image.service.ImageUploadRecordService;
import com.upedge.thirdparty.ali1688.vo.ProductImgVo;
import com.upedge.thirdparty.shopify.moudles.product.entity.ShopifyImage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;


@Service
public class ImageUploadRecordServiceImpl implements ImageUploadRecordService {

    @Autowired
    private ImageUploadRecordDao imageUploadRecordDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        ImageUploadRecord record = new ImageUploadRecord();
        record.setId(id);
        return imageUploadRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ImageUploadRecord record) {
        return imageUploadRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ImageUploadRecord record) {
        return imageUploadRecordDao.insert(record);
    }

    @Override
    public ImageUploadRecord selectByImageId(String imageId) {
        return imageUploadRecordDao.selectByImageId(imageId);
    }

    @Override
    public ImageUploadRecord uploadStoreImage(ShopifyImage shopifyImage) {

        String imageUrl = shopifyImage.getSrc();
        if (StringUtils.isBlank(imageUrl)){
            return null;
        }
        ImageUploadRecord imageUploadRecord = imageUploadRecordDao.selectByImageId(shopifyImage.getId());
        if (null != imageUploadRecord){
            return imageUploadRecord;
        }
        String newImage = uploadImage(imageUrl,"store");
        if (StringUtils.isBlank(newImage)){
            return null;
        }
        imageUploadRecord = new ImageUploadRecord();
        imageUploadRecord.setNewImage(newImage);
        imageUploadRecord.setOldImage(imageUrl);
        imageUploadRecord.setImageSource(0);
        imageUploadRecord.setImageSourceId(shopifyImage.getId());
        imageUploadRecord.setCreateTime(new Date());
        imageUploadRecordDao.insert(imageUploadRecord);

        return imageUploadRecord;
    }

    @Override
    public ImageUploadRecord uploadAlibabaImage(ProductImgVo productImgVo) {
        String imageId = productImgVo.getId().toString();
        String imageUrl = productImgVo.getImageUrl();
        if (StringUtils.isBlank(imageUrl)){
            return null;
        }
        ImageUploadRecord imageUploadRecord = imageUploadRecordDao.selectByImageId(imageId);
        if (null != imageUploadRecord){
            return imageUploadRecord;
        }
        String newImage = uploadImage(imageUrl,"store");
        if (StringUtils.isBlank(newImage)){
            return null;
        }
        imageUploadRecord = new ImageUploadRecord();
        imageUploadRecord.setNewImage(newImage);
        imageUploadRecord.setOldImage(imageUrl);
        imageUploadRecord.setImageSource(0);
        imageUploadRecord.setImageSourceId(imageId);
        imageUploadRecord.setCreateTime(new Date());
        imageUploadRecordDao.insert(imageUploadRecord);

        return imageUploadRecord;
    }

    /**
     *
     */
    public ImageUploadRecord selectByPrimaryKey(Integer id){
        ImageUploadRecord record = new ImageUploadRecord();
        record.setId(id);
        return imageUploadRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ImageUploadRecord record) {
        return imageUploadRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ImageUploadRecord record) {
        return imageUploadRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ImageUploadRecord> select(Page<ImageUploadRecord> record){
        record.initFromNum();
        return imageUploadRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ImageUploadRecord> record){
        return imageUploadRecordDao.count(record);
    }


    public String uploadImage(String imageUrl, String module) {

        String fileName = IdGenerate.uuid() + ".jpg";
        String endPoint = "oss-us-east-1.aliyuncs.com";
        String keyId = "LTAI4G11r85nKNnKxhtHrAQ6";
        String keySecret = "51qt1QMGeGez01wKCqqA1od6U5RROb";
        String bucketName = "upedge-image";

        InputStream inputStream = null;
        try {
            URL url = new URL(imageUrl);
            inputStream = url.openStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建bucket
            ossClient.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        //文件名：uuid.扩展名
        String key = module + "/" + fileName;

        //文件上传至阿里云
        ossClient.putObject(bucketName, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回url地址
        return "https://" + bucketName + "." + endPoint + "/" + key;
    }

}