package com.upedge.ums.modules.application.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationVo {

    private Long id;

    private String name;

    private List<String> menuGroupList=new ArrayList<>();

}
