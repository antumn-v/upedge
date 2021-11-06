package com.upedge.ums.modules.account.controller;

import io.swagger.annotations.ApiOperation;
import com.upedge.ums.modules.account.entity.PayMethodAttr;
import com.upedge.ums.modules.account.request.PayMethodAddUpdateRequest;
import com.upedge.ums.modules.account.request.PayMethodAttrsListRequest;
import com.upedge.ums.modules.account.request.PayMethodListRequest;
import com.upedge.ums.modules.account.response.*;
import com.upedge.ums.modules.account.service.PayMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 海桐
 */
//@RestController
//@RequestMapping("/payMethod")
public class PayMethodController {

    private String moudle = "paymethod";

    @Autowired
    PayMethodService payMethodService;

    /**
     * 支付方式列表
     */
    @PostMapping("/list")
    public PayMethodListResponse listPayMethod(@RequestBody PayMethodListRequest request){

        return payMethodService.listPayMethod(request);
    }

    /**
     * 查询支付方式属性
     * 超级管理员之外的用户只能看到需用户输入的属性
     * @param request
     * @return
     */
    @PostMapping("/{id}/attrs")
    public PayMethodAttrsListResponse payMethodAttrsList(@PathVariable Integer id, @RequestBody PayMethodAttrsListRequest request){
        if(request == null){
            request.setT(new PayMethodAttr());
        }
        if(request.getT().getPaymethodId() == null){
            request.getT().setPaymethodId(id);
        }
        return payMethodService.selectPayMethodAttr(request);
    }

    /**
     * 添加支付方式
     * @param request
     * @return
     */
    @ApiOperation("添加支付方式")
    @PostMapping("/add")
    public PayMethodAddUpdateResponse addPayMethod(@RequestBody PayMethodAddUpdateRequest request){
        return payMethodService.addPayMethod(request);
    }

    /**
     * 禁用支付方式
     * @param id
     * @return
     */
    @ApiOperation("添加支付方式")
    @PostMapping("/{id}/disable")
    public PayMethodDisableResponse disablePayMethod(@PathVariable Integer id){
        return payMethodService.disablePayMethod(id);
    }

    /**
     * 启用支付方式
     * @param id
     * @return
     */
    @ApiOperation("启用支付方式")
    @PostMapping("/{id}/enable")
    public PayMethodEnableResponse enablePayMethod(@PathVariable Integer id){
        return payMethodService.enablePayMethod(id);
    }

    /**
     * 更新支付方式
     * @param id
     * @param request
     * @return
     */
    @ApiOperation("更新支付方式")
    @PostMapping("/{id}/update")
    public PayMethodAddUpdateResponse updatePayMethod(@PathVariable Integer id, @RequestBody PayMethodAddUpdateRequest request){
        return payMethodService.updatePayMethod(id, request);
    }

}
