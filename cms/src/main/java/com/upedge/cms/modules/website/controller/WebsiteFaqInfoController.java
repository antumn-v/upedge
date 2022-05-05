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
@Api(tags = "faq详情")
@RestController
@RequestMapping("/faqInfo")
public class WebsiteFaqInfoController {
    @Autowired
    private WebsiteFaqInfoService websiteFaqInfoService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    /**
     * FaqInfo列表
     * @param request
     * @return
     */
    @ApiOperation("FaqInfo列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteFaqInfoListResponse list(@RequestBody @Valid WebsiteFaqInfoListRequest request) {
        return websiteFaqInfoService.adminList(request);
    }

    /**
     * FaqInfo详情
     * @param id
     * @return
     */
    @ApiOperation("FaqInfo详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoInfoResponse adminInfo(@PathVariable Long id) {
        return websiteFaqInfoService.adminInfo(id);
    }

    /**
     * 新增FaqInfo
     * @param request
     * @return
     */
    @ApiOperation("新增FaqInfo")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public WebsiteFaqInfoAddResponse addFaqInfo(@RequestBody @Valid WebsiteFaqInfoAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.addFaqInfo(request,session);
    }

    /**
     * 更新FaqInfo
     * @param request
     * @return
     */
    @ApiOperation("更新FaqInfo")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse updateFaqInfo(@RequestBody @Valid WebsiteFaqInfoUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.updateFaqInfo(request,session);
    }

    /**
     * 启用FaqInfo
     * @param id
     * @return
     */
    @ApiOperation("启用FaqInfo")
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse enableFaqInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.enableFaqInfo(id,session);
    }

    /**
     * 禁用FaqInfo
     * @param id
     * @return
     */
    @ApiOperation("禁用FaqInfo")
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public WebsiteFaqInfoUpdateResponse disableFaqInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteFaqInfoService.disableFaqInfo(id,session);
    }


}
