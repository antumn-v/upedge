package com.upedge.oms.modules.pick.request;

import com.upedge.oms.modules.pick.vo.OrderPickInfoVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TwicePickSubmitRequest {

   @NotNull
   private Long pickId;

   @Size(min = 1)
   private List<OrderPickInfoVo> orderPickInfoVos;
}
