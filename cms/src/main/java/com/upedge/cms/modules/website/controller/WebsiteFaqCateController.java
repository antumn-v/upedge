package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteFaqCateAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteFaqCateAddResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateListResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteFaqCateService;
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
@RequestMapping("/faqCate")
public class WebsiteFaqCateController {
    @Autowired
    private WebsiteFaqCateService websiteFaqCateService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //=============================admin===========================

    /**
     * FaqCate列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public WebsiteFaqCateListResponse list(@RequestBody @Valid WebsiteFaqCateListRequest request) {
        return websiteFaqCateService.adminList(request);
    }

    /**
     * FaqCate详情
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/info/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateInfoResponse adminInfo(@PathVariable Long id) {
        return websiteFaqCateService.adminInfo(id);
    }

    /**
     * 新增FaqCate
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/add", method=RequestMethod.POST)
    public WebsiteFaqCateAddResponse addFaqCate(@RequestBody @Valid WebsiteFaqCateAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.addFaqCate(request,session);
    }

    /**
     * 更新FaqCate
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/update", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse updateFaqCate(@RequestBody @Valid WebsiteFaqCateUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.updateFaqCate(request,session);
    }

    /**
     * 启用FaqCate
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/enable/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse enableFaqCate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.enableFaqCate(id,session);
    }

    /**
     * 禁用FaqCate
     * @param id
     * @return
     */
    @RequestMapping(value="/admin/disable/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse disableFaqCate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.disableFaqCate(id,session);
    }

    /**
     * FaqCate下拉列表
     * @return
     */
    @RequestMapping(value="/admin/all", method=RequestMethod.POST)
    public WebsiteFaqCateListResponse allFaqCate() {
        return websiteFaqCateService.allFaqCate();
    }

}
