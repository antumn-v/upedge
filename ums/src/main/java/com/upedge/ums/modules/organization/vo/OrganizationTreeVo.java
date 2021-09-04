package com.upedge.ums.modules.organization.vo;

import com.upedge.ums.modules.organization.entity.Organization;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationTreeVo extends Organization {

    List<Organization> children = new ArrayList<>();
}
