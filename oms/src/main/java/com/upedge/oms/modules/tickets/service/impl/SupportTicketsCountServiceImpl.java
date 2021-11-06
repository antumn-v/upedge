package com.upedge.oms.modules.tickets.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.oms.modules.tickets.dao.SupportTicketsCountDao;
import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import com.upedge.oms.modules.tickets.request.TicketsDataRequest;
import com.upedge.oms.modules.tickets.request.TicketsListDataRequest;
import com.upedge.oms.modules.tickets.service.SupportTicketsCountService;
import com.upedge.oms.modules.tickets.vo.TicketsDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SupportTicketsCountServiceImpl implements SupportTicketsCountService {

    @Autowired
    private SupportTicketsCountDao supportTicketsCountDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        SupportTicketsCount record = new SupportTicketsCount();
        record.setId(id);
        return supportTicketsCountDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SupportTicketsCount record) {
        return supportTicketsCountDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SupportTicketsCount record) {
        return supportTicketsCountDao.insert(record);
    }

    /**
     *
     */
    public SupportTicketsCount selectByPrimaryKey(Long id){
        SupportTicketsCount record = new SupportTicketsCount();
        record.setId(id);
        return supportTicketsCountDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(SupportTicketsCount record) {
        return supportTicketsCountDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(SupportTicketsCount record) {
        return supportTicketsCountDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<SupportTicketsCount> select(Page<SupportTicketsCount> record){
        record.initFromNum();
        return supportTicketsCountDao.select(record);
    }

    /**
    *
    */
    public long count(Page<SupportTicketsCount> record){
        return supportTicketsCountDao.count(record);
    }

    @Override
    public BaseResponse ticketsData(TicketsDataRequest ticketsDataRequest) {
        ticketsDataRequest.initFromNum();
        List<TicketsDataVo> result =  supportTicketsCountDao.selectTicketsDataPage(ticketsDataRequest);

        Long count =   supportTicketsCountDao.selectTicketsDataCount(ticketsDataRequest);
        ticketsDataRequest.setTotal(count);
        return BaseResponse.success(result,count);
    }

    /**
     * 统计报表：ticket数据
     * @param request
     * @return
     */
    @Override
    public BaseResponse listData(TicketsListDataRequest request) {
        request.initFromNum();
        Long count = supportTicketsCountDao.totalSupportTicketsCount(request);
        List<SupportTicketsCount> list=supportTicketsCountDao.pageSupportTickets(request);

        request.setTotal(count);
        return BaseResponse.success(list,request);
    }

}