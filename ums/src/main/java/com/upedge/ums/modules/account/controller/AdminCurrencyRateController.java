package com.upedge.ums.modules.account.controller;

import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.request.AdminCurrencyRateAddRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateListRequest;
import com.upedge.ums.modules.account.request.AdminCurrencyRateUpdateRequest;
import com.upedge.ums.modules.account.response.AdminCurrencyRateAddResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateInfoResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateListResponse;
import com.upedge.ums.modules.account.response.AdminCurrencyRateUpdateResponse;
import com.upedge.ums.modules.account.service.AdminCurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/adminCurrencyRate")
public class AdminCurrencyRateController {
    @Autowired
    private AdminCurrencyRateService adminCurrencyRateService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 货币汇率详情
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    public AdminCurrencyRateInfoResponse info(@PathVariable Long id) {
        return adminCurrencyRateService.info(id);
    }

    /**
     * admin货币汇率列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public AdminCurrencyRateListResponse list(@RequestBody @Valid AdminCurrencyRateListRequest request) {
        return adminCurrencyRateService.adminList(request);
    }

    /**
     * 添加货币汇率
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public AdminCurrencyRateAddResponse add(@RequestBody @Valid AdminCurrencyRateAddRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return adminCurrencyRateService.addCurrencyRate(request,session);
    }

    /**
     * 更新货币汇率
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public AdminCurrencyRateUpdateResponse update(@PathVariable Long id, @RequestBody @Valid AdminCurrencyRateUpdateRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return adminCurrencyRateService.updateCurrencyRate(request,session);
    }


}
