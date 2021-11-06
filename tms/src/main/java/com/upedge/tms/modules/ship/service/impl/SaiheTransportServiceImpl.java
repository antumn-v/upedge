package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiGetTransportResponse;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiTransport;
import com.upedge.thirdparty.saihe.service.SaiheService;
import com.upedge.tms.modules.ship.dao.SaiheTransportDao;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.response.SaiheTransportListResponse;
import com.upedge.tms.modules.ship.response.SaiheTransportUpdateResponse;
import com.upedge.tms.modules.ship.service.SaiheTransportService;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaiheTransportServiceImpl implements SaiheTransportService {

    @Autowired
    private SaiheTransportDao saiheTransportDao;

    @Autowired
    private ShippingMethodService shippingMethodService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        SaiheTransport record = new SaiheTransport();
        record.setId(id);
        return saiheTransportDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SaiheTransport record) {
        return saiheTransportDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SaiheTransport record) {
        return saiheTransportDao.insert(record);
    }

    /**
     *
     */
    public SaiheTransport selectByPrimaryKey(Integer id){
        SaiheTransport record = new SaiheTransport();
        record.setId(id);
        return saiheTransportDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(SaiheTransport record) {
        shippingMethodService.updateBySaiheTransport(record);
        int i = saiheTransportDao.updateByPrimaryKeySelective(record);
        SaiheTransport saiheTransport = saiheTransportDao.selectByPrimaryKey(record);
        redisTemplate.opsForValue().set(
                RedisKey.STRING_SAIHE_TRANSPORT_IDKEY+saiheTransport.getId(),
                saiheTransport
        );
        return i;
    }

    /**
    *
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(SaiheTransport record) {
        int i = saiheTransportDao.updateByPrimaryKey(record);
        SaiheTransport saiheTransport = saiheTransportDao.selectByPrimaryKey(record);
        redisTemplate.opsForValue().set(
                RedisKey.STRING_SAIHE_TRANSPORT_IDKEY+saiheTransport.getId(),
                saiheTransport
        );
        return i;
    }

    /**
    *
    */
    public List<SaiheTransport> select(Page<SaiheTransport> record){
        record.initFromNum();
        return saiheTransportDao.select(record);
    }

    /**
    *
    */
    public long count(Page<SaiheTransport> record){
        return saiheTransportDao.count(record);
    }

    @Override
    public SaiheTransportListResponse allSaiheTransport() {
        List<SaiheTransport> saiheTransportList=saiheTransportDao.allSaiheTransport();
        return new SaiheTransportListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,saiheTransportList);
    }

    @Override
    public SaiheTransportUpdateResponse refreshSaihe() {
        //获取运输方式列表
        List<SaiheTransport> saiheTransportList=new ArrayList<>();
        ApiGetTransportResponse apiGetTransportResponse= SaiheService.getTransportList(SaiheConfig.SOURCINBOX_DEFAULT_WAREHOURSE_ID);
        if(apiGetTransportResponse.getGetTransportListResult().getStatus().equals("OK")){
            List<ApiTransport> transportList=apiGetTransportResponse.getGetTransportListResult().
                    getTransportList().getApiTransport();
            for(ApiTransport a:transportList){
                SaiheTransport saiheTransport=new SaiheTransport();
                saiheTransport.setId(a.getID());
                saiheTransport.setCarrierName(a.getCarrierName());
                saiheTransport.setTransportName(a.getTransportName());
                saiheTransport.setTransportNameEn(a.getTransportNameEn());
                saiheTransport.setIsRegistered(a.getRegistered());
                saiheTransportList.add(saiheTransport);

            }
        }
        saiheTransportDao.saveSaiheTransport(saiheTransportList);
        for (SaiheTransport saiheTransport : saiheTransportList) {
            redisTemplate.opsForValue().set(
                    RedisKey.STRING_SAIHE_TRANSPORT_IDKEY+saiheTransport.getId(),
                    saiheTransport
            );
        }
        return new SaiheTransportUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}