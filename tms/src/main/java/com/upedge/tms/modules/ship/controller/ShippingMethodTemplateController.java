package com.upedge.tms.modules.ship.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateUpdateRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateAddResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateDelResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateListResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateUpdateResponse;
import com.upedge.tms.modules.ship.service.ShippingMethodTemplateService;
import com.upedge.tms.modules.ship.service.ShippingTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 运输模板-运输方式
 *
 * @author author
 */
@RestController
@RequestMapping("/shippingMethodTemplate")
public class ShippingMethodTemplateController {
    @Autowired
    private ShippingMethodTemplateService shippingMethodTemplateService;
    @Autowired
    private ShippingTemplateService shippingTemplateService;

    /**
     * 运输方式对应的运输属性列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public ShippingMethodTemplateListResponse list(@RequestBody @Valid ShippingMethodTemplateListRequest request) {
        return shippingMethodTemplateService.listMethodTemplate(request);
    }

    /**
     * 新增运输模板-运输方式
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ShippingMethodTemplateAddResponse add(@RequestBody @Valid ShippingMethodTemplateAddRequest request) {
        return shippingMethodTemplateService.addShippingMethodTemplate(request);
    }

    /**
     * 删除运输模板-运输方式
     * @param id
     * @return
     */
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    public ShippingMethodTemplateDelResponse del(@PathVariable Long id) {
        shippingMethodTemplateService.deleteByPrimaryKey(id);
        ShippingMethodTemplateDelResponse res = new ShippingMethodTemplateDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 更新排序
     * @param request
     * @return
     */
    @RequestMapping(value="/updateSort", method=RequestMethod.POST)
    public ShippingMethodTemplateUpdateResponse updateSort(@RequestBody @Valid ShippingMethodTemplateUpdateRequest request) {
        return shippingMethodTemplateService.updateSort(request);
    }


}
