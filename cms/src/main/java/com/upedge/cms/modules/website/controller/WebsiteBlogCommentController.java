package com.upedge.cms.modules.website.controller;

import com.upedge.cms.modules.website.request.WebsiteBlogCommentListRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteBlogCommentService;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/comment")
public class WebsiteBlogCommentController {
    @Autowired
    private WebsiteBlogCommentService websiteBlogCommentService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //=============================admin===========================


    /**
     * 博客评论列表
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public WebsiteBlogCommentListResponse adminList(@RequestBody WebsiteBlogCommentListRequest request) {
        return websiteBlogCommentService.adminList(request);
    }
    /**
     * 禁用博客评论
     */
    @RequestMapping(value="/admin/disable/{id}", method=RequestMethod.POST)
    public WebsiteBlogCommentUpdateResponse disableComment(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogCommentService.disableComment(id,session);
    }

    /**
     * 启用博客评论
     */
    @RequestMapping(value="/admin/enable/{id}", method=RequestMethod.POST)
    public WebsiteBlogCommentUpdateResponse enableComment(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return websiteBlogCommentService.enableComment(id,session);
    }

}
