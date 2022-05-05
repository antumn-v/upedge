package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteMessageAllocateRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageListRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteMessageDelResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageListResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteMessageService;
import com.upedge.common.base.BaseResponse;
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
@RequestMapping("/websiteMessage")
public class WebsiteMessageController {
    @Autowired
    private WebsiteMessageService websiteMessageService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //=============================admin===========================

    /**
     * 客户信息列表
     * @param request
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public WebsiteMessageListResponse list(@RequestBody @Valid WebsiteMessageListRequest request) {
        return websiteMessageService.adminList(request);
    }

    /**
     * 删除客户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    public WebsiteMessageDelResponse delWebsiteMessage(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteMessageService.delWebsiteMessage(id,session);
    }

    /**
     * 更新备注
     * @param request
     * @return
     */
    @RequestMapping(value="/updateRemark", method=RequestMethod.POST)
    public WebsiteMessageUpdateResponse updateRemark(@RequestBody @Valid WebsiteMessageUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteMessageService.updateRemark(request,session);
    }

    /**
     * 分配客户经理
     * @param request
     * @return
     */
    @RequestMapping(value="/allocate", method=RequestMethod.POST)
    public WebsiteMessageUpdateResponse allocate(@RequestBody @Valid WebsiteMessageAllocateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteMessageService.allocate(request,session);
    }

    /**
     * 客户信息导出
     * @return
     */
    @RequestMapping(value ="/export",method = RequestMethod.POST)
    public BaseResponse export(){
        return websiteMessageService.export();
    }

}
