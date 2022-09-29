package com.upedge.common.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;

import java.io.InputStream;
import java.net.URL;

public class AliYunOssService {

    static String endPoint = "oss-cn-hangzhou.aliyuncs.com";
    static String keyId = "LTAI4G11r85nKNnKxhtHrAQ6";
    static String keySecret = "51qt1QMGeGez01wKCqqA1od6U5RROb";
    static String bucketName = "label-pdf";

    public static String uploadLabel(String labelUrl, String module, String fileName) {

        InputStream inputStream = null;
        try {
            URL url = new URL(labelUrl);
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
