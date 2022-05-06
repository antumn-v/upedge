package com.upedge.cms.modules.website.controller;


import com.upedge.cms.modules.website.entity.*;
import com.upedge.cms.modules.website.service.*;
import com.upedge.cms.modules.website.vo.WebSiteSearch;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/website/")
public class WebsiteController {

    @Autowired
    WebsiteFaqCateService websiteFaqCateService;
    @Autowired
    WebsiteFaqInfoService websiteFaqInfoService;
    @Autowired
    WebsiteBlogInfoService websiteBlogInfoService;
    @Autowired
    WebsiteBlogCommentService websiteBlogCommentService;
    @Autowired
    WebsiteMessageService websiteMessageService;
    @Autowired
    WebsiteCommentFollowService websiteCommentFollowService;
    @Autowired
    WebsiteBlogFollowService websiteBlogFollowService;
    @Autowired
    WebsiteEmailService websiteEmailService;
    @Autowired
    WebsiteRemarkService websiteRemarkService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("faq类别")
    @PostMapping(value= "faqCate")
    @ResponseBody
    public List<WebsiteFaqCate> faqCate() {
        List<WebsiteFaqCate> list = websiteFaqCateService.listFaqCate();
        return list;
    }

//    @PostMapping(value = "faqInfo/{cateId}")
//    @ResponseBody
//    public List<WebsiteFaqInfo> faqInfo(@PathVariable(value = "cateId") String cateId) {
//        List<WebsiteFaqInfo> list = websiteFaqInfoService.listFaqInfo(cateId);
//        return list;
//    }

    //搜索faq 根据问题关键词搜搜
    @ApiOperation("faq类别")
    @PostMapping(value = "faqInfo/search")
    @ResponseBody
    public BaseResponse faqInfoSearch(@RequestBody WebSiteSearch webSiteSearch) {
        List<WebsiteFaqInfo> list = websiteFaqInfoService.searchFaqInfo(webSiteSearch);
        return BaseResponse.success(list);
    }

    @PostMapping(value = "blog/search")
    @ResponseBody
    public List<WebsiteBlogInfo> blogSearch(@RequestBody WebSiteSearch webSiteSearch) {
        List<WebsiteBlogInfo> list = websiteBlogInfoService.blogSearch(webSiteSearch.getInfo());
        for(WebsiteBlogInfo websiteBlogInfo:list){
            Long commentNum= websiteBlogCommentService.countWebsiteRemarkByBlogId(websiteBlogInfo.getId());
            websiteBlogInfo.setCommentNum(commentNum);
        }
        return list;
    }

    @PostMapping(value = "blog")
    @ResponseBody
    public List<WebsiteBlogInfo> blog() {
        List<WebsiteBlogInfo> list = websiteBlogInfoService.listBlog();
        for(WebsiteBlogInfo websiteBlogInfo:list){
            Long commentNum= websiteBlogCommentService.countWebsiteRemarkByBlogId(websiteBlogInfo.getId());
            websiteBlogInfo.setCommentNum(commentNum);
        }
        return list;
    }

    @PostMapping(value = "blogInfo")
    @ResponseBody
    public WebsiteBlogInfo blogInfo(@RequestBody WebsiteBlogInfo blogInfo) throws CustomerException {
        Session session = UserUtil.getSession(redisTemplate);
        if (null != session){
            blogInfo.setUserId(session.getId());
        }
        WebsiteBlogInfo websiteBlogInfo = websiteBlogInfoService.queryBlogByUrlSuf(blogInfo.getUrlSuf());
        if(websiteBlogInfo==null){
            return new WebsiteBlogInfo();
        }
        //查询博客评论
        List<WebsiteBlogComment> commentList= websiteBlogCommentService.listComment(websiteBlogInfo.getId());
        //评论点赞状态
        for(WebsiteBlogComment comment:commentList){
            if(blogInfo.getUserId() != null){
                WebsiteCommentFollow follow=websiteCommentFollowService.queryWebsiteCommentFollow(comment.getId(),blogInfo.getUserId());
                if(follow!=null&&follow.getState()!=null&&follow.getState()==1){
                    comment.setFollowState(1);
                }
            }
        }
        if(commentList==null){
            commentList=new ArrayList<>();
        }
        websiteBlogInfo.setCommentList(commentList);
        websiteBlogInfo.setCommentNum((long) commentList.size());
        if(blogInfo.getUserId() != null){
            WebsiteBlogFollow follow=websiteBlogFollowService.queryWebsiteBlogFollow(websiteBlogInfo.getId(),blogInfo.getUserId());
            if(follow!=null&&follow.getState()!=null&&follow.getState()==1){
                websiteBlogInfo.setFollowState(1);
            }
        }
        return websiteBlogInfo;
    }

