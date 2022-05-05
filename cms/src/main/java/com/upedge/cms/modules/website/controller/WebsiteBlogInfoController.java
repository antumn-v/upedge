package com.upedge.cms.modules.website.controller;


import com.upedge.cms.modules.website.request.WebsiteBlogInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoAddResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteBlogInfoService;
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
@RequestMapping("/blogInfo")
public class WebsiteBlogInfoController {
    @Autowired
    private WebsiteBlogInfoService websiteBlogInfoService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //============================admin================================

    /**
     * 博客列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteBlogInfoListResponse list(@RequestBody @Valid WebsiteBlogInfoListRequest request) {
        return websiteBlogInfoService.adminList(request);
    }

    /**
     * 博客详情
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public WebsiteBlogInfoInfoResponse adminInfo(@PathVariable Long id) {
        return websiteBlogInfoService.adminInfo(id);
    }

    /**
     * 新增博客
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public WebsiteBlogInfoAddResponse addBlogInfo(@RequestBody @Valid WebsiteBlogInfoAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogInfoService.addBlogInfo(request,session);
    }

    /**
     * 更新博客
     * @param request
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public WebsiteBlogInfoUpdateResponse updateBlogInfo(@RequestBody @Valid WebsiteBlogInfoUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogInfoService.updateBlogInfo(request,session);
    }

    /**
     * 启用博客
     * @param id
     * @return
     */
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public WebsiteBlogInfoUpdateResponse enableBlogInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogInfoService.enableBlogInfo(id,session);
    }

    /**
     * 禁用博客
     * @param id
     * @return
     */
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public WebsiteBlogInfoUpdateResponse disableBlogInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogInfoService.disableBlogInfo(id,session);
    }


}
