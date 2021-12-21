package com.upedge.cms.modules.website.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WebsiteBlogInfoVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String urlSuf;
    /**
     *
     */
    private String adminUser;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private Integer viewNum;
    /**
     *
     */
    private Integer followNum;
    /**
     *
     */
    private Integer state;

}
