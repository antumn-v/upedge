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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author author
 */
@Api(tags = "faq类目管理")
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
    @ApiOperation("FaqCate列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteFaqCateListResponse list(@RequestBody @Valid WebsiteFaqCateListRequest request) {
        return websiteFaqCateService.adminList(request);
    }

    /**
     * FaqCate详情
     * @param id
     * @return
     */
    @ApiOperation("FaqCate详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateInfoResponse adminInfo(@PathVariable Long id) {
        return websiteFaqCateService.adminInfo(id);
    }

    /**
     * 新增FaqCate
     * @param request
     * @return
     */
    @ApiOperation("新增FaqCate")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public WebsiteFaqCateAddResponse addFaqCate(@RequestBody @Valid WebsiteFaqCateAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.addFaqCate(request,session);
    }

    /**
     * 更新FaqCate
     * @param request
     * @return
     */
    @ApiOperation("更新FaqCate")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse updateFaqCate(@RequestBody @Valid WebsiteFaqCateUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.updateFaqCate(request,session);
    }

    /**
     * 启用FaqCate
     * @param id
     * @return
     */
    @ApiOperation("启用FaqCate")
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse enableFaqCate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.enableFaqCate(id,session);
    }

    /**
     * 禁用FaqCate
     * @param id
     * @return
     */
    @ApiOperation("禁用FaqCate")
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public WebsiteFaqCateUpdateResponse disableFaqCate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqCateService.disableFaqCate(id,session);
    }

    /**
     * FaqCate下拉列表
     * @return
     */
    @ApiOperation("FaqCate下拉列表")
    @RequestMapping(value="/all", method=RequestMethod.POST)
    public WebsiteFaqCateListResponse allFaqCate() {
        return websiteFaqCateService.allFaqCate();
    }

}
