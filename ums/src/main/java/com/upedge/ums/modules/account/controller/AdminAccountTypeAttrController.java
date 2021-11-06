package com.upedge.ums.modules.account.controller;

import com.upedge.ums.modules.account.response.AdminAccountTypeAttrListResponse;
import com.upedge.ums.modules.account.service.AdminAccountTypeAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/adminAccountTypeAttr")
public class AdminAccountTypeAttrController {
    @Autowired
    private AdminAccountTypeAttrService adminAccountTypeAttrService;

    /**
     * 获取账户类型参数列表
     * @param accountType
     * @return
     */
    @RequestMapping(value="/list/{accountType}", method=RequestMethod.POST)
    public AdminAccountTypeAttrListResponse list(@PathVariable Integer accountType) {
        return adminAccountTypeAttrService.listByAccountType(accountType);
    }

//    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
//    public AdminAccountTypeAttrInfoResponse info(@PathVariable Long id) {
//        AdminAccountTypeAttr result = adminAccountTypeAttrService.selectByPrimaryKey(id);
//        AdminAccountTypeAttrInfoResponse res = new AdminAccountTypeAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
//        return res;
//    }

//    @RequestMapping(value="/list", method=RequestMethod.POST)
//    public AdminAccountTypeAttrListResponse list(@RequestBody @Valid AdminAccountTypeAttrListRequest request) {
//        List<AdminAccountTypeAttr> results = adminAccountTypeAttrService.select(request);
//        Long total = adminAccountTypeAttrService.count(request);
//        request.setTotal(total);
//        AdminAccountTypeAttrListResponse res = new AdminAccountTypeAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
//        return res;
//    }

//    @RequestMapping(value="/add", method=RequestMethod.POST)
//    public AdminAccountTypeAttrAddResponse add(@RequestBody @Valid AdminAccountTypeAttrAddRequest request) {
//        AdminAccountTypeAttr entity=request.toAdminAccountTypeAttr();
//        adminAccountTypeAttrService.insertSelective(entity);
//        AdminAccountTypeAttrAddResponse res = new AdminAccountTypeAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
//        return res;
//    }

//    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
//    public AdminAccountTypeAttrUpdateResponse update(@PathVariable Long id,@RequestBody @Valid AdminAccountTypeAttrUpdateRequest request) {
//        AdminAccountTypeAttr entity=request.toAdminAccountTypeAttr(id);
//        adminAccountTypeAttrService.updateByPrimaryKeySelective(entity);
//        AdminAccountTypeAttrUpdateResponse res = new AdminAccountTypeAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
//        return res;
//    }


}
