package com.upedge.ums.modules.organization.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.organization.vo.OrganizationTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationDao;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.service.OrganizationService;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Organization record = new Organization();
        record.setId(id);
        return organizationDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Organization record) {
        return organizationDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Organization record) {
        return organizationDao.insert(record);
    }

    @Override
    public BaseResponse organizationTree() {
        Session session = UserUtil.getSession(redisTemplate);
        Page<Organization> organizationPage = new Page<>();
        Organization organization = new Organization();
        organization.setCustomerId(session.getCustomerId());
        organizationPage.setT(organization);
        organizationPage.setPageSize(-1);
        List<Organization> organizations = organizationDao.select(organizationPage);
        return tree(organizations);
    }

    /**
     *
     */
    public Organization selectByPrimaryKey(Long id){
        Organization record = new Organization();
        record.setId(id);
        return organizationDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Organization record) {
        return organizationDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Organization record) {
        return organizationDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Organization> select(Page<Organization> record){
        record.initFromNum();
        return organizationDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Organization> record){
        return organizationDao.count(record);
    }


    private BaseResponse tree(List<Organization> organizations)  {
        List<OrganizationTreeVo> list = new ArrayList<>();
        BeanUtils.copyProperties(organizations,list);
        // 初始化结果集
        List<OrganizationTreeVo> result = new ArrayList<OrganizationTreeVo>();
        // 初始化索引
        Map<Long,OrganizationTreeVo> map = new HashMap<Long,OrganizationTreeVo>();
        // 死循环阈值
        int maxLoop = (list.size() + 1)*list.size()/2;
        while(list.size() > 0) {
            OrganizationTreeVo org = list.get(0);
            // 一级节点
            if(org.getOrgParent().longValue() == 0L) {
                result.add(org);
                map.put(org.getId(),org);
                list.remove(0);
            }
            else {
                //其他节点
                if(map.containsKey(org.getOrgParent())) {
                    //父节点地址在map中有记录
                    OrganizationTreeVo parent = map.get(org.getOrgParent());
                    parent.getChildren().add(org);
                    map.put(org.getId(), org);
                    list.remove(0);
                }
                else {
                    // 如果没有找到父节点就将其移动到队列的末尾
                    list.remove(0);
                    list.add(org);
                }
            }
            if(maxLoop-- < 0) {
                //当循环到此时，说明有孤立节点，终止循环
                return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
            }
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,result);
    }
}