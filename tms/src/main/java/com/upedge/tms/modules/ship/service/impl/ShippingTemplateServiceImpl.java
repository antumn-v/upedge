package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.common.model.tms.ShippingTemplateVo;
import com.upedge.tms.modules.ship.dao.ShippingMethodTemplateDao;
import com.upedge.tms.modules.ship.dao.ShippingTemplateDao;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import com.upedge.tms.modules.ship.request.ShippingTemplateListRequest;
import com.upedge.tms.modules.ship.response.ShippingTemplateDisableResponse;
import com.upedge.tms.modules.ship.response.ShippingTemplateEnableResponse;
import com.upedge.tms.modules.ship.response.ShippingTemplateListResponse;
import com.upedge.tms.modules.ship.service.ShippingTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ShippingTemplateServiceImpl implements ShippingTemplateService {

    @Autowired
    private ShippingTemplateDao shippingTemplateDao;
    @Autowired
    private ShippingMethodTemplateDao shippingMethodTemplateDao;
    @Autowired
    private PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ShippingTemplate record = new ShippingTemplate();
        record.setId(id);
        return shippingTemplateDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingTemplate record) {
        return shippingTemplateDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingTemplate record) {
        ShippingTemplateRedis shippingTemplateRedis = new ShippingTemplateRedis();
        BeanUtils.copyProperties(record,shippingTemplateRedis);
        redisTemplate.opsForHash().put(RedisKey.SHIPPING_TEMPLATE,String.valueOf(record.getId()),shippingTemplateRedis);
        return shippingTemplateDao.insert(record);
    }

    /**
     *
     */
    public ShippingTemplate selectByPrimaryKey(Long id){
        return shippingTemplateDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    public int updateByPrimaryKeySelective(ShippingTemplate record) {
        shippingTemplateDao.updateByPrimaryKeySelective(record);
        record = selectByPrimaryKey(record.getId());
        ShippingTemplateRedis shippingTemplateRedis = new ShippingTemplateRedis();
        BeanUtils.copyProperties(record,shippingTemplateRedis);
        redisTemplate.opsForHash().put(RedisKey.SHIPPING_TEMPLATE,String.valueOf(record.getId()),shippingTemplateRedis);
        return 1;
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ShippingTemplate record) {
        return shippingTemplateDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ShippingTemplate> select(Page<ShippingTemplate> record){
        record.initFromNum();
        return shippingTemplateDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ShippingTemplate> record){
        return shippingTemplateDao.count(record);
    }

    @Override
    public ShippingTemplateListResponse list(ShippingTemplateListRequest request) {
        List<ShippingTemplate> results =select(request);
        Long total = count(request);
        request.setTotal(total);
        List<ShippingTemplateVo> shippingTemplateVoList=new ArrayList<>();
        results.forEach(shippingTemplate -> {
            ShippingTemplateVo shippingTemplateVo=new ShippingTemplateVo();
            BeanUtils.copyProperties(shippingTemplate,shippingTemplateVo);
            int methodNum=shippingMethodTemplateDao.countShipMethod(shippingTemplate.getId());
            shippingTemplateVo.setMethodNum(methodNum);
            shippingTemplateVoList.add(shippingTemplateVo);
        });
        ShippingTemplateListResponse res = new ShippingTemplateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shippingTemplateVoList,request);
        return res;
    }

    @Override
    public List<ShippingTemplate> getAll() {
        return shippingTemplateDao.allUseShippingTemplate();
    }

    @Override
    public ShippingTemplateListResponse allShippingTemplate() {
        List<ShippingTemplate> shippingTemplateList=shippingTemplateDao.allUseShippingTemplate();
        return new ShippingTemplateListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,shippingTemplateList);
    }

    @Override
    @Transactional
    public ShippingTemplateEnableResponse enableShippingTemplate(Long id) {
        shippingTemplateDao.updateShippingTemplateState(id,1);

        ShippingTemplate record = selectByPrimaryKey(id);
        ShippingTemplateRedis shippingTemplateRedis = new ShippingTemplateRedis();
        BeanUtils.copyProperties(record,shippingTemplateRedis);
        redisTemplate.opsForHash().put(RedisKey.SHIPPING_TEMPLATE,String.valueOf(record.getId()),shippingTemplateRedis);
        return new ShippingTemplateEnableResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional
    public ShippingTemplateDisableResponse disableShippingTemplate(Long id) {
        ShippingTemplate shippingTemplate=shippingTemplateDao.selectByPrimaryKey(id);
        if(shippingTemplate==null){
            return new ShippingTemplateDisableResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        if(shippingTemplate.getState()==0){
            return new ShippingTemplateDisableResponse(ResultCode.FAIL_CODE,"该模板已经禁用");
        }
        //是否关联运输方式
        int methodNum=shippingMethodTemplateDao.countShipMethod(shippingTemplate.getId());
        if(methodNum>0){
            return new ShippingTemplateDisableResponse(ResultCode.FAIL_CODE,"有运输方式关联");
        }
        //是否产品在使用
        BaseResponse response =pmsFeignClient.countProductByShippingId(shippingTemplate.getId());
        if(response==null||response.getCode()!=1){
            return new ShippingTemplateDisableResponse(ResultCode.FAIL_CODE,"服务异常");
        }
        Integer productNum= (Integer) response.getData();
        if(productNum>0){
            return new ShippingTemplateDisableResponse(ResultCode.FAIL_CODE,"有产品关联");
        }
        redisTemplate.opsForHash().delete(RedisKey.SHIPPING_TEMPLATE,String.valueOf(id));
        shippingTemplateDao.updateShippingTemplateState(id,0);
        return new ShippingTemplateDisableResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

}