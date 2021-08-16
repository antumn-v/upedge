package com.upedge.ums.modules.organization.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.organization.entity.Organization;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private String orgPath;
    /**
    * 
    */
    private String orgName;
    /**
    * 
    */
    private Long orgParent;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public Organization toOrganization(){
        Organization organization=new Organization();
        organization.setCustomerId(customerId);
        organization.setOrgPath(orgPath);
        organization.setOrgName(orgName);
        organization.setOrgParent(orgParent);
        organization.setCreateTime(createTime);
        organization.setUpdateTime(updateTime);
        return organization;
    }

}
