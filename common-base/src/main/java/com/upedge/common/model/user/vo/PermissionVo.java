package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.List;
@Data
public class PermissionVo  {

    private Long id;
    /**
     *
     */
    private Long parentId;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private String description;

    List<PermissionVo> children;
}
