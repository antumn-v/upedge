package com.upedge.ums.modules.application.vo;

import com.upedge.ums.modules.application.entity.Menu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuVo extends Menu {

    private List<Menu> children = new ArrayList<Menu>();

    private boolean createPerm;
}
