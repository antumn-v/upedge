package com.upedge.ums.modules.user.vo;

import com.upedge.ums.modules.application.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class CustomerStaffDetailVo {

    private String username;

    private String loginName;

    List<Menu> menus;

    Integer state;

    List<Long> orgIds;
}
