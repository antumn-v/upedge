package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.AppProductVo;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class MarketPlaceListRequest extends Page<AppProductVo> {
    /**
     *  Best Seller = turnover desc  (默认)
     *
     *  New Arrival = create_time desc
     *
     *  Best Feedback = score desc
     *
     *  Price  = price_range desc/asc
     */
    private String orderBy = "turnover desc";
}
