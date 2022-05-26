package com.upedge.cms.modules.notice.service.impl;

import com.upedge.cms.modules.notice.dao.AppNoticeDao;
import com.upedge.cms.modules.notice.entity.AppNotice;
import com.upedge.cms.modules.notice.request.AppNoticeAddRequest;
import com.upedge.cms.modules.notice.request.AppNoticeListRequest;
import com.upedge.cms.modules.notice.request.AppNoticeUpdateRequest;
import com.upedge.cms.modules.notice.response.AppNoticeAddResponse;
import com.upedge.cms.modules.notice.response.AppNoticeInfoResponse;
import com.upedge.cms.modules.notice.response.AppNoticeListResponse;
import com.upedge.cms.modules.notice.response.AppNoticeUpdateResponse;
import com.upedge.cms.modules.notice.service.AppNoticeService;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class AppNoticeServiceImpl implements AppNoticeService {

    @Autowired
    private AppNoticeDao appNoticeDao;

    @Override
    public AppNotice selectRecentlyNotice() {
        return appNoticeDao.selectRecentlyNotice();
    }

    @Override
    public int addViewCountById(Long id) {
        return appNoticeDao.addViewCountById(id);
    }

    /**
     * 公告列表
     * @param request
     * @return
     */
    @Override
    public AppNoticeListResponse adminList(AppNoticeListRequest request) {
        request.initFromNum();
        List<AppNotice> results = appNoticeDao.select(request);
        Long total = appNoticeDao.count(request);
        request.setTotal(total);
        return new AppNoticeListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * 公告详情
     * @param id
     * @return
     */
    @Override
    public AppNoticeInfoResponse adminInfo(Long id) {
        AppNotice result = appNoticeDao.selectByPrimaryKey(id);
        return new AppNoticeInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
    }

    /**
     * 新增公告
     */
    @Transactional(readOnly = false)
    @Override
    public AppNoticeAddResponse addNotice(AppNoticeAddRequest request, Session session) {
        AppNotice entity=request.toAppNotice(session.getId());
        entity.setId(IdGenerate.nextId());
        appNoticeDao.insert(entity);
        return new AppNoticeAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新公告
     * @param request
     * @param session
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public AppNoticeUpdateResponse updateNotice(AppNoticeUpdateRequest request, Session session) {
        AppNotice entity=request.toAppNotice(request.getId(),session.getId());
        appNoticeDao.updateByPrimaryKeySelective(entity);
        return new AppNoticeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用公告
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public AppNoticeUpdateResponse disableNotice(Long id, Session session) {
        AppNotice appNotice=new AppNotice();
        appNotice.setId(id);
        appNotice.setUpdateTime(new Date());
        appNotice.setOperatorId(session.getId());
        appNotice.setState(0);
        appNoticeDao.updateState(appNotice);
        return new AppNoticeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用公告
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public AppNoticeUpdateResponse enableNotice(Long id, Session session) {
        AppNotice appNotice=new AppNotice();
        appNotice.setId(id);
        appNotice.setUpdateTime(new Date());
        appNotice.setOperatorId(session.getId());
        appNotice.setState(1);
        appNoticeDao.updateState(appNotice);
        return new AppNoticeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

}