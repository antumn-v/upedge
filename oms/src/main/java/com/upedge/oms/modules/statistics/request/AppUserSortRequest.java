package com.upedge.oms.modules.statistics.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.statistics.vo.AppUserSortVo;
import lombok.Data;

/**
 * Created by cjq on 2019/7/11.
 */
@Data
public class AppUserSortRequest extends Page<AppUserSortVo> {

   private String startDay;
   private String endDay;

}
