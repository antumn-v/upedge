package com.upedge.pms.modules.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.response.ImportPriceRuleListResponse;
import com.upedge.pms.modules.product.response.ImportPriceRuleUpdateResponse;
import com.upedge.pms.modules.product.service.ImportPriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/priceRule")
public class ImportPriceRuleController {
    @Autowired
    private ImportPriceRuleService importPriceRuleService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "app:importpricerule:list")
    public ImportPriceRuleListResponse list() {

        return importPriceRuleService.selectPriceRule();
    }


    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "app:importpricerule:update")
    public ImportPriceRuleUpdateResponse update(@RequestBody JSONObject jsonObject) {

        return importPriceRuleService.updatePriceRule(jsonObject);
    }


}
