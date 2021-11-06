package com.upedge.ums.modules.account.controller;

import com.upedge.ums.modules.account.request.AdminAccountAddRequest;
import com.upedge.ums.modules.account.request.AdminAccountListRequest;
import com.upedge.ums.modules.account.request.AdminAccountUpdateRequest;
import com.upedge.ums.modules.account.response.AdminAccountAddResponse;
import com.upedge.ums.modules.account.response.AdminAccountInfoResponse;
import com.upedge.ums.modules.account.response.AdminAccountListResponse;
import com.upedge.ums.modules.account.response.AdminAccountUpdateResponse;
import com.upedge.ums.modules.account.service.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/adminAccount")
public class AdminAccountController {
    @Autowired
    private AdminAccountService adminAccountService;

    /**
     * 查询账户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public AdminAccountInfoResponse info(@PathVariable Long id) {
        return adminAccountService.info(id);
    }

    /**
     * 账户下拉列表
     * @return
     */
    @RequestMapping(value="/all", method=RequestMethod.POST)
    public AdminAccountListResponse all() {
        return adminAccountService.all();
    }

    /**
     * 潘达账户列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public AdminAccountListResponse list(@RequestBody @Valid AdminAccountListRequest request) {
        return adminAccountService.listAdminAccount(request);
    }

    /**
     * 添加潘达账户
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public AdminAccountAddResponse add(@RequestBody @Valid AdminAccountAddRequest request) {
        return adminAccountService.addAdminAccount(request);
    }

    /**
     * 更新账户信息
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public AdminAccountUpdateResponse update(@PathVariable Long id, @RequestBody @Valid AdminAccountUpdateRequest request) {
        return adminAccountService.updateAdminAccount(id,request);
    }


}
