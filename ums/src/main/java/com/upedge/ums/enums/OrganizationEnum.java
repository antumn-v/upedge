package com.upedge.ums.enums;

import java.util.Date;

/**
 *  组织枚举
 */
public enum OrganizationEnum {
    SYSTEM_ORGANIZATIONENUM(1000000000000000000L,1427460745513627649L,"1000000000000000000","upedge_system",0L,new Date(),new Date()),
    ADMIN_ORGANIZATIONENUM(1000000000000000001L,1000000000000000001L,"1000000000000000000|1000000000000000001","upedge_admin",0L,new Date(),new Date()),
    APP_ORGANIZATIONENUM(1000000000000000002L,1000000000000000002L,"1000000000000000000|1000000000000000002"," ",0L,new Date(),new Date()),
    ;

   private Long id;
   private Long customerId;
   private String orgPath;
   private String orgName;
   private Long orgParent;
   private Date createTime;
   private Date updateTime;

    OrganizationEnum(Long id, Long customerId, String orgPath, String orgName, Long orgParent, Date createTime, Date updateTime) {
        this.id = id;
        this.customerId = customerId;
        this.orgPath = orgPath;
        this.orgName = orgName;
        this.orgParent = orgParent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getOrgParent() {
        return orgParent;
    }

    public void setOrgParent(Long orgParent) {
        this.orgParent = orgParent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
