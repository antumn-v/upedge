package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteFaqInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoAddResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoListResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteFaqInfoService;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/faqInfo")
public class WebsiteFaqInfoController {
    @Autowired
    private WebsiteFaqInfoService websiteFaqInfoService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //=============================admin===========================

    /**
     * FaqInfo列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public WebsiteFaqInfoListResponse list(@RequestBody @Valid WebsiteFaqInfoListRequest request) {
        return websiteFaqInfoService.adminList(request);
    }

    /**
     * FaqInfo详情
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/info/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoInfoResponse adminInfo(@PathVariable Long id) {
        return websiteFaqInfoService.adminInfo(id);
    }

    /**
     * 新增FaqInfo
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/add", method=RequestMethod.POST)
    public WebsiteFaqInfoAddResponse addFaqInfo(@RequestBody @Valid WebsiteFaqInfoAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.addFaqInfo(request,session);
    }

    /**
     * 更新FaqInfo
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/update", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse updateFaqInfo(@RequestBody @Valid WebsiteFaqInfoUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.updateFaqInfo(request,session);
    }

    /**
     * 启用FaqInfo
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/enable/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse enableFaqInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.enableFaqInfo(id,session);
    }

    /**
     * 禁用FaqInfo
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/disable/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse disableFaqInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.disableFaqInfo(id,session);
    }


}
