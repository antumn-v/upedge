package com.upedge.cms.modules.notice.controller;


import com.upedge.cms.modules.notice.entity.AppNotice;
import com.upedge.cms.modules.notice.request.AppNoticeAddRequest;
import com.upedge.cms.modules.notice.request.AppNoticeListRequest;
import com.upedge.cms.modules.notice.request.AppNoticeUpdateRequest;
import com.upedge.cms.modules.notice.response.AppNoticeAddResponse;
import com.upedge.cms.modules.notice.response.AppNoticeInfoResponse;
import com.upedge.cms.modules.notice.response.AppNoticeListResponse;
import com.upedge.cms.modules.notice.response.AppNoticeUpdateResponse;
import com.upedge.cms.modules.notice.service.AppNoticeService;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author author
 */
@RestController
@RequestMapping("/notice")
public class AppNoticeController {
    @Autowired
    private AppNoticeService appNoticeService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("用户获取最近一次的没有接收过的notice")
    @GetMapping("/user/recent")
    public BaseResponse userRecentNotice(HttpServletRequest request, HttpServletResponse response) {
        Session session = UserUtil.getSession(redisTemplate);
        AppNotice notice = appNoticeService.selectRecentlyNotice();
        String key = RedisKey.STRING_USER_RECENT_NOTICE + session.getId() + ":" + notice.getId();
        Long sentTime = (Long) redisTemplate.opsForValue().get(key);
        if (sentTime == null) {
            appNoticeService.addViewCountById(notice.getId());
            redisTemplate.opsForValue().set(key,System.currentTimeMillis(),30, TimeUnit.DAYS);
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, notice);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }


    //=============================admin===========================

    /**
     * 公告列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public AppNoticeListResponse list(@RequestBody @Valid AppNoticeListRequest request) {
        return appNoticeService.adminList(request);
    }

    /**
     * 公告详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.POST)
    public AppNoticeInfoResponse adminInfo(@PathVariable Long id) {
        return appNoticeService.adminInfo(id);
    }

    /**
     * 新增公告
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AppNoticeAddResponse addNotice(@RequestBody @Valid AppNoticeAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return appNoticeService.addNotice(request, session);
    }

    /**
     * 更新公告
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AppNoticeUpdateResponse updateNotice(@RequestBody @Valid AppNoticeUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return appNoticeService.updateNotice(request, session);
    }

    /**
     * 启用公告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable/{id}", method = RequestMethod.POST)
    public AppNoticeUpdateResponse enableNotice(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return appNoticeService.enableNotice(id, session);
    }

    /**
     * 禁用公告
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.POST)
    public AppNoticeUpdateResponse disableNotice(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return appNoticeService.disableNotice(id, session);
    }
}
