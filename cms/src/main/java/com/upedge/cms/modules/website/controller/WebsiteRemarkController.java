package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteRemarkAddRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkListRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteRemarkAddResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkListResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteRemarkService;
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
@RequestMapping("/websiteRemark")
public class WebsiteRemarkController {
    @Autowired
    private WebsiteRemarkService websiteRemarkService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 好评列表
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteRemarkListResponse adminList(@RequestBody WebsiteRemarkListRequest request) {
        return websiteRemarkService.adminList(request);
    }


    /**
     * 好评详情
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}", method=RequestMethod.POST)
    public WebsiteRemarkInfoResponse adminInfo(@PathVariable Long id) {
        return websiteRemarkService.adminInfo(id);
    }

    /**
     * 新增好评
     * @param request
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public WebsiteRemarkAddResponse addRemark(@RequestBody @Valid WebsiteRemarkAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteRemarkService.addRemark(request,session);
    }

    /**
     * 更新好评
     * @param request
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public WebsiteRemarkUpdateResponse updateRemark(@RequestBody @Valid WebsiteRemarkUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteRemarkService.updateRemark(request,session);
    }

    /**
     * 启用好评
     * @param id
     * @return
     */
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public WebsiteRemarkUpdateResponse enableRemark(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteRemarkService.enableRemark(id,session);
    }

    /**
     * 禁用好评
     * @param id
     * @return
     */
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public WebsiteRemarkUpdateResponse disableRemark(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteRemarkService.disableRemark(id,session);
    }

    /**
     * 删除好评
     * @param id
     * @return
     */
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    public WebsiteRemarkUpdateResponse delWebsiteRemark(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteRemarkService.delWebsiteRemark(id,session);
    }


}
