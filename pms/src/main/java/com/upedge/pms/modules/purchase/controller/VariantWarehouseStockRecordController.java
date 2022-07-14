package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "仓库出入记录")
@RestController
@RequestMapping("/variantWarehouseStockRecord")
public class VariantWarehouseStockRecordController {
    @Autowired
    private VariantWarehouseStockRecordService variantWarehouseStockRecordService;


    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestockrecord:list")
    public BaseResponse list(@RequestBody @Valid VariantWarehouseStockRecordListRequest request) {
        return variantWarehouseStockRecordService.variantStockRecordList(request);
    }


}
