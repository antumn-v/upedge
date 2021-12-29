package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.utils.IdGenerate;
import com.upedge.tms.modules.ship.dao.ShippingMethodDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodTemplateDao;
import com.upedge.tms.modules.ship.dao.ShippingTemplateDao;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateUpdateRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateAddResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateListResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateUpdateResponse;
import com.upedge.tms.modules.ship.service.ShippingMethodTemplateService;
import com.upedge.tms.modules.ship.vo.MethodTemplateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ShippingMethodTemplateServiceImpl implements ShippingMethodTemplateService {

    @Autowired
    private ShippingMethodTemplateDao shippingMethodTemplateDao;
    @Autowired
    private ShippingMethodDao shippingMethodDao;
    @Autowired
    private ShippingTemplateDao shippingTemplateDao;

    @Autowired
    RedisTemplate redisTemplate;





    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        ShippingMethodTemplate record = new ShippingMethodTemplate();
        record.setId(id);
        return shippingMethodTemplateDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(ShippingMethodTemplate record) {
        return shippingMethodTemplateDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(ShippingMethodTemplate record) {
        record.setId(IdGenerate.nextId());
        return shippingMethodTemplateDao.insert(record);
    }

    @Override
    public void redisInit() {
        List<ShippingMethodTemplate> shippingMethodTemplateList=shippingMethodTemplateDao.listShippingMethodTemplate();
        List<ShippingTemplate> shippingTemplates = shippingTemplateDao.allUseShippingTemplate();
        for (ShippingTemplate shippingTemplate : shippingTemplates) {
            redisTemplate.delete(RedisKey.SHIPPING_METHODS+shippingTemplate.getId());
        }
        for(ShippingMethodTemplate shippingMethodTemplate:shippingMethodTemplateList){
            Long methodId=shippingMethodTemplate.getMethodId();
            redisTemplate.opsForSet().add(RedisKey.SHIPPING_METHODS+shippingMethodTemplate.getShippingId(),methodId);
        }
    }

    /**
     *
     */
    public ShippingMethodTemplate selectByPrimaryKey(Long id){
        ShippingMethodTemplate record = new ShippingMethodTemplate();
        record.setId(id);
        return shippingMethodTemplateDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(ShippingMethodTemplate record) {
        return shippingMethodTemplateDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(ShippingMethodTemplate record) {
        return shippingMethodTemplateDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<ShippingMethodTemplate> select(Page<ShippingMethodTemplate> record){
        record.initFromNum();
        return shippingMethodTemplateDao.select(record);
    }

    /**
    *
    */
    public long count(Page<ShippingMethodTemplate> record){
        return shippingMethodTemplateDao.count(record);
    }

    /**
     * 更新排序
     * @param request
     * @return
     */
    @Transactional
    @Override
    public ShippingMethodTemplateUpdateResponse updateSort(ShippingMethodTemplateUpdateRequest request) {
        shippingMethodTemplateDao.updateSort(request.getMethodTemplateList());
        return new ShippingMethodTemplateUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 运输方式对应的运输属性列表
     */
    @Override
    public ShippingMethodTemplateListResponse listMethodTemplate( ShippingMethodTemplateListRequest request) {
        if(request.getT()==null){
            ShippingMethodTemplate t=new ShippingMethodTemplate();
            request.setT(t);
        }
        List<ShippingMethodTemplate> results =select(request);
        List<MethodTemplateVo> methodTemplateVoList=new ArrayList<>();
        results.forEach(methodTemplate -> {
            MethodTemplateVo methodTemplateVo=new MethodTemplateVo();
            BeanUtils.copyProperties(methodTemplate,methodTemplateVo);
            ShippingMethod shippingMethod=shippingMethodDao.selectByPrimaryKey(methodTemplate.getMethodId());
            methodTemplateVo.setMethodName(shippingMethod.getName());
            methodTemplateVo.setDescription(shippingMethod.getDesc());
            ShippingTemplate shippingTemplate=shippingTemplateDao.selectByPrimaryKey(methodTemplate.getShippingId());
            methodTemplateVo.setShipName(shippingTemplate.getName());
            methodTemplateVoList.add(methodTemplateVo);
        });
        Long total =count(request);
        request.setTotal(total);
        return new ShippingMethodTemplateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,methodTemplateVoList,request);
    }

    @Override
    @Transactional
    public ShippingMethodTemplateAddResponse addShippingMethodTemplate(ShippingMethodTemplateAddRequest request) {
        ShippingTemplate shippingTemplate=shippingTemplateDao.selectByPrimaryKey(request.getShippingId());
        if(shippingTemplate.getState()==0){
            return new ShippingMethodTemplateAddResponse(ResultCode.FAIL_CODE,"运输模板已经禁用");
        }
        ShippingMethodTemplate methodTemplate=shippingMethodTemplateDao.queryMethodTemplate(request.getShippingId(),request.getMethodId());
        if(methodTemplate!=null){
            return new ShippingMethodTemplateAddResponse(ResultCode.FAIL_CODE,"重复添加");
        }
        ShippingMethodTemplate entity=request.toShippingMethodTemplate();
        insertSelective(entity);
        return new ShippingMethodTemplateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    @Override
    public List<ShippingMethodTemplate> listShippingMethodTemplate() {
        return shippingMethodTemplateDao.listShippingMethodTemplate();
    }
}