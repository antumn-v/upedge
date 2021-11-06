package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/store/variant")
public class StoreProductVariantController {
    @Autowired
    private StoreProductVariantService storeProductVariantService;

    @Autowired
    StoreProductService storeProductService;


    @PostMapping("/selectByPlatId")
    public BaseResponse selectByPlatId(@RequestBody PlatIdSelectStoreVariantRequest request){
        List<StoreProductVariantVo> variantVos = storeProductService.selectVariantByPlatId(request);

        return new BaseResponse(ResultCode.SUCCESS_CODE,variantVos);
    }

}
