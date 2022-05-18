package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.manager.dao.ManagerInfoDao;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {

    @Autowired
    private ManagerInfoDao managerInfoDao;

    @Autowired
    UserService userService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ManagerInfo record = new ManagerInfo();
        record.setId(id);
        return managerInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ManagerInfo record) {
        return managerInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ManagerInfo record) {
        return managerInfoDao.insert(record);
    }

    @Override
    public ManagerInfo selectByInviteCode(String inviteCode) {
        if (StringUtils.isBlank(inviteCode)){
            return null;
        }
        return managerInfoDao.selectByInviteCode(inviteCode);
    }

    @Override
    public List<ManagerInfoVo> selectManagerDetail() {
        return managerInfoDao.selectManagerDetail();
    }

    @Transactional
    @Override
    public BaseResponse create(ManagerInfoAddRequest request, Session session) {
        if (session.getApplicationId() != Constant.ADMIN_APPLICATION_ID
        || session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            return BaseResponse.failed("权限不足");
        }
        ManagerInfo managerInfo = managerInfoDao.selectByInviteCode(request.getInviteCode());
        if (null != managerInfo){
            return BaseResponse.failed("邀请码不可重复");
        }
        User user = userService.selectByLoginName(request.getLoginName());
        if (user != null){
            return BaseResponse.failed("登陆信息不能重复");
        }
        Long userId = IdGenerate.nextId();
        user = request.toUser(userId);
        user.setCustomerId(session.getCustomerId());
        userService.insert(user);
        userService.userBindAccountOrgApp(userId,session.getApplicationId(),session.getAccountId(),session.getParentOrganization().getId(),session.getRole().getId());

        managerInfo = request.toManagerInfo();
        managerInfo.setUserId(userId);
        managerInfo.setCreatorId(session.getId());
        insert(managerInfo);
        return BaseResponse.success();
    }

    /**
     *
     */
    public ManagerInfo selectByPrimaryKey(Long id){
        ManagerInfo record = new ManagerInfo();
        record.setId(id);
        return managerInfoDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ManagerInfo record) {
        return managerInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ManagerInfo record) {
        return managerInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ManagerInfo> select(Page<ManagerInfo> record){
        record.initFromNum();
        return managerInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ManagerInfo> record){
        return managerInfoDao.count(record);
    }

}