    @PostMapping(value = "viewBlog")
    @ResponseBody
    public WebsiteBlogInfo viewBlog(@RequestBody WebsiteBlogInfo blogInfo) {
        if(blogInfo.getId()==null){
            return new WebsiteBlogInfo();
        }
        WebsiteBlogInfo websiteBlogInfo = websiteBlogInfoService.viewBlog(blogInfo.getId());
        return websiteBlogInfo;
    }

    //留言
    @PostMapping(value = "leaveMsg")
    @ResponseBody
    public String leaveMsg(@RequestBody WebsiteMessage websiteMessage, HttpServletRequest request){
        websiteMessageService.save(websiteMessage,request);
        return "success";
    }

    @PostMapping(value = "leaveEmail")
    @ResponseBody
    public String leaveEmail(@RequestBody WebsiteEmail websiteEmail,HttpServletRequest request){
        if(!StringUtils.isBlank(websiteEmail.getEmail())) {
            websiteEmail.setState(1);
            websiteEmailService.save(websiteEmail, request);
        }
        return "success";
    }

    @PostMapping(value = "listRemark")
    @ResponseBody
    public List<WebsiteRemark> listRemark(HttpServletRequest request){
        List<WebsiteRemark>  list=websiteRemarkService.listWebsiteRemark();
        return list;
    }

    //用户评论博客
    @PostMapping(value = "commentBlog")
    @ResponseBody
    public String commentBlog(@RequestBody WebsiteBlogComment websiteBlogComment) throws CustomerException {
        if(websiteBlogComment.getComment()==null
                ||websiteBlogComment.getBlogId()==null){
            return "error";
        }
        WebsiteBlogInfo blogInfo=websiteBlogInfoService.selectByPrimaryKey(websiteBlogComment.getBlogId());
        if(blogInfo==null){
            return "error";
        }
        Session session = getSession();
        websiteBlogComment.setUserId(session.getId());
        websiteBlogComment.setUserName(session.getUserName());
        websiteBlogComment.setUpdateTime(new Date());
        websiteBlogComment.setCreateTime(new Date());
        websiteBlogComment.setComment(websiteBlogComment.getComment());
        websiteBlogComment.setState(1);
        websiteBlogComment.setFollowNum(0);
        websiteBlogCommentService.insert(websiteBlogComment);
        return "success";
    }

    //用户编辑评论
    @PostMapping(value = "editComment")
    @ResponseBody
    public String editComment(@RequestBody WebsiteBlogComment websiteBlogComment) throws CustomerException {
        if(StringUtils.isBlank(websiteBlogComment.getComment())
                ||websiteBlogComment.getId()==null){
            return "error";
        }
        Session session = getSession();
        websiteBlogComment.setUserId(session.getId());
        WebsiteBlogComment comment=websiteBlogCommentService.selectByPrimaryKey(websiteBlogComment.getId());
        if(comment==null){
            return "error";
        }
        if(!comment.getUserId().equals(websiteBlogComment.getUserId())){
            return "error";
        }
        comment.setUpdateTime(new Date());
        comment.setComment(websiteBlogComment.getComment());
        websiteBlogCommentService.updateByPrimaryKeySelective(comment);
        return "success";
    }


    //用户删除评论
    @PostMapping(value = "deleteComment")
    @ResponseBody
    public String deleteComment(@RequestBody WebsiteBlogComment websiteBlogComment) throws CustomerException {
        if(websiteBlogComment.getId()==null){
            return "error";
        }
        Session session = getSession();
        websiteBlogComment.setUserId(session.getId());
        WebsiteBlogComment comment=websiteBlogCommentService.selectByPrimaryKey(websiteBlogComment.getId());
        if(comment==null){
            return "error";
        }
        if(!comment.getUserId().equals(websiteBlogComment.getUserId())){
            return "error";
        }
        comment.setUpdateTime(new Date());
        //逻辑删除评论
        comment.setState(2);
        websiteBlogCommentService.updateByPrimaryKeySelective(comment);
        return "success";
    }

