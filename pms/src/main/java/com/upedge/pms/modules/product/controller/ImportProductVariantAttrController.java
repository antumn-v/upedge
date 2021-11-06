package com.upedge.pms.modules.product.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;
import com.upedge.pms.modules.product.request.ImportProductVariantAttrAddRequest;
import com.upedge.pms.modules.product.request.ImportProductVariantAttrListRequest;
import com.upedge.pms.modules.product.request.ImportProductVariantAttrUpdateRequest;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ImportProductVariantAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/importProductVariantAttr")
public class ImportProductVariantAttrController {
    @Autowired
    private ImportProductVariantAttrService importProductVariantAttrService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "app:importproductvariantattr:info:id")
    public ImportProductVariantAttrInfoResponse info(@PathVariable Long id) {
        ImportProductVariantAttr result = importProductVariantAttrService.selectByPrimaryKey(id);
        ImportProductVariantAttrInfoResponse res = new ImportProductVariantAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariantattr:list")
    public ImportProductVariantAttrListResponse list(@RequestBody @Valid ImportProductVariantAttrListRequest request) {
        List<ImportProductVariantAttr> results = importProductVariantAttrService.select(request);
        Long total = importProductVariantAttrService.count(request);
        request.setTotal(total);
        ImportProductVariantAttrListResponse res = new ImportProductVariantAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariantattr:add")
    public ImportProductVariantAttrAddResponse add(@RequestBody @Valid ImportProductVariantAttrAddRequest request) {
        ImportProductVariantAttr entity=request.toImportProductVariantAttr();
        importProductVariantAttrService.insertSelective(entity);
        ImportProductVariantAttrAddResponse res = new ImportProductVariantAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariantattr:del:id")
    public ImportProductVariantAttrDelResponse del(@PathVariable Long id) {
        importProductVariantAttrService.deleteByPrimaryKey(id);
        ImportProductVariantAttrDelResponse res = new ImportProductVariantAttrDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "app:importproductvariantattr:update")
    public ImportProductVariantAttrUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ImportProductVariantAttrUpdateRequest request) {
        ImportProductVariantAttr entity=request.toImportProductVariantAttr(id);
        importProductVariantAttrService.updateByPrimaryKeySelective(entity);
        ImportProductVariantAttrUpdateResponse res = new ImportProductVariantAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
