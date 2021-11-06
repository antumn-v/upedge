package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author author
 */
@Data
public class ShippingMethodTemplateUpdateRequest{
   @Size(min = 1)
   List<ShippingMethodTemplate> methodTemplateList;
}
