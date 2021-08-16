package com.upedge.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class UploadImgUtil {
    //获取上传路径
/*    final static String fileSavePath ="D:/image/";
    final static String prefix= HostConfig.HOST+"/image/";*/
    final static String fileSavePath ="/root/project/image/";
    final static String prefix= "/root/project/image/";

    public enum IMGDIR {
        PRODUCT("product/","产品图片"),
        USER("user/","用户图片");


        private String dir;

        private String desc;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        IMGDIR(String dir, String desc) {
            this.dir = dir;
            this.desc = desc;
        }

    }

    public static List<String> uploadImg(List<MultipartFile> files, String dir){
       List<String> imgList=new ArrayList<>();
       for(MultipartFile file:files){
           String imgUrl=uploadImg(file,dir);
           if(!StringUtils.isBlank(imgUrl)){
               imgList.add(imgUrl);
           }
       }
       return imgList;
    }

    public static String uploadImg(MultipartFile file, String dir){
        try {
        //设置图片为唯一的uuid
        String originalFilename=file.getOriginalFilename();
        String pictureName = IdGenerate.nextId()+originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

        String filePath=fileSavePath+dir+pictureName;
        pictureName=prefix+dir+pictureName;
            /**
             * transferTo在开发Web应用程序时比较常见的功能之一，
             * 就是允许用户利用multipart请求将本地文件上传到服务器,
             * Spring通过对ServletAPI的HttpServletRequest接口进行扩展，使其能够很好地处理文件上传
             */
            file.transferTo(new File(filePath));
            return pictureName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取上传文件的md5 去重
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
