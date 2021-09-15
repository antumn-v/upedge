package com.upedge.ums.modules.organization.request;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
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

    public Organization toOrganization(Session session){
        Long id = IdGenerate.nextId();
        Organization organization=new Organization();
        organization.setId(id);
        organization.setCustomerId(session.getCustomerId());
        if (null != orgParent){
            orgPath = orgParent + ":" + id;
        }else {
            orgPath = id +"";
        }
        organization.setOrgPath(orgPath);
        organization.setOrgName(orgName);
        organization.setOrgParent(orgParent);
        organization.setCreateTime(new Date());
        organization.setUpdateTime(new Date());
        return organization;
    }

}