    //喜欢博客
    @PostMapping(value = "followBlog")
    @ResponseBody
    public String followBlog(@RequestBody WebsiteBlogFollow websiteBlogFollow) throws CustomerException {
        if(websiteBlogFollow.getBlogId()==null){
            return "error";
        }
        WebsiteBlogInfo blogInfo=websiteBlogInfoService.selectByPrimaryKey(websiteBlogFollow.getBlogId());
        if(blogInfo==null){
            return "error";
        }
        Session session = getSession();
        websiteBlogFollow.setUserId(session.getId());
        websiteBlogFollow.setUserName(session.getUserName());
        WebsiteBlogFollow old=websiteBlogFollowService.queryWebsiteBlogFollow(websiteBlogFollow.getBlogId(),
                websiteBlogFollow.getUserId());
        if(old!=null  &&  old.getState() == 0){
            old.setState(1);
            websiteBlogFollowService.updateByPrimaryKeySelective(old);
            websiteBlogInfoService.followBlog(websiteBlogFollow.getBlogId());
        }
       else if(old == null){
            websiteBlogFollow.setId(IdGenerate.nextId());
            websiteBlogFollow.setState(1);
            websiteBlogFollowService.insert(websiteBlogFollow);
            websiteBlogInfoService.followBlog(websiteBlogFollow.getBlogId());
        }
        return "success";
    }


    //取消喜欢博客
    @PostMapping(value = "cancelBlog")
    @ResponseBody
    public String cancelBlog(@RequestBody WebsiteBlogFollow websiteBlogFollow) throws CustomerException {
        if(websiteBlogFollow.getBlogId()==null){
            return "error";
        }
        WebsiteBlogInfo blogInfo=websiteBlogInfoService.selectByPrimaryKey(websiteBlogFollow.getBlogId());
        if(blogInfo==null){
            return "error";
        }
        Session session = getSession();
        websiteBlogFollow.setUserId(session.getId());
        WebsiteBlogFollow old=websiteBlogFollowService.queryWebsiteBlogFollow(websiteBlogFollow.getBlogId(),
                websiteBlogFollow.getUserId());
        if(old!=null  &&  old.getState() == 1 ){
            old.setState(0);
            websiteBlogFollowService.updateByPrimaryKeySelective(old);
            websiteBlogInfoService.cancelBlog(websiteBlogFollow.getBlogId());
        }

        return "success";
    }

    //喜欢评论
    @PostMapping(value = "followComment")
    @ResponseBody
    public String followComment(@RequestBody WebsiteCommentFollow websiteCommentFollow) throws CustomerException {
        if(websiteCommentFollow.getCommentId()==null){
            return "error";
        }
        WebsiteBlogComment blogInfo=websiteBlogCommentService.selectByPrimaryKey(websiteCommentFollow.getCommentId());
        if(blogInfo==null){
            return "error";
        }
        Session session = getSession();
        websiteCommentFollow.setUserId(session.getId());
        websiteCommentFollow.setUserName(session.getUserName());
        WebsiteCommentFollow old=websiteCommentFollowService.queryWebsiteCommentFollow(websiteCommentFollow.getCommentId(),
                websiteCommentFollow.getUserId());
        if(old!=null){
            old.setState(1);
            websiteCommentFollowService.updateByPrimaryKeySelective(old);
        }else {
            websiteCommentFollow.setId(IdGenerate.nextId());
            websiteCommentFollow.setState(1);
            websiteBlogCommentService.followComment(websiteCommentFollow.getCommentId());
            websiteCommentFollowService.insert(websiteCommentFollow);
        }
        return "success";
    }


    //取消喜欢评论
    @PostMapping(value = "cancelComment")
    @ResponseBody
    public String cancelComment(@RequestBody WebsiteCommentFollow websiteCommentFollow) throws CustomerException {
        if(websiteCommentFollow.getCommentId()==null){
            return "error";
        }
        WebsiteBlogComment blogComment=websiteBlogCommentService.selectByPrimaryKey(websiteCommentFollow.getCommentId());
        if(blogComment==null){
            return "error";
        }
        Session session = getSession();
        websiteCommentFollow.setUserId(session.getId());
        WebsiteCommentFollow old=websiteCommentFollowService.queryWebsiteCommentFollow(websiteCommentFollow.getCommentId(),
                websiteCommentFollow.getUserId());
        if(old!=null){
            old.setState(0);
            websiteCommentFollowService.updateByPrimaryKeySelective(old);
        }
        return "success";
    }


    //路由列表
    @RequestMapping(value = "listRouters")
    @ResponseBody
    public List<String> listRouters(){
    List<String> list= websiteBlogInfoService.listRouters();
        return list;
}

    private Session getSession() throws CustomerException {
         Session session = UserUtil.getSession(redisTemplate);
        if (session == null){
            throw new CustomerException(CustomerExceptionEnum.LOGIN_INFORMATION_INVALID);
        }
        return session;
    }

}
