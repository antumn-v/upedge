package com.upedge.pms.modules.product.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ImportProductAttribute;
import com.upedge.pms.modules.product.request.ImportAddAppProductRequest;
import com.upedge.pms.modules.product.request.ImportProductAttributeListRequest;
import com.upedge.pms.modules.product.response.ImportProductAttributeListResponse;
import com.upedge.pms.modules.product.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "My Product")
@RestController
@RequestMapping("/myProduct")
public class MyProductController {



    @Autowired
    ImportProductService importProductService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ImportProductAttributeService importProductAttributeService;

    @Autowired
    ImportProductImageService importProductImageService;

    @Autowired
    private ImportProductVariantService importProductVariantService;

    @Autowired
    private ImportProductDescriptionService importProductDescriptionService;

    @ApiOperation("从winning product导入")
    @PostMapping("/import/winningProduct")
    public BaseResponse importFormWinningProduct(@RequestBody @Valid ImportAddAppProductRequest request){
        return importProductService.addAppProduct(request);
    }


    @ApiOperation("My Product列表")
    @PostMapping("/list")
    public ImportProductAttributeListResponse list(@RequestBody @Valid ImportProductAttributeListRequest request) {
        if (null == request.getT()) {
            request.setT(new ImportProductAttribute());
        }
        Session session = UserUtil.getSession(redisTemplate);
        request.getT().setCustomerId(session.getCustomerId());
        request.setOrderBy("create_time desc");
        List<ImportProductAttribute> results = importProductAttributeService.select(request);
        Long total = importProductAttributeService.count(request);
        request.setTotal(total);
        return new ImportProductAttributeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }


//    @GetMapping("/{id}/detail")
//    public BaseResponse myProductDetail(@PathVariable Long id){
//
//    }
}
