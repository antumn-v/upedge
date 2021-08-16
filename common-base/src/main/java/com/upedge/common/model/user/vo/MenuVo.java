package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Data
public class MenuVo {
    private Long id;

    private String title;

    private String name;

    private String url;

    private Boolean leaf;

    private Long parentId;

    private String menuPath;

    private Long seq;

    private List<MenuVo> children;


}
