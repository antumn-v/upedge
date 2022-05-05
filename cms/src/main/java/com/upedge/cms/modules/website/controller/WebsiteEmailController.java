package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteEmailListRequest;
import com.upedge.cms.modules.website.response.WebsiteEmailDelResponse;
import com.upedge.cms.modules.website.response.WebsiteEmailListResponse;
import com.upedge.cms.modules.website.service.WebsiteEmailService;
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
@RequestMapping("/websiteEmail")
public class WebsiteEmailController {
    @Autowired
    private WebsiteEmailService websiteEmailService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //=============================admin===========================

    /**
     * 客户邮件列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteEmailListResponse list(@RequestBody @Valid WebsiteEmailListRequest request) {
        return websiteEmailService.adminList(request);
    }

    /**
     * 删除客户邮件
     * @param id
     * @return
     */
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    public WebsiteEmailDelResponse delWebsiteEmail(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteEmailService.delWebsiteEmail(id,session);
    }

}
