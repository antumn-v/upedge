package com.upedge.cms.config;

import com.upedge.common.config.HostConfig;

public class ImgConstant {

    //类型
    public enum ImgType {


/*        //AVATAR(1,"E:/image/avatar/", HostConfig.HOST+"/image/avatar/"),
        AVATAR(1,"D:/image/avatar/", HostConfig.HOST+"/image/avatar/"),
        //公告
        NOTICE(2,"D:/image/notice/",HostConfig.HOST+"/image/notice/"),
        //NOTICE(2,"E:/image/notice/",HostConfig.HOST+"/image/notice/"),
        //博客
        // BLOG(3,"E:/image/blog/",HostConfig.HOST+"/image/blog/"),
        BLOG(3,"D:/image/blog/",HostConfig.HOST+"/image/blog/"),
        //博客
        // PRODUCT(4,"E:/image/product/",HostConfig.HOST+"/image/product/");
        PRODUCT(4,"D:/image/product/",HostConfig.HOST+"/image/product/");*/



        //用户头像
        AVATAR(1,"/root/files/image/avatar/", HostConfig.HOST+"/cms/image/avatar/"),
        //公告
        NOTICE(2,"/root/files/image/notice/",HostConfig.HOST+"/cms/image/notice/"),
        //博客
        BLOG(3,"/root/files/image/blog/",HostConfig.HOST+"/cms/image/blog/"),
        //博客
        PRODUCT(4,"/root/files/image/product/",HostConfig.HOST+"/cms/image/product/");

        private Integer type;

        private String fileSavePath;

        private String imgUrlPrefix;

        public Integer getType() {
            return type;
        }

        public String getFileSavePath() {
            return fileSavePath;
        }

        public String getImgUrlPrefix() {
            return imgUrlPrefix;
        }

        ImgType(Integer type, String fileSavePath, String imgUrlPrefix) {
            this.type=type;
            this.fileSavePath = fileSavePath;
            this.imgUrlPrefix = imgUrlPrefix;
        }


    }

    public static ImgType getImgType(Integer type){
        for(ImgType imgType: ImgType.values()){
           if(type.equals(imgType.type)){
               return imgType;
           }
        }
        return null;
    }

}